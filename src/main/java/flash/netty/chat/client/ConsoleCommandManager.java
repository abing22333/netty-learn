package flash.netty.chat.client;

import io.netty.channel.Channel;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ConsoleCommandManager implements ConsoleCommand {

    private final Map<String, ConsoleCommand> consoleCommandMap = new HashMap<>();

    public ConsoleCommandManager() {
        consoleCommandMap.put("createGroup", new CreateGroupConsoleCommand());
        consoleCommandMap.put("sendToUser", new SendToUserConsoleCommand());
    }


    @Override
    public void exec(Scanner scanner, Channel channel) {
        String command = scanner.next();
        ConsoleCommand consoleCommand = consoleCommandMap.get(command);

        if (consoleCommand != null) {
            consoleCommand.exec(scanner, channel);
        } else {
            System.out.println("无法识别[" + command + "]指令，请重写输入！");
        }
    }
}
