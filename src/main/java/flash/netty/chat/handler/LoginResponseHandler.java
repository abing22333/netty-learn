package flash.netty.chat.handler;

import flash.netty.chat.packet.LoginResponsePacket;
import flash.netty.chat.util.LoginUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.time.LocalDateTime;

public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {


    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端连接被关闭！");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket msg) throws Exception {

        if (msg.getSuccess()) {
            LoginUtil.markAsLogin(ctx.channel());
            System.out.println(LocalDateTime.now() + ": 客户端登录成功");
        } else {
            System.out.println(LocalDateTime.now() + ": 客户端登录失败，原因：" + msg.getReason());
        }
    }
}
