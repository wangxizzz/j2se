package reacitive.rxJava.rxJava反应式编程.业务实例.chapter5.c10k问题;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author wangxi created on 2020/6/14 16:33
 * @version v1.0
 *
 * 只能处理单个连接
 *
 * 让一个线程来处理所有的传入连接显然不具备扩展性，我们仅仅解决了C1（一个并发连接）问题。
 */
public class SingleThreadHTTP {

    public static final byte[] response = "OK".getBytes();

    public static void main(String[] args) throws IOException {
        final ServerSocket serverSocket = new ServerSocket(8080, 100);

        // 这里会发生阻塞(下面代码是长连接的体现)
        while (!Thread.currentThread().isInterrupted()) {
            Socket client = serverSocket.accept();

            System.out.println("接收外部请求。。。");
            handle(client);
        }

        // 短连接的体现
//        while (true) {
//            Socket client = serverSocket.accept();
//            System.out.println("接收外部请求。。。");
//            handle(client);
//        }

    }

    private static void handle(Socket client) {
        try {
            // 单一线程持续接收请求(长连接)，长连接会导致只能处理一个client，其他全部阻塞
            while (!Thread.currentThread().isInterrupted()) {
                readFullRequest(client);
                client.getOutputStream().write(response);
            }

            // 这种是短连接，读完了就释放了连接。该单一线程就会去处理其他client的连接
//            readFullRequest(client);
//            client.getOutputStream().write(response);
        } catch (Exception e) {
            e.printStackTrace();
            IOUtils.closeQuietly(client);
        }
    }

    private static void readFullRequest(Socket client) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
        // 这里会发生阻塞
        String line = reader.readLine();
        while (StringUtils.isNotBlank(line)) {
            System.out.println(line);
            line = reader.readLine();
        }
    }
}
