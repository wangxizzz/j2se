package javaIO.网络IO.netty权威指南.netty.ch2_io对比.nio.空轮询问题;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * @author wangxi created on 2020/7/19 15:38
 * @version v1.0
 */
public class TestServer {
    private static final long SLEEP_PERIOD = 5000L; // 5 seconds
    private static final int BUFFER_SIZE = 8192;
    private int port;

    public TestServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) throws Throwable {
//        if (args.length < 1) {
//            System.err.println("Usage : java TestServer <port>");
//            System.exit(0);
//        }

        new TestServer(Integer.parseInt("8080")).start();
    }

    public void start() throws Throwable {
        ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);

        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.configureBlocking(false);
        ServerSocket server = serverChannel.socket();
        server.bind(new InetSocketAddress(port));

        Selector selector = Selector.open();
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);
        SocketChannel clientChannel = null;

        System.out.println("0. SERVER STARTED TO LISTEN");
        boolean writeNow = false;

        while (true) {
            try {
                // wait for selection
                int numKeys = selector.select();

                if (numKeys == 0) {
                    System.err.println("select wakes up with zero!!!");
                }

                Iterator it = selector.selectedKeys().iterator();
                while (it.hasNext()) {
                    SelectionKey selected = (SelectionKey) it.next();
                    int ops = selected.interestOps();

                    try {
                        // process new connection
                        if ((ops & SelectionKey.OP_ACCEPT) != 0) {
                            clientChannel = serverChannel.accept();
                            clientChannel.configureBlocking(false);

                            // register channel to selector
                            clientChannel.register(selector, SelectionKey.OP_READ, null);
                            System.out.println("2. SERVER ACCEPTED AND REGISTER READ OP : client - " + clientChannel.socket().getInetAddress());
                        }

                        if ((ops & SelectionKey.OP_READ) != 0) {
                            // read client message
                            System.out.println("3. SERVER READ DATA FROM client - " + clientChannel.socket().getInetAddress());
                            readClient((SocketChannel) selected.channel(), buffer);

                            // deregister OP_READ
//                            System.out.println("PREV SET : " + selected.interestOps());
                            // 不能取消读事件，否则就只能读取一次客户端数据
//                            //selected.interestOps(selected.interestOps() & ~SelectionKey.OP_READ);
//                            System.out.println("NEW SET : " + selected.interestOps());

                            //Thread.sleep(SLEEP_PERIOD * 2);
                            //new WriterThread(clientChannel).start();
                        }

                    } finally {
                        // remove from selected key set
                        it.remove();
                    }
                }
            } catch (IOException e) {
                System.err.println("IO Error : " + e.getMessage());
            }
        }
    }
    public void readClient(SocketChannel channel, ByteBuffer buffer) throws IOException {
        try {
            buffer.clear();

            int nRead = channel.read(buffer);

//            if (nRead < 0) {
//                channel.close();
//                return;
//            }

            if (buffer.position() != 0) {
                int size = buffer.position();
                buffer.flip();
                byte[] bytes = new byte[size];
                buffer.get(bytes);
                System.out.println("RECVED : " + new String(bytes));
            }
        } catch (IOException e) {
            System.err.println("IO Error : " + e.getMessage());
            channel.close();
        }
    }
}
