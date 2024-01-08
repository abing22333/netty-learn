package org.example.chat.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.example.chat.packet.LoginRequestPacket;
import org.example.chat.packet.LoginResponsePacket;
import org.example.chat.packet.Packet;
import org.example.chat.packet.PacketCodeC;
import org.example.chat.util.LoginUtil;

import java.util.Date;
import java.util.UUID;

/**
 * @author abing
 * @date 2024/1/8
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(new Date() + "：客户端开始登录");

        LoginRequestPacket packet = new LoginRequestPacket();
        packet.setUserName("abing");
        packet.setPassword("pwd");
        packet.setUserId(UUID.randomUUID().toString());

        ByteBuf buf = PacketCodeC.INSTANCE.encode(ctx.alloc(), packet);
        ctx.channel().writeAndFlush(buf);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;

        Packet packet = PacketCodeC.INSTANCE.decode(byteBuf);

        if (packet instanceof LoginResponsePacket) {
            LoginResponsePacket responsePacket = (LoginResponsePacket) packet;
            if (responsePacket.getSuccess()) {
                LoginUtil.markAsLogin(ctx.channel());
                System.out.println(new Date() + ": 客户端登录成功");
            } else {
                System.out.println(new Date() + ": 客户端登录失败，原因：" + responsePacket.getReason());
            }
        }
    }
}
