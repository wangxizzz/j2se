package javaIO.网络IO.netty权威指南.netty.ch2.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

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


        ServerSocket server = null;
        try{
            server = new ServerSocket(port);
            System.out.println("the time server is start in port :"+port);
            Socket socket = null;
            while (true) {
                socket = server.accept();
                // 每个连接 创建一个Thread处理
                Thread t = new Thread(new TimeserverHandler(socket, "thread-01"));
                t.start();
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
