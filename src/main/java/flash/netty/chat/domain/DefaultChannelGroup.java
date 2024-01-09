package flash.netty.chat.domain;

import io.netty.channel.Channel;
import io.netty.util.concurrent.EventExecutor;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class DefaultChannelGroup implements ChannelGroup {

    private final EventExecutor executor;

    private final Set<Channel> channelSet = new HashSet<>();

    public DefaultChannelGroup(EventExecutor executor) {
        this.executor = executor;
    }


    @Override
    public void addChannel(Channel channel) {
        channelSet.add(channel);
    }

    @Override
    public void removeChannel(Channel channel) {
        channelSet.remove(channel);
    }

    @Override
    public void writeAndFlush(Object msg) {
        channelSet.forEach(channel -> channel.writeAndFlush(msg));
    }
}
