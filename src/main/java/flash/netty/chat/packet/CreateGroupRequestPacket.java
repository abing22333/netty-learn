package flash.netty.chat.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
@Data
@EqualsAndHashCode(callSuper = true)
public class CreateGroupRequestPacket extends Packet {
    private List<String> userIds;

    private String groupName;

    @Override
    public Byte getCommand() {
        return Command.CREATE_GROUP_REQUEST;
    }
}
