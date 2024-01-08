package org.example.chat.packet;

/**
 * @author abing
 * @date 2024/1/8
 */
public class MessageRequestPacket extends Packet {
    @Override
    public Byte getCommand() {
        return Command.MESSAGE_REQUEST;
    }
}
