package org.example.chat.packet;

import lombok.Data;

/**
 * @author abing
 * @date 2024/1/8
 */
@Data
public class LoginResponsePacket extends Packet {
    @Override
    public Byte getCommand() {
        return Command.LOGIN_RESPONSE;
    }

    private Boolean success;

    private String reason;
}
