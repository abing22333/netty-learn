package flash.netty.chat.client;

import io.netty.channel.Channel;
import flash.netty.chat.packet.CreateGroupRequestPacket;

import java.util.Arrays;
import java.util.Scanner;

public class CreateGroupConsoleCommand implements ConsoleCommand {
    private final static String USER_ID_SPLITER = ",";

    @Override
    public void exec(Scanner scanner, Channel channel) {
        CreateGroupRequestPacket createGroupRequestPacket = new CreateGroupRequestPacket();
        System.out.print("【拉人群聊】输入userId列表: ");
        String userIds = scanner.next();
        createGroupRequestPacket.setUserIds(Arrays.asList(userIds.split(USER_ID_SPLITER)));

        System.out.print("【拉人群聊】输入群聊名称: ");
        String groupName = scanner.next();
        createGroupRequestPacket.setGroupName(groupName);

        channel.writeAndFlush(createGroupRequestPacket);
    }
}
