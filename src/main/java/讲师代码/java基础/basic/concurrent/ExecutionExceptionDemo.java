/**
 * 
 */
package 讲师代码.java基础.basic.concurrent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import 讲师代码.java基础.basic.multithreads.ThreadSubClassDemo;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 
 *
 * @author Guanying Piao
 *
 * 2018-06-23
 */
public class ExecutionExceptionDemo {

    private static Logger logger = LoggerFactory .getLogger(ThreadSubClassDemo.class);

    public ExecutionExceptionDemo() {}

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newSingleThreadExecutor(r -> new Thread(r, "executor"));
        Future<?> future = executorService.submit(() -> {
            try {
                throw new IllegalArgumentException("Real cause of this exception");
            } catch (Exception e) {
                throw new IllegalStateException("wrapper of real cause", e);
            }
        });

        try {
            future.get();
        } catch (InterruptedException e) {
            logger.warn("Got interrupted while waiting for other threads to stop", e);
        } catch (ExecutionException e) {
            logger.warn("Before unwrapping exception: {}", e.getMessage());
            logger.warn("Unwrapped once exception: {}", e.getCause().getMessage());
            Throwable t = e;
            while (t.getCause() != null) {
                t = t.getCause();
            }
            logger.warn("Thoroughly unwrapped exception: {}", t.getMessage());
        }
    }

}
