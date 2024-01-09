package flash.netty.chat.handler;

import flash.netty.chat.domain.DefaultChannelGroup;
import flash.netty.chat.packet.CreateGroupResponsePacket;
import flash.netty.chat.util.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import flash.netty.chat.packet.CreateGroupRequestPacket;

import java.util.ArrayList;
import java.util.List;

public class CreateGroupRequestHandler extends SimpleChannelInboundHandler<CreateGroupRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CreateGroupRequestPacket packet) {
        List<String> userNameList = new ArrayList<>();
        List<String> userIds = packet.getUserIds();


        DefaultChannelGroup channelGroup = new DefaultChannelGroup(ctx.executor());

        for (String userId : userIds) {
            Channel channel = SessionUtil.getChannel(userId);
            if (channel != null) {
                channelGroup.addChannel(channel);
                userNameList.add(SessionUtil.getSession(channel).getUserName());
            }
        }

        CreateGroupResponsePacket responsePacket = new CreateGroupResponsePacket();
        responsePacket.setSuccess(true);
        responsePacket.setGroupId(packet.getGroupName());
        responsePacket.setUserNameList(userNameList);

        channelGroup.writeAndFlush(responsePacket);

        System.out.println("群聊创建成功，id为[" + responsePacket.getGroupId() + "]");
        System.out.println("群里面有：" + responsePacket.getUserNameList());
    }
}
