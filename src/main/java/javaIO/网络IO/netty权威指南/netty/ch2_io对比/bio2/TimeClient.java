package javaIO.网络IO.netty权威指南.netty.ch2_io对比.bio2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by wangxi on 21/03/2018.
 */
public class TimeClient {
    public static void main(String[] args){
        Socket socket = null;
        BufferedReader in = null;
        PrintWriter out = null;

        try{

            socket = new Socket("127.0.0.1",8080);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(),true);
            out.println("query time order");
            System.out.println(" send order to server succeed");
            String resp = in.readLine();
            System.out.println(resp);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(socket != null){
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                socket = null;
            }
            if(out != null){
                out.close();
                out = null;
            }
            if(in!=null){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                in = null;
            }
        }
    }
}
