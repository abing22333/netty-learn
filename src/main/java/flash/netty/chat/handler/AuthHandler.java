package flash.netty.chat.handler;

import flash.netty.chat.packet.LoginRequestPacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import flash.netty.chat.util.SessionUtil;

public class AuthHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof LoginRequestPacket) {
            super.channelRead(ctx, msg);
        }

        if (!SessionUtil.hasLogin(ctx.channel())) {
            ctx.channel().close();
        } else {
            ctx.pipeline().remove(this);
            super.channelRead(ctx, msg);
        }
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        if (SessionUtil.hasLogin(ctx.channel())) {
            System.out.println("当前连接验证完毕，无须再次验证，AuthHandler 被移除");
        } else {
            System.out.println("无登录验证，强制关闭连接！");
        }
    }
}
