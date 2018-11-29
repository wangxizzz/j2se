/**
 * 
 */
package 讲师代码.java基础.basic.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * 
 *
 * @author Guanying Piao
 *
 * 2018-06-30
 */
public class BioWebClient {

    private static Logger logger = LoggerFactory.getLogger(BioWebClient.class);
    
    public BioWebClient() {}
    
    public static void main(String[] args) {
        try (Socket socket = new Socket("127.0.0.1", 8080);
                PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
                InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);) {
            printWriter.println("hello");
            printWriter.println("I'm a client");
            printWriter.println("end");
            bufferedReader.lines().forEach(logger::info);
        } catch (Exception e) {
            logger.warn("Got exception while connect to server, error:{}", e.getMessage());
        }
    } 

}
