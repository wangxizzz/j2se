package javaIO.网络IO.netty权威指南.netty.ch2.nio.空轮询问题;

import org.apache.commons.lang3.StringUtils;

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
 * Created by wangxi on 24/03/2018.
 */
public class TimeServerNioBug {

    private volatile boolean stop;
    Selector selector = null;

    public static void main(String[] args){

        new TimeServerNioBug().start();
    }

    /**
     * NIO详解：
     * https://segmentfault.com/a/1190000006824196
     * https://segmentfault.com/a/1190000005675241
     */
    public void start(){
        ServerSocketChannel acceptorSvr = null;
        try {
            int port = 8081;
            acceptorSvr = ServerSocketChannel.open();
            // 把serverChannel设置为非阻塞的
            acceptorSvr.configureBlocking(false);
            acceptorSvr.bind(new InetSocketAddress("127.0.0.1",port));
            // 创建一个选择器
            selector = Selector.open();
            // 将 channel 注册到 selector 中.
            // 通常我们都是先注册一个 OP_ACCEPT 事件, 然后在 OP_ACCEPT 到来时, 再将这个 Channel 的 OP_READ
            // 注册到 Selector 中.
            acceptorSvr.register(selector,SelectionKey.OP_ACCEPT);
            System.out.println("Timeserver start!!");

            SelectionKey key;
            // 接收关闭指令，不断的去轮询Selector
            while(!stop){
                // select()方法返回的值表示有多少个 Channel 可操作.
                // 此方法会一直阻塞当前Thread直到至少一个channel准备好或者设置超时时间或被interrupted.
                // 通过调用 select 方法, 阻塞地等待 channel I/O 可操作
                int select = selector.select();
                System.out.println("============");
                // 获取 I/O 操作就绪的 SelectionKey, 通过 SelectionKey 可以知道哪些 Channel 的哪类 I/O 操作已经就绪.
                Set<SelectionKey> selectionKeySet = selector.selectedKeys();
                Iterator<SelectionKey>  iterator = selectionKeySet.iterator();
                while(iterator.hasNext()){
                    System.out.println("进入SelectionKey的迭代");
                    key = iterator.next();
                    // 当获取一个 SelectionKey 后, 就要将它删除, 表示我们已经对这个 IO 事件进行了处理.
                    iterator.remove();
                    try{
                        handleInput(key);
                    }catch (Exception e){
                        e.printStackTrace();
                        if(key != null){
                            key.cancel();
                            if(key.channel() !=null){
                                key.channel().close();
                            }
                        }
                    }
                }

            }
            if(selector !=null){
                try{
                    selector.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void handleInput(SelectionKey key) throws IOException, InterruptedException {
        // is cancelled, its channel is closed, or its selector is closed
        if(key.isValid()){
            if(key.isAcceptable()){
                System.out.println("连接建立");
                // 当 OP_ACCEPT 事件到来时, 我们就有从 ServerSocketChannel 中获取一个 SocketChannel,
                // 代表客户端的连接
                // 注意, 在 OP_ACCEPT 事件中, 从 key.channel() 返回的 Channel 是 ServerSocketChannel.
                // 而在 OP_WRITE 和 OP_READ 中, 从 key.channel() 返回的是 SocketChannel.
                ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
                // 接收一个客户端连接SocketChannel(相当于建立一个连接)
                SocketChannel clientChannel  = ssc.accept();
                clientChannel.configureBlocking(false);
                //在 OP_ACCEPT 到来时, 再将这个 Channel 的 OP_READ 注册到 Selector 中.
                // 注意, 这里我们如果没有设置 OP_READ 的话, 即 interest set 仍然是 OP_CONNECT 的话, 那么 select 方法会一直直接返回.
                clientChannel.register(selector,SelectionKey.OP_READ);
            }
            if(key.isReadable()){
                SocketChannel clientChannel = (SocketChannel)key.channel();
                ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                int readBytes = clientChannel.read(readBuffer);
                // 从通道读取到字节，对字节进行解码。返回0，正常现象忽略
                if(readBytes > 0){
                    // Buffer切换为读取模式
                    readBuffer.flip();
                    byte[] bytes = new byte[readBuffer.remaining()];
                    readBuffer.get(bytes);
                    String body = new String(bytes,"UTF-8");
                    System.out.println("the time server receive order:"+body);

                    // 验证经典和多Reactor模型单线程阻塞问题
//                    System.out.println("等着....");
//                    Thread.sleep(20000);
//                    System.out.println("等待结束...");

                    // 上面同步等待，下面使用异步处理业务逻辑(操作DB,RPC调用)
                    System.out.println("等着....");
                    new Thread(() -> {
                        try {
                            // 表示业务调用需耗时20s,没返回值。
                            // 如果需要返回值，那么就可以利用回调CompletableFuture获取返回值，selector线程依旧不阻塞.
                            Thread.sleep(20000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }).start();
                    System.out.println("异步快速返回....");
                    doWrite(clientChannel,"the time server receive order:"+body);
                    // 返回-1，链路已关闭，关闭Channel，释放资源
                }
                // 空轮询bug
//                System.out.println("进入key.isReadable()");
//                else if(readBytes <0){
//                    System.out.println("链路关闭");
//                    key.cancel();
//                    clientChannel.close();
//                }
            }
        }
    }

    private void doWrite(SocketChannel sc, String response) throws IOException{
        if(StringUtils.isNotBlank(response)){
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            byteBuffer.put(response.getBytes());
            byteBuffer.flip();
            sc.write(byteBuffer);
            System.out.println("send to client:"+response);
        }
    }

    public void stop(){
        this.stop = true;
    }


}
