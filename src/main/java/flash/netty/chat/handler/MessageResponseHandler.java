package flash.netty.chat.handler;

import flash.netty.chat.packet.MessageResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.time.LocalDateTime;

public class MessageResponseHandler extends SimpleChannelInboundHandler<MessageResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageResponsePacket packet) throws Exception {

        System.out.println(LocalDateTime.now() + ": " + packet.getFromUserName() + " -> " + packet.getMassage());
    }


}
