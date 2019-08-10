package javaIO.网络IO.netty权威指南.netty.msgPackDemo;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by wangxi on 09/06/2018.
 */
public class EchoServerMsgPackHandler extends ChannelInboundHandlerAdapter {

    int counter = 0;

    @Override
    public void channelRead(ChannelHandlerContext ctx,Object msg) throws Exception{
        try {
            //直接输出msg
            System.out.println(msg.toString());
            String remsg = new String("has receive");
            //回复has receive 给客户端
            ctx.write(msg);
            System.out.println("send reply to client");
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
        }
    }
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        // TODO Auto-generated method stub
        ctx.flush();
    }
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){
        cause.printStackTrace();
        ctx.close();
    }

}
