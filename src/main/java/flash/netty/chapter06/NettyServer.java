package flash.netty.chapter06;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyServer {
    public static void main(String[] args) {
        ServerBootstrap serverBootstrap = new ServerBootstrap();

        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();
        serverBootstrap.group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel channel) throws Exception {
                        channel.pipeline().addLast(new FirstServerHandler());
                    }
                });

        bind(serverBootstrap, 8080);

    }

    static void bind(ServerBootstrap serverBootstrap, int port) {
        serverBootstrap.bind(port)
                .addListener(future -> {
                    if (future.isSuccess()) {
                        System.out.printf("端口[%d]绑定成功%n", port);
                    } else {
                        System.out.printf("端口[%d]绑定失败%n", port);
                        bind(serverBootstrap, port + 1);
                    }
                });
    }
}
