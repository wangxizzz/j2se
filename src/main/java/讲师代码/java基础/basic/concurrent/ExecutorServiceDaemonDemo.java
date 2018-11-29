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
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 
 *
 * @author Guanying Piao
 *
 * 2018-06-23
 */
public class ExecutorServiceDaemonDemo {

    private static Logger logger = LoggerFactory .getLogger(ThreadSubClassDemo.class);

    public ExecutorServiceDaemonDemo() {}

    public static void main(String[] args) {
        
        int threadCount = 5;
        int jobCount = 10;
        AtomicInteger threadCounter = new AtomicInteger();
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount, r -> {
            Thread thread = new Thread(r, "executor-" + threadCounter.getAndIncrement());
            thread.setDaemon(true);
            return thread;
        });
        Future<?>[] futures = new Future[jobCount];
        
        for (int i = 0; i < jobCount; i++) {
            int jobIndex = i;
            Future<?> future = executorService.submit(() -> {
                for (int j = 0; j < 10; j++) {
                    logger.info("{} th job's {}th number", jobIndex, j);
                }
            });
            futures[i] = future;
        }
        
        try {
            for (Future<?> future : futures) {
                future.get();
            }
        } catch (InterruptedException e) {
            logger.warn("Got interrupted while waiting for other threads to stop", e);
        } catch (ExecutionException e) {
            Throwable t = e;
            while (t.getCause() != null) {
                t = t.getCause();
            }
            logger.warn("Got business exception", t);
        }
        
//        executorService.shutdown();
    }

}
