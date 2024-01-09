package flash.netty.chat.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author abing
 * @date 2024/1/8
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MessageRequestPacket extends Packet {
    private String toUserId;
    private String massage;

    public MessageRequestPacket(String toUserId, String message) {
        this.toUserId = toUserId;
        this.massage = message;
    }

    @Override
    public Byte getCommand() {
        return Command.MESSAGE_REQUEST;
    }
}
