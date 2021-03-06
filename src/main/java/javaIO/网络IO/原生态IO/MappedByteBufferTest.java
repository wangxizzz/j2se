package javaIO.网络IO.原生态IO;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Scanner;

/**
 * @author wangxi created on 2020/9/12 19:49
 * @version v1.0
 *
 * https://blog.csdn.net/qq_41969879/article/details/81629469
 *
 * 从代码层面上看，从硬盘上将文件读入内存，都要经过文件系统进行数据拷贝，并且数据拷贝操作是由文件系统和硬件驱动实现的，理论上来说，拷贝数据的效率是一样的。
 * 但是通过内存映射的方法访问硬盘上的文件，效率要比read和write系统调用高，这是为什么？
 *
 * read()是系统调用，首先将文件从硬盘拷贝到内核空间的一个缓冲区，再将这些数据拷贝到用户空间，实际上进行了两次数据拷贝；
 * map()也是系统调用，但没有进行数据拷贝，当缺页中断发生时，直接将文件从硬盘拷贝到用户空间，只进行了一次数据拷贝。
 * 所以，采用内存映射的读写效率要比传统的read/write性能高。
 */
public class MappedByteBufferTest {
    public static void main(String[] args) {
        File file = new File("/Users/wangxi/ideaProjects/j2se/src/main/java/javaIO/网络IO/原生态IO/TransferByNio.java");
        long len = file.length();
        byte[] ds = new byte[(int) len];

        try {
            MappedByteBuffer mappedByteBuffer = new RandomAccessFile(file, "r")
                    .getChannel()
                    .map(FileChannel.MapMode.READ_ONLY, 0, len);
            for (int offset = 0; offset < len; offset++) {
                byte b = mappedByteBuffer.get();
                ds[offset] = b;
            }

            Scanner scan = new Scanner(new ByteArrayInputStream(ds)).useDelimiter(" ");
            while (scan.hasNext()) {
                System.out.print(scan.next() + " ");
            }

        } catch (IOException e) {}
    }

}
