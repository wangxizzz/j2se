package javaIO.网络IO.netty权威指南.netty.ch2_io对比.io演进;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author wangxi created on 2020/7/19 22:24
 * @version v1.0
 */
public class SocketMultiplexingMultiThread {

    private ServerSocketChannel ss = null;

    private Selector selector1 = null;

    private Selector selector2 = null;

    private Selector selector3 = null;

    private int port = 8080;

    public static void main(String[] args) throws Exception {
        SocketMultiplexingMultiThread service = new SocketMultiplexingMultiThread();
        service.initServer();
        Thread t1 = new NioThread(service.selector1, 2);
        Thread t2 = new NioThread(service.selector2);
        Thread t3 = new NioThread(service.selector3);

        t1.start();

        Thread.sleep(1000);

        t2.start();
        t3.start();

        Thread.currentThread().join();
    }

    public void initServer() throws Exception {
        ss = ServerSocketChannel.open();

        ss.bind(new InetSocketAddress(port));
        ss.configureBlocking(false);

        selector1 = Selector.open();
        selector2 = Selector.open();
        selector3 = Selector.open();

        ss.register(selector1, SelectionKey.OP_ACCEPT);
    }


    public static class NioThread extends Thread {

        public static AtomicInteger index = new AtomicInteger(0);
        public static List<BlockingQueue<SocketChannel>> queues;

        private Selector selector;
        private static int selectorNum;
        private boolean boss;
        private boolean worker;

        private int id = 0;

        NioThread(Selector selector, int n) {
            this.selector = selector;
            selectorNum = n;
            boss = true;
            // boss thread持有两个worker thread

            queues = new LinkedList<>();
            for (int i = 0; i < selectorNum; i++) {
                queues.add(new LinkedBlockingQueue<>());
            }

            System.out.println("boss 启动");
        }

        NioThread(Selector selector) {
            this.selector = selector;
            id = index.getAndIncrement() % selectorNum;

            worker = true;
            System.out.println("worker " + id + "启动");
        }

        @Override
        public void run() {
            /**
             * boss selector上只注册了 ServerSocketChannel，而且只监听OP_ACCEPT事件
             *
             * worker selector上注册了客户端channel，只监听了 OP_READ事件
             */
            try {
               while (true) {
                   // 只有boos thread绑定了 ServerSocketChannel,client端channel并没注册到boss selector上
                   // ，并且注册了OP_ACCEPT事件,因此可以select出 accept事件。
                   // worker thread只能select出读写事件，因为并没有注册OP_ACCEPT事件
                   while (selector.select(10) > 0) {    // 注意：selector.select()是一个阻塞操作，设置超时时间，在过了时间不会阻塞
                       Set<SelectionKey> selectionKeys = selector.selectedKeys();
                       Iterator<SelectionKey> iterator = selectionKeys.iterator();
                       while (iterator.hasNext()) {
                           SelectionKey selectionKey = iterator.next();
                           iterator.remove();

                           if (selectionKey.isAcceptable()) {
                               // boss不想把client注册自己的selector上
                               System.out.println("thread name = " + Thread.currentThread().getName());
                               handleAcceptable(selectionKey);

                           } else if (selectionKey.isReadable()) {
                               System.out.println("thread name = " + Thread.currentThread().getName());
                               handleReadable(selectionKey);
                           }
                       }
                   }

                   if (worker && !queues.get(id).isEmpty()) {
                       ByteBuffer buffer = ByteBuffer.allocateDirect(4096);
                       SocketChannel client = queues.get(id).take();

                       client.configureBlocking(false);
                       client.register(selector, SelectionKey.OP_READ, buffer);
                       System.out.println("====================");
                       System.out.println("新客户端 ：" + client.getRemoteAddress() + "分配到worker: " + (id));
                       System.out.println("====================");
                   }
               }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // boss thread接收accept,并分配到worker
        private void handleAcceptable(SelectionKey selectionKey) {
            try {
                ServerSocketChannel server = (ServerSocketChannel)selectionKey.channel();
                SocketChannel client = server.accept();
                // 把建立连接的client放入 队列中

                // 基于轮询方式放入2个worker
                int num = index.getAndIncrement() % selectorNum;
                queues.get(num).put(client);
            } catch (Exception e) {
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
}
