package javaIO.网络IO.netty权威指南.netty.ch5;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by wangxi on 09/06/2018.
 */
public class EchoClientHandler extends ChannelHandlerAdapter {

    private  int counter;

    static final String ECHO_REQ = "Hi,welcome to Netty.$_";

    public EchoClientHandler(){

    }
    @Override
    public void channelActive(ChannelHandlerContext ctx){
        for(int i = 0;i < 10; i++){
            ctx.writeAndFlush(Unpooled.copiedBuffer(ECHO_REQ.getBytes()));
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx,Object msg) throws Exception{
        System.out.println("This is " + ++counter + " times receive server :[" + msg +"]");
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception{
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx,Throwable cause){
        cause.printStackTrace();
        ctx.close();
    }
}
