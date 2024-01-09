package flash.netty.chat.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author abing
 * @date 2024/1/8
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MessageResponsePacket extends Packet {
    private String fromUserId;
    private String fromUserName;
    private String massage;

    @Override
    public Byte getCommand() {
        return Command.MESSAGE_RESPONSE;
    }
}
