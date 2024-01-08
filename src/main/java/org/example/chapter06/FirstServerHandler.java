package org.example.chapter06;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.StandardCharsets;
import java.util.Date;


public class FirstServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(new Date() + ": 新客户端接入 ");
        ctx.channel().writeAndFlush(getByteBuf(ctx));
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buffer = (ByteBuf) msg;
        System.out.println(new Date() + ": 服务端读取数据 -> " + buffer.toString(StandardCharsets.UTF_8));


        System.out.println(new Date() + ": 服务端写出数据");
        ctx.channel().writeAndFlush(getByteBuf(ctx));
    }

    ByteBuf getByteBuf(ChannelHandlerContext ctx) {
        ByteBuf buffer = ctx.alloc().buffer();
        buffer.writeBytes("你好，欢迎关注我的微信公众号，《闪电侠的博客》！".getBytes(StandardCharsets.UTF_8));
        return buffer;
    }
}
