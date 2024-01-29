package netty.definitive.guide.bio;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;

public class BioTimeServerHandler implements Runnable {
    Socket socket;

    public BioTimeServerHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            while (true) {
                String body = in.readLine();
                if (body == null) {
                    break;
                }
                System.out.println("The time server receive order: " + body);
                String currentTime = "QUERY TIME ORDER".equals(body) ? new Date().toString() : "BAD ORDER";
                out.println(currentTime);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.socket = null;
        }
    }


}
