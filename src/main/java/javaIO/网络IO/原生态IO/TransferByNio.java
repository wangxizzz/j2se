package javaIO.网络IO.原生态IO;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

/**
 * <Description>
 * 参考：https://segmentfault.com/a/1190000005675241
 *
 * 利用了sendFile系统调用
 *
 * @author wangxi
 */
public class TransferByNio {
    public static void trainforNio() {
        RandomAccessFile fromFile=null;
        RandomAccessFile toFile=null;
        try {

            fromFile = new RandomAccessFile("src/nio.txt", "rw");
            // channel获取数据
            FileChannel fromChannel = fromFile.getChannel();
            toFile = new RandomAccessFile("src/toFile.txt", "rw");
            FileChannel toChannel = toFile.getChannel();
            System.out.println(toChannel.size());
            //position处开始向目标文件写入数据,这里是toChannel
            long position = toChannel.size();
            long count = fromChannel.size();
            // 也可以使用fromFile.transferTo
            toChannel.transferFrom(fromChannel, position, count);
            System.out.println(toChannel.size());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fromFile != null) {
                    fromFile.close();
                }
                if (toFile != null) {
                    toFile.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

