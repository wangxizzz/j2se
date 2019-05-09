package javaIO.网络IO.原生态IO;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * <Description>
 *
 * @author wangxi
 */
public class TransferFileByChannel {
    public static void nio() {
        RandomAccessFile aFile = null;
        try {

            aFile = new RandomAccessFile("src/nio.txt", "rw");
            // channel获取数据
            FileChannel fileChannel = aFile.getChannel();
            // 初始化Buffer，设定Buffer每次可以存储数据量
            // 创建的Buffer是1024byte的，如果实际数据本身就小于1024，那么limit就是实际数据大小
            ByteBuffer buf = ByteBuffer.allocate(1024);
            // channel中的数据写入Buffer
            int bytesRead = fileChannel.read(buf);
            System.out.println(bytesRead);

            while (bytesRead != -1) {
                // Buffer切换为读取模式
                buf.flip();
                // 读取数据
                while (buf.hasRemaining()) {
                    System.out.print((char) buf.get());
                }
                // 清空Buffer区
                buf.compact();
                // 继续将数据写入缓存区。read()不会阻塞，只是把数据读取到buffer中，如果Channel无数据直接返回
                bytesRead = fileChannel.read(buf);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (aFile != null) {
                    aFile.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args) {
        TransferFileByChannel.nio();
    }
}

