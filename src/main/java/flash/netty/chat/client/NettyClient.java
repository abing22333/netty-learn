package flash.netty.chat.client;

import flash.netty.chat.handler.*;
import flash.netty.chat.util.LoginUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class NettyClient {

    public static void main(String[] args) throws InterruptedException {

        Bootstrap bootstrap = new Bootstrap();
        NioEventLoopGroup group = new NioEventLoopGroup();

        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel channel) throws Exception {
                        channel.pipeline().addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 7, 4));
                        channel.pipeline().addLast(new PacketDecoder());
                        channel.pipeline().addLast(new LoginResponseHandler());
                        channel.pipeline().addLast(new CreateGroupResponseHandler());
                        channel.pipeline().addLast(new MessageResponseHandler());
                        channel.pipeline().addLast(new PacketEncoder());
                    }
                });


        connect(bootstrap, "127.0.0.1", 8080, 5);

    }

    static void connect(Bootstrap bootstrap, String host, int port, int retry) {
        bootstrap.connect(host, port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.printf("%s [%s:%d]连接成功%n", LocalDateTime.now(), host, port);
                Channel channel = ((ChannelFuture) future).channel();
                startConsoleThread(channel);
            } else if (retry <= 0) {
                System.out.printf("%s [%s:%d]放弃连接%n", LocalDateTime.now(), host, port);
            } else {
                System.out.printf("%s [%s:%d]连接失败%n", LocalDateTime.now(), host, port);
                bootstrap.config().group().schedule(() -> connect(bootstrap, host, port, Math.min(retry - 1, 5)), (5 - retry) * 3L, TimeUnit.SECONDS);
            }
        });
    }

    public static void startConsoleThread(Channel channel) {

        Scanner scanner = new Scanner(System.in);
        new Thread(() -> {
            while (!Thread.interrupted()) {
                if (!LoginUtil.hasLogin(channel)) {
                    new LoginConsoleCommand().exec(scanner, channel);
                } else {
                    new ConsoleCommandManager().exec(scanner, channel);
                }
            }
        }).start();
    }

}
