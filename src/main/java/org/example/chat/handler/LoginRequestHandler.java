package org.example.chat.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.example.chat.packet.LoginRequestPacket;
import org.example.chat.packet.LoginResponsePacket;
import org.example.chat.packet.PacketCodeC;
import org.example.chat.util.LoginUtil;

import java.util.Date;

public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket packet) throws Exception {

        LoginResponsePacket responsePacket = login(ctx, packet);
        ByteBuf writeBuf = PacketCodeC.INSTANCE.encode(ctx.alloc(), responsePacket);
        ctx.channel().writeAndFlush(writeBuf);
    }

    private LoginResponsePacket login(ChannelHandlerContext ctx, LoginRequestPacket packet) {
        System.out.println(new Date() + "：客户端开始登录");

        LoginResponsePacket responsePacket = new LoginResponsePacket();
        responsePacket.setVersion(packet.getVersion());
        if (valid(packet)) {
            responsePacket.setSuccess(true);
            LoginUtil.markAsLogin(ctx.channel());
        } else {
            responsePacket.setSuccess(false);
            responsePacket.setReason("账号或者秘密错误");
        }

        return responsePacket;
    }


    private boolean valid(LoginRequestPacket requestPacket) {
        return true;
    }
}
