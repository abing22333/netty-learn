package org.example.chapter08;

import lombok.Data;
import lombok.Getter;

@Data
public abstract class Packet {
    /**
     * 协议版本
     */
    private Byte version = 1;

    /**
     * 指令
     */
    public abstract Byte getCommand();

    @Getter
    public enum Command {

        LOGIN_REQUEST((byte) 1, LoginRequestPacket.class);

        private final Byte code;
        private final Class<? extends Packet> packetClass;


        Command(Byte code, Class<? extends Packet> packetClass) {
            this.code = code;
            this.packetClass = packetClass;
        }

        public static Class<? extends Packet> getRequestType(Byte command) {
            for (Command value : values()) {
                if (value.code.equals(command)) {
                    return value.packetClass;
                }
            }
            return null;
        }
    }
}
