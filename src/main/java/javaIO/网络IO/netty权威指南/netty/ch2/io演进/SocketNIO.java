package javaIO.网络IO.netty权威指南.netty.ch2.io演进;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.LinkedList;
import java.util.List;

/**
 * @author wangxi created on 2020/7/19 20:00
 * @version v1.0
 *
 *  此时只是NIO的代码，并没有引入多路复用器。
 *  linux下的多路复用器的系统调用为：select, poll, epoll
 */
public class SocketNIO {
    public static void main(String[] args) throws Exception {
        List<SocketChannel> clients = new LinkedList<>();

        ServerSocketChannel ss = ServerSocketChannel.open();
        ss.bind(new InetSocketAddress(8080));

        ss.configureBlocking(false);

        while (true) {
            Thread.sleep(1000);
            SocketChannel client = ss.accept();   // 非阻塞.无连接，OS返回-1
            if (client == null) {
                System.out.println("null.......");
            } else {
                // 把客户端设置为非阻塞
                client.configureBlocking(false);
                int port = client.socket().getPort();
                System.out.println("client.... port = " + port);
                clients.add(client);
            }

            ByteBuffer buffer = ByteBuffer.allocateDirect(4096);
            for (SocketChannel c : clients) {    // 串行执行每个客户端
                // 写入buffer
                int num = c.read(buffer);  // 快速返回 0，-1（通道关闭），>0  // 不阻塞
                if (num > 0) {
                    buffer.flip();
                    byte[] aaa = new byte[buffer.limit()];
                    // 把数据读出到byte数组
                    buffer.get(aaa);

                    String s = new String(aaa);

                    System.out.println(c.socket().getPort() + ": " + s);
                    buffer.clear();
                }
            }
        }
    }

    /**
     * 上述代码分析：
     *
     * 一个main thread即做了accept事情，也做了数据IO事情。即netty中只有一个boss一般
     *
     * NIO的问题：
     *    假如有C10k个客户端连接，此时一个线程会 轮询所有client，那么此时复杂度就是 O(10k)，即会发起C10K次系统调用。
     *    此时如果有数据的client只有2个，那么真正有意义的通信 其实只是O(2)，只需发起2次read系统调用。
     *    据上述分析，会浪费大量的性能。资源浪费
     *
     * 解决：
     * 因此IO多路复用器Selector站出来了，由它来告诉上层应用，有哪些client是有事件的，进而返回Set<SelectionKey>
     *     此时上层线程只需 O(1) 进行select即可知道哪些client有数据了，这是由OS返回的。此时只有一次系统调用切换，相对于上面NIO的
     *     问题，节省了很多的资源。
     *
     * 多路复用器只告诉上层 client的IO状态，真正的IO操作还需要上层应用自己读取。此时数据有的通道，IO操作仍然是阻塞的，自己要去内核空间读取数据到应用空间
     */
}
