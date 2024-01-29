package netty.definitive.guide.nio;

import java.io.IOException;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

public class NioTimeServer {

    public static void main(String[] args) {
        int port = 8080;

    }

    public class NioMultiplexerTimeServer implements Runnable {
        private Selector selector;
        private ServerSocketChannel socketChannel;

        private volatile boolean stop;

        public NioMultiplexerTimeServer(int port) {
            try {
                selector = Selector.open();
                socketChannel = ServerSocketChannel.open();
                socketChannel.configureBlocking(false);
                socketChannel.socket().bind(new InetSocketAddress(port), 1024);
                socketChannel.register(selector, SelectionKey.OP_ACCEPT);
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }

        public void stop() {
            this.stop = true;
        }

        @Override
        public void run() {
            while (!stop) {
                try {
                    selector.select(1000);
                    Set<SelectionKey> selectionKeys = selector.selectedKeys();
                    Iterator<SelectionKey> iterator = selectionKeys.iterator();

                    SelectionKey key = null;
                    while (iterator.hasNext()) {
                        key = iterator.next();
                        try {
                            handleInput(key);
                        } catch (Exception e) {
                            if (key != null) {
                                key.cancel();
                                if (key.channel() != null) {
                                    key.channel().close();
                                }
                            }
                        }
                    }

                } catch (Throwable e) {
                    e.printStackTrace();
                }
                if (selector != null) {
                    try {
                        selector.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }

        private void handleInput(SelectionKey key) throws IOException {

            if (key.isValid()) {
                if (key.isAcceptable()) {
                    ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
                    SocketChannel sc = ssc.accept();
                    sc.configureBlocking(false);
                    sc.register(selector, SelectionKey.OP_READ);
                }
                if (key.isReadable()) {
                    SocketChannel sc = (SocketChannel) key.channel();
                    ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                    int readBytes = sc.read(readBuffer);
                    if (readBytes > 0) {
                        readBuffer.flip();
                        byte[] bytes = new byte[readBuffer.remaining()];
                        readBuffer.get(bytes);
                        String body = new String(bytes, StandardCharsets.UTF_8);
                        System.out.println("The time server receive order : " + body);
                        String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body) ? new Date().toString() : "BAD ORDER";
                        doWrite(sc, currentTime);
                    } else if (readBytes < 0) {
                        key.cancel();
                        sc.close();
                    } else {
                        // 读到0字节，忽略
                    }
                }
            }
        }

        private void doWrite(SocketChannel sc, String response) throws IOException {
            if (response != null && !response.trim().isEmpty()) {
                byte[] bytes = response.getBytes();
                ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
                writeBuffer.put(bytes);
                writeBuffer.flip();
                sc.write(writeBuffer);
            }
        }
    }
}
