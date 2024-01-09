package flash.netty.chat.packet;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author abing
 */
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

    private static final Map<Byte, Class<? extends Packet>> registry = new HashMap<Byte, Class<? extends Packet>>() {{
        put(Command.LOGIN_REQUEST, LoginRequestPacket.class);
        put(Command.LOGIN_RESPONSE, LoginResponsePacket.class);
        put(Command.MESSAGE_REQUEST, MessageRequestPacket.class);
        put(Command.MESSAGE_RESPONSE, MessageResponsePacket.class);
        put(Command.CREATE_GROUP_REQUEST, CreateGroupRequestPacket.class);
        put(Command.CREATE_GROUP_RESPONSE, CreateGroupResponsePacket.class);
    }};


    public static Class<? extends Packet> getRequestType(Byte command) {
        return registry.get(command);
    }


    public interface Command {
        Byte LOGIN_REQUEST = 1;
        Byte LOGIN_RESPONSE = 2;
        Byte MESSAGE_REQUEST = 3;
        Byte MESSAGE_RESPONSE = 4;
        Byte CREATE_GROUP_REQUEST = 5;
        Byte CREATE_GROUP_RESPONSE = 6;
    }
}
