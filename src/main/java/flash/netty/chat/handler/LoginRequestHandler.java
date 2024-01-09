package flash.netty.chat.handler;

import flash.netty.chat.packet.LoginRequestPacket;
import flash.netty.chat.packet.LoginResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import flash.netty.chat.domain.Session;
import flash.netty.chat.util.SessionUtil;

import java.time.LocalDateTime;

public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket packet) throws Exception {

        ctx.channel().writeAndFlush(login(ctx, packet));
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Session session = SessionUtil.getSession(ctx.channel());

        System.out.println(LocalDateTime.now() + "：客户端" + session + "断开连接");
        SessionUtil.unbindSession(ctx.channel());
    }

    private LoginResponsePacket login(ChannelHandlerContext ctx, LoginRequestPacket packet) {

        LoginResponsePacket responsePacket = new LoginResponsePacket();
        responsePacket.setVersion(packet.getVersion());
        if (valid(packet)) {
            Session session = new Session();
            session.setUserName(packet.getUserName());
            session.setUserId(packet.getUserId());

            System.out.println(LocalDateTime.now() + "：客户端" + session + "登录成功");

            responsePacket.setSuccess(true);
            SessionUtil.bindSession(session, ctx.channel());
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
