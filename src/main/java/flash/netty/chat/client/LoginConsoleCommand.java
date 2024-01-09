package flash.netty.chat.client;

import flash.netty.chat.packet.LoginRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

public class LoginConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.print("输入用户名登录：");

        LoginRequestPacket packet = new LoginRequestPacket();
        String userName = scanner.nextLine();
        packet.setUserName(userName);
        packet.setPassword("pwd");
        packet.setUserId(userName);

        CountDownLatch countDownLatch = new CountDownLatch(1);
        channel.writeAndFlush(packet).addListener(c -> {
            while (!c.isDone()) {
            }
            countDownLatch.countDown();
        });

        try {
            countDownLatch.await();
        } catch (Exception ignored) {

        }
    }
}
