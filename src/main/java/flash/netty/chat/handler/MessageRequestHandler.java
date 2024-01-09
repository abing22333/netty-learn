package flash.netty.chat.handler;

import flash.netty.chat.packet.MessageRequestPacket;
import flash.netty.chat.packet.MessageResponsePacket;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import flash.netty.chat.domain.Session;
import flash.netty.chat.util.SessionUtil;

public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket packet) throws Exception {
        Session session = SessionUtil.getSession(ctx.channel());

        MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
        messageResponsePacket.setFromUserId(session.getUserId());
        messageResponsePacket.setFromUserName(session.getUserName());
        messageResponsePacket.setMassage(packet.getMassage());

        Channel toUserChannel = SessionUtil.getChannel(packet.getToUserId());

        if (toUserChannel != null && SessionUtil.hasLogin(toUserChannel)) {
            toUserChannel.writeAndFlush(messageResponsePacket);
        } else {
            System.out.println("[" + messageResponsePacket.getFromUserId() + "],不在线，发生失败！");
        }
    }
}
