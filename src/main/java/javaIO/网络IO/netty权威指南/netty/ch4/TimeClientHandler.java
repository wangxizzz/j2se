package javaIO.网络IO.netty权威指南.netty.ch4;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.util.logging.Logger;

/**
 * Created by wangxi on 24/05/2018.
 */
public class TimeClientHandler extends ChannelHandlerAdapter {

    private static final Logger logger = Logger.getLogger(TimeClientHandler.class.getName());

    private int counter;

    private byte[] req;
    public TimeClientHandler() {
        // 组装发送给服务端的消息req
         req = ("Query Time Order" + System.getProperty("line.separator")).getBytes() ;

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx){
        ByteBuf message;
        for(int i = 0; i < 50; i++){
            // 向服务端发送消息
            message = Unpooled.buffer(req.length);
            message.writeBytes(req);
            ctx.writeAndFlush(message);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx,Object msg) throws Exception{

        String body = (String)msg;
        System.out.println("NOW is :" + body +"; the counter is:"+ ++counter);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx,Throwable cause){
        logger.warning("unexpected exception from downstream : " + cause.getMessage());
        ctx.close();
    }
}
