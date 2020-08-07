package javaIO.网络IO.netty权威指南.netty.ch2.io演进;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author wangxi created on 2020/7/20 22:54
 * @version v1.0
 *  netty IO是同步的，处理时异步的(返回Future)
 */
public class NettyNIO {
    public static void main(String[] args) {
        // 参数为1，表示这个EventLoopGroup 有2个thread, boss中的thread数设置大于1，其实启动时，也只会用一个thread，否则会有惊群效应
        NioEventLoopGroup boss = new NioEventLoopGroup(2);
        // group里有两个thread，每个EventLoop里有一个Selector
        NioEventLoopGroup worker = new NioEventLoopGroup(2);

        ServerBootstrap bootstrap = new ServerBootstrap();

        try {
            // 如果两个都是boss，那么就相当于SocketMultiplexingSingleThreadV1这个版本，
            // 单个thread及处理连接(此处是一个线程池版的Selector)，也处理IO事件，此时属于组合模型
            bootstrap.group(boss, worker)
                     .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel ch) {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new MyInbound());
                        }
                    });
            ChannelFuture channelFuture = bootstrap.bind(8080).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class MyInbound extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ctx.write(msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }
}
