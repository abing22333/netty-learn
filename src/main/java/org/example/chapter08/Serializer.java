package org.example.chapter08;

import lombok.Getter;

public interface Serializer {

    Serializer DEFAULT = new JsonSerializer();

    byte getSerializerAlgorithm();

    byte[] serialize(Object object) ;

    <T> T deserialize(Class<T> clazz, byte[] bytes)  ;

    @Getter
    enum SerializerAlgorithm {
        /**
         * JSON序列号标识
         */

        JSON((byte) 1, Serializer.class);

        private final Byte code;
        private final Class<? extends Serializer> serializerType;

        SerializerAlgorithm(Byte code, Class<? extends Serializer> serializerType) {
            this.code = code;
            this.serializerType = serializerType;
        }
        public static Class<? extends Serializer> getRequestType(Byte command) {
            for (Serializer.SerializerAlgorithm value : values()) {
                if (value.code.equals(command)) {
                    return value.serializerType;
                }
            }
            return null;
        }
    }
}
