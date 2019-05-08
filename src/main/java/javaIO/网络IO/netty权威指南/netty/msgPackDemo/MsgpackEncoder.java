package javaIO.网络IO.netty权威指南.netty.msgPackDemo;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.msgpack.MessagePack;

/**
 * Created by wangxi on 09/06/2018.
 */
public class MsgpackEncoder extends MessageToByteEncoder {

    @Override
    protected void encode(ChannelHandlerContext ctx,Object msg,ByteBuf bytebuf) throws Exception{
        try {
            MessagePack messagePack = new MessagePack();
            byte[] raw = messagePack.write(msg);
            bytebuf.writeBytes(raw);
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }
}
