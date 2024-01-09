package flash.netty.chat.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author abing
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class LoginRequestPacket extends Packet {

    private String userId;
    private String userName;
    private String password;

    @Override
    public Byte getCommand() {
        return Command.LOGIN_REQUEST;
    }
}
