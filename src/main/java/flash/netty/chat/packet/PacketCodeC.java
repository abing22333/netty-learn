package flash.netty.chat.packet;

import flash.netty.chat.serializer.Serializer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

/**
 * @author abing
 */
public class PacketCodeC {
    public final static PacketCodeC INSTANCE = new PacketCodeC();

    private PacketCodeC() {
    }

    private final static int MAGIC_NUMBER = 0x12345678;


    public ByteBuf encode(ByteBuf byteBuf, Packet packet) {

        byte[] bytes = Serializer.DEFAULT.serialize(packet);

        byteBuf.writeInt(MAGIC_NUMBER);
        byteBuf.writeByte(packet.getVersion());
        byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlgorithm());
        byteBuf.writeByte(packet.getCommand());
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);

        return byteBuf;
    }

    public ByteBuf encode(ByteBufAllocator byteBufAllocator, Packet packet) {
        ByteBuf byteBuf = byteBufAllocator.ioBuffer();
        return encode(byteBuf, packet);
    }

    public Packet decode(ByteBuf byteBuf) {
        byteBuf.skipBytes(4);

        byteBuf.skipBytes(1);

        byte serializerAlgorithm = byteBuf.readByte();

        byte command = byteBuf.readByte();

        int length = byteBuf.readInt();
        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);

        Class<? extends Packet> requestType = Packet.getRequestType(command);
        Serializer serializer = Serializer.getSerializer(serializerAlgorithm);

        if (requestType != null && serializer != null) {
            return serializer.deserialize(requestType, bytes);
        }
        return null;
    }
}
