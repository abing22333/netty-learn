package org.example.chapter06;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class NettyClient {
    public static void main(String[] args) throws InterruptedException {
        Bootstrap bootstrap = new Bootstrap();
        NioEventLoopGroup group = new NioEventLoopGroup();

        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel ch) throws Exception {
                        ch.pipeline().addLast(new FirstClientHandler());
                    }
                });


        connect(bootstrap, "127.0.0.1", 8080, 5);

    }

    static void connect(Bootstrap bootstrap, String host, int port, int retry) {
        bootstrap.connect(host, port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.printf("%s [%s:%d]连接成功%n", new Date(), host, port);
            } else if (retry <= 0) {
                System.out.printf("%s [%s:%d]放弃连接%n", new Date(), host, port);
            } else {
                System.out.printf("%s [%s:%d]连接失败%n", new Date(), host, port);
                bootstrap.config().group().schedule(() -> connect(bootstrap, host, port, Math.min(retry - 1, 5)), (5 - retry) * 3L, TimeUnit.SECONDS);
            }
        });
    }
}
