package org.example.chat.util;

import io.netty.channel.Channel;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;

/**
 * @author abing
 * @date 2024/1/8
 */
public class LoginUtil {


    private final static AttributeKey<Boolean> LOGIN = AttributeKey.newInstance("login");

    public static void markAsLogin(Channel channel) {
        channel.attr(LOGIN).set(true);
    }

    public static boolean hasLogin(Channel channel) {
        Attribute<Boolean> loginAttr = channel.attr(LOGIN);

        return loginAttr != null && loginAttr.get();
    }
}
