package flash.netty.chat.util;

import flash.netty.chat.domain.Session;
import io.netty.channel.Channel;
import io.netty.util.AttributeKey;

import java.util.HashMap;
import java.util.Map;

public class SessionUtil {


    private final static AttributeKey<Session> SESSION = AttributeKey.newInstance("session");

    private static final Map<String, Channel> userIdChannelMap = new HashMap<>();

    public static void bindSession(Session session, Channel channel) {
        userIdChannelMap.put(session.getUserId(), channel);
        channel.attr(SESSION).set(session);
    }

    public static void unbindSession(Channel channel) {
        if (hasLogin(channel)) {
            userIdChannelMap.remove(getSession(channel).getUserId());
            channel.attr(SESSION).set(null);
        }
    }

    public static Session getSession(Channel channel) {
        return channel.attr(SESSION).get();
    }

    public static Channel getChannel(String userId) {
        return userIdChannelMap.get(userId);
    }

    public static boolean hasLogin(Channel channel) {

        return channel.hasAttr(SESSION);
    }
}
