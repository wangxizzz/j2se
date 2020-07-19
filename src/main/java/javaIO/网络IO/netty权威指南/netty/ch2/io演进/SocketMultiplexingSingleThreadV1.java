package javaIO.网络IO.netty权威指南.netty.ch2.io演进;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author wangxi created on 2020/7/19 20:44
 * @version v1.0
 *
 *  Socket多路复用器 单线程版,只用了一个 selector
 *
 *
 */
public class SocketMultiplexingSingleThreadV1 {

    // SocketNIO  non-blockingIO，socketIO,是由内核提供的机制

    // 此类的 NIO 是 new IO, JDK提供IO  {channel, ByteBuffer, Selector(多路复用器的包装)}
    private ServerSocketChannel ss = null;

    private Selector selector = null;

    private int port = 8080;

    public static void main(String[] args) throws Exception {
        new SocketMultiplexingSingleThreadV1().start();
    }

    public void initServer() throws Exception {
        ss = ServerSocketChannel.open();
        ss.bind(new InetSocketAddress(port));
        ss.configureBlocking(false);

        selector = Selector.open();
        ss.register(selector, SelectionKey.OP_ACCEPT);
    }

    public void start() throws Exception {
        initServer();
        System.out.println("服务端启动了，，，，");
        while (true) {
            /**
             * 此时客户端的accept事件与 数据读取事件，都是由单线执行的。
             */
            // 由selector告诉上层应用，哪个client有数据了
            while (selector.select() > 0) {     // 处理事件是串行的
                Set<SelectionKey> selectionKeys = selector.selectedKeys(); // 问过内核是否有可用的连接

                Iterator<SelectionKey> iterator = selectionKeys.iterator(); // 从多路复用器取出有效的连接
                while (iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();
                    iterator.remove();
                    if (selectionKey.isAcceptable()) {
                        handleAcceptable(selectionKey);
                    } else if (selectionKey.isReadable()) {

                        // client端断开连接，同样是触发 读事件，此时channel read的字节返回的为-1
                        handleReadable(selectionKey);
                    }
                }
            }
        }
    }

    private void handleAcceptable(SelectionKey selectionKey) {
        try {
            ServerSocketChannel server = (ServerSocketChannel)selectionKey.channel();
            SocketChannel client = server.accept();
            client.configureBlocking(false);
            ByteBuffer buffer = ByteBuffer.allocateDirect(4096);
            // 客户端channel注册到IO多路复用器
            client.register(selector, SelectionKey.OP_READ, buffer); // 此时selector中不止包含ServerSocketChannel,同时也包含SocketChannel对应的事件

            System.out.println("======================");
            System.out.println("新客户端：" + client.getRemoteAddress());
            System.out.println("======================");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleReadable(SelectionKey selectionKey) {
        SocketChannel client = (SocketChannel)selectionKey.channel();
        // 获取注册时的buffer
        ByteBuffer buffer = (ByteBuffer)selectionKey.attachment();

        buffer.clear();
        int read = 0;
        try {
            while (true) {
                read = client.read(buffer);
                if (read > 0) {
                    buffer.flip();
                    while (buffer.hasRemaining()) {
                        // 写出到client端
                        client.write(buffer);
                    }

                    buffer.clear();
                } else if (read == 0) {
                    break;
                }
                // 这里当client断开时，会产生死循环。根本原因是，client端channel断开时，
                // selector仍然会select可读事件，只是读取的数据为-1
                else if (read < 0) {
                    // 此时，关闭通道，防止空轮询。如果没有此分支，那么while(true)循环一直跳不出去，因为read返回-1,没有匹配任何break分支
                    client.close();
                    break;
                }

                //System.out.println("空轮询bug....");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
