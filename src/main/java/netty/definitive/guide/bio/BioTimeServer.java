package netty.definitive.guide.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class BioTimeServer {
    public static void main(String[] args) throws IOException {
        int port = 8080;
        if (args != null && args.length > 0) {
            port = Integer.parseInt(args[0]);
        }

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("The time server is start in port : " + port);
            Socket socket = null;
            while (true) {
                socket = serverSocket.accept();
                new Thread(new BioTimeServerHandler(socket)).start();
            }
        } finally {
            System.out.println("The time server close");
        }
    }
}
