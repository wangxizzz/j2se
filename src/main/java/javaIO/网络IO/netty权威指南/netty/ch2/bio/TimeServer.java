package javaIO.网络IO.netty权威指南.netty.ch2.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by wangxi on 21/03/2018.
 */
public class TimeServer {

    public static void main(String[] args) throws IOException{
        int port = 8080;
        if(args != null && args.length >0 ){
            try{
                port = Integer.valueOf(args[0]);
            }catch (NumberFormatException e){

            }
        }

        AtomicInteger threadCounter = new AtomicInteger(1);
        AtomicInteger clientCounter = new AtomicInteger(1);
        ServerSocket server = null;
        try{
            server = new ServerSocket(port);
            System.out.println("the time server is start in port :"+port);
            Socket socket = null;
            socket = server.accept();   // 阻塞点1   只能接收一个请求
            System.out.println("client " +  clientCounter.getAndIncrement() + " connect success!");

            while (true) {
                //socket = server.accept();   // 阻塞点1  可以接收多个请求
//                System.out.println("client " +  clientCounter.getAndIncrement() + " connect success!");
//                // 每个连接 创建一个Thread处理
//                Thread t = new Thread(new TimeserverHandler(socket, "thread-" + threadCounter.getAndIncrement()));
//                t.start();
            }
        }finally {
            if(server!=null){
                System.out.println("the time server close");
                server.close();
                server = null;
            }
        }
    }
}
