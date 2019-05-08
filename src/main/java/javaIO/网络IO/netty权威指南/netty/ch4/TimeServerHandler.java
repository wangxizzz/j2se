package javaIO.网络IO.netty权威指南.netty.ch4;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.util.Date;

public class TimeServerHandler extends ChannelHandlerAdapter {

    private int counter;

    @Override
    public void channelRead(ChannelHandlerContext ctx,Object msg) throws Exception{

        String body = (String)msg;
        System.out.println("the time server receive order:"+body + ";the counter is:" + ++counter);
        String currentTime = "query time order".equalsIgnoreCase(body)? new Date().toString():"BAD ORDER";
        // 添加系统换行符
        currentTime = currentTime + System.getProperty("line.separator");
        // 把当前的时间返回Client
        ByteBuf resp = Unpooled.copiedBuffer(currentTime.getBytes());
        ctx.writeAndFlush(resp);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx,Throwable throwable){
        ctx.close();
    }
}
