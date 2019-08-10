package javaIO.网络IO.netty权威指南.netty.protobuf;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangxi on 10/06/2018.
 */
public class SubReqClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx){
        for(int i = 0;i <10;i++){
            ctx.write(subReq(i));
        }
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx,Object msg){
        System.out.println("Receive server response:[" + msg +"]");
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx){
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx,Throwable cause){
        cause.printStackTrace();
        ctx.close();
    }

    private Object subReq(int i) {
        SubscribeReqProto.SubscribeReq.Builder builder = SubscribeReqProto.SubscribeReq.newBuilder();
        builder.setSubReqID(i);
        builder.setUserName("wangxi");
        builder.setProductName("netty book");
        List<String> address = new ArrayList<>();
        address.add("Nanjing yuhuatai");
        address.add("BeiJing LiuLiChang");
        address.add("Shenzhen HongShuLin");
        builder.addAllAddress(address);
        return builder.build();
    }
}
