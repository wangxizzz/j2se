package javaIO.网络IO.netty权威指南.netty.protobuf;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by wangxi on 10/06/2018.
 */
public class SubReqServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx,Object msg){
        SubscribeReqProto.SubscribeReq req = (SubscribeReqProto.SubscribeReq)msg;
        if("wangxi".equalsIgnoreCase(req.getUserName())){
            System.out.println("server accept client subscribe req:[" + req.toString() + "]");
            SubscribeRespProto.SubscribeResp subscribeResp = resp(req.getSubReqID());
            ctx.writeAndFlush(subscribeResp);
            System.out.println(" sent to client:"+subscribeResp);
        }
    }

    private SubscribeRespProto.SubscribeResp resp(int subReqID) {
        SubscribeRespProto.SubscribeResp.Builder builder = SubscribeRespProto.SubscribeResp.newBuilder();
        builder.setSubReqID(subReqID);
        builder.setRespCode(0);
        builder.setDesc("Netty book order successd!");
        return builder.build();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx,Throwable cause){
        cause.printStackTrace();
        ctx.close();
    }
}
