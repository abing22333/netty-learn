package org.example.chat.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.example.chat.packet.LoginRequestPacket;
import org.example.chat.packet.LoginResponsePacket;
import org.example.chat.packet.Packet;
import org.example.chat.packet.PacketCodeC;
import org.example.chat.util.LoginUtil;

import java.nio.Buffer;
import java.util.Date;

/**
 * @author abing
 * @date 2024/1/8
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;

        Packet packet = PacketCodeC.INSTANCE.decode(byteBuf);
        if (packet instanceof LoginRequestPacket) {
            System.out.println(new Date() + "：客户端开始登录");

            LoginRequestPacket requestPacket = (LoginRequestPacket) packet;

            LoginResponsePacket responsePacket = new LoginResponsePacket();
            responsePacket.setVersion(packet.getVersion());
            if (valid(requestPacket)) {
                responsePacket.setSuccess(true);
                LoginUtil.markAsLogin(ctx.channel());
            } else {
                responsePacket.setSuccess(false);
                responsePacket.setReason("账号或者秘密错误");
            }
            ByteBuf writeBuf = PacketCodeC.INSTANCE.encode(ctx.alloc(), responsePacket);
            ctx.channel().writeAndFlush(writeBuf);
        }
    }

    private boolean valid(LoginRequestPacket requestPacket) {
        return true;
    }
}
