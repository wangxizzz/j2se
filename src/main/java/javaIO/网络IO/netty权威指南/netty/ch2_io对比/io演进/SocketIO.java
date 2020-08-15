package javaIO.网络IO.netty权威指南.netty.ch2_io对比.io演进;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author wangxi created on 2020/7/19 17:53
 * @version v1.0
 *
 *  BIO模型
 *
 */
public class SocketIO {
    public static void main(String[] args) throws Exception {
        ServerSocket server = new ServerSocket(8080);

        System.out.println("step 1 : new ServerSocket(8080)");

        Socket client = server.accept();  // 阻塞1

        System.out.println("step2 : client\t" + client.getPort());

        // 读数据
        InputStream inputStream = client.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        System.out.println(bufferedReader.readLine());  // 阻塞2

        while (true) {

        }
    }
}
