package org.example.chat.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.example.chat.packet.MessageRequestPacket;
import org.example.chat.packet.MessageResponsePacket;

import java.util.Date;

public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket packet) throws Exception {
        System.out.println(new Date() + "：服务收到消息 -> " + packet.getMassage());

        MessageResponsePacket messageResponsePacket = receiveMessage(ctx, packet);
        ctx.channel().writeAndFlush(messageResponsePacket);
    }

    private MessageResponsePacket receiveMessage(ChannelHandlerContext ctx, MessageRequestPacket packet) {
        MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
        messageResponsePacket.setVersion(packet.getVersion());
        messageResponsePacket.setMassage("服务端回复【" + packet.getMassage() + "】");

        return messageResponsePacket;
    }
}
