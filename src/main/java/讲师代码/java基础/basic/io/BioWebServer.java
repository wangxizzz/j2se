/**
 * 
 */
package 讲师代码.java基础.basic.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 
 *
 * @author Guanying Piao
 *
 * 2018-06-30
 */
public class BioWebServer {

    private static Logger logger = LoggerFactory .getLogger(BioWebServer.class);
    
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        Future<?> future = null;
        try (ServerSocket serverSocket = new ServerSocket(8080)) {            
            while (true) {
                Socket socket = serverSocket.accept();
                future = executorService.submit(() -> {
                    try (InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
                            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true)) {
                        logger.info("begin to print client's request");
                        String line = null;
                        while (!"end".equalsIgnoreCase(line = bufferedReader.readLine())) {
                            logger.info(line);
                        }
                        logger.info("end to print client's request");
                        printWriter.println("Got your request, thank you!");
                    } catch (Exception e) {
                        logger.warn("Got exception while processing client's request, error:{}", e.getMessage());
                    } finally {
                        try {
                            socket.close();
                        } catch (IOException e) {
                            logger.warn("Failed to close socket");
                        }
                    }
                });
                break;
            }
        } catch (IOException e) {
            throw new IllegalStateException("Failed to bind to 8080");
        }    
        try {
            future.get();
        } catch (InterruptedException | ExecutionException e) {
            logger.warn("Failed to wait for job to end", e.getMessage());
        }
        executorService.shutdown();
    }
    
}
