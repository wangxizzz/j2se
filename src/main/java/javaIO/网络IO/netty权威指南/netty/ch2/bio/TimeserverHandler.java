package javaIO.网络IO.netty权威指南.netty.ch2.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;

/**
 * Created by wangxi on 21/03/2018.
 */
public class TimeserverHandler implements Runnable {

    private Socket socket;
    private String threadName;
    public TimeserverHandler(Socket socket, String threadName) {
        this.socket = socket;
        this.threadName = threadName;
    }
    public void run(){
        BufferedReader in = null;
        PrintWriter out = null;
        try{
            in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            out = new PrintWriter(this.socket.getOutputStream(),true);
            String currentTime = null;
            String body = null;
            while(true){
                body = in.readLine();
                if(body == null){
                    break;
                }
                System.out.println(threadName + " : " + "The time server receive order :" + body);
                currentTime = "Query time order".equalsIgnoreCase(body) ? new Date(System.currentTimeMillis()).toString():"bad order";
                out.println(threadName + " : " + currentTime);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if(in!=null)
                    in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(out != null){
                out.close();
                out=null;
            }
           if(this.socket != null){
               try {
                   this.socket.close();
               } catch (IOException e) {
                   e.printStackTrace();
               }
               this.socket = null;
           }
        }
    }
}
