package org.example.chat.serializer;

import java.util.HashMap;
import java.util.Map;

/**
 * @author abing
 */
public interface Serializer {

    Serializer DEFAULT = new JsonSerializer();

    byte getSerializerAlgorithm();

    byte[] serialize(Object object);

    <T> T deserialize(Class<T> clazz, byte[] bytes);

    Map<Byte, Serializer> registry = new HashMap<Byte, Serializer>() {{
        put(SerializerAlgorithm.JSON, DEFAULT);
    }};

    static Serializer getSerializer(Byte command) {
        return registry.get(command);
    }


    interface SerializerAlgorithm {
        /**
         * JSON序列号标识
         */

        Byte JSON = 1;
    }
}
