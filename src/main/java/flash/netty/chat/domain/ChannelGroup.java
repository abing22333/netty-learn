package flash.netty.chat.domain;

import io.netty.channel.Channel;

public interface ChannelGroup {
    void addChannel(Channel channel);

    void removeChannel(Channel channel);

    void writeAndFlush(Object msg);
}
