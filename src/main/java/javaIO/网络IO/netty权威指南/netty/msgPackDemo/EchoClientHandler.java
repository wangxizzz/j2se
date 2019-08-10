package javaIO.网络IO.netty权威指南.netty.msgPackDemo;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by wangxi on 09/06/2018.
 */
public class EchoClientHandler extends ChannelInboundHandlerAdapter {

    private final int sendNumber;
    public EchoClientHandler(int sendNumber) {
        this.sendNumber = sendNumber;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx){
        UserInfo[] infos = userInfos();

        for(UserInfo userInfo:infos){
            ctx.write(userInfo);
        }
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx,Object msg) throws Exception{
        System.out.println("Client receive the msgpack message : " + msg);
        //ctx.write(msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception{
        ctx.flush();
    }

    private UserInfo[] userInfos() {
        UserInfo[] userInfos = new UserInfo[sendNumber];

        UserInfo userInfo = null;
        for(int i=0;i < sendNumber;i++){
            userInfo = new UserInfo();
            userInfo.setAge(String.valueOf(i));
            userInfo.setName("ABCDEFG ---->" + i);
            userInfos[i] = userInfo;
        }
        return userInfos;
    }
}
