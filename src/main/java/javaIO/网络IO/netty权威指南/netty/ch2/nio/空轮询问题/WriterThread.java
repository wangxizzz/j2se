package javaIO.网络IO.netty权威指南.netty.ch2.nio.空轮询问题;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @author wangxi created on 2020/7/19 15:39
 * @version v1.0
 */
public class WriterThread extends Thread {
    private SocketChannel clientChannel;
    public WriterThread(SocketChannel clientChannel) {
        this.clientChannel = clientChannel;
    }

    public void run() {
        try {
            writeClient(clientChannel);
            System.out.println("5. SERVER WRITE DATA TO client - " + clientChannel.socket().getInetAddress());
        } catch (IOException e) {
            System.err.println("5. SERVER WRITE DATA FAILED : " + e);
        }
    }

    public void writeClient(SocketChannel channel) throws IOException {
        try {
            ByteBuffer buffer = ByteBuffer.wrap("zwxydfdssdfsd".getBytes());
            int total = buffer.limit();

            int totalWrote = 0;
            int nWrote = 0;

            while ((nWrote = channel.write(buffer)) >= 0) {
                totalWrote += nWrote;
                if (totalWrote == total) {
                    break;
                }
            }
        } catch (IOException e) {
            System.err.println("IO Error : " + e.getMessage());
            channel.close();
        }
    }
}
