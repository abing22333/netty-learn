package flash.netty.chat.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import flash.netty.chat.packet.CreateGroupResponsePacket;

public class CreateGroupResponseHandler extends SimpleChannelInboundHandler<CreateGroupResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CreateGroupResponsePacket packet) {
        System.out.println("群聊创建成功，id为[" + packet.getGroupId() + "]");
        System.out.println("群里面有：" + packet.getUserNameList());
    }
}
