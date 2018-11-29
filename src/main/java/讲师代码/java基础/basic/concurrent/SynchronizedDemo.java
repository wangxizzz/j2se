/**
 * 
 */
package 讲师代码.java基础.basic.concurrent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
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
 * 2018-07-01
 */
public class SynchronizedDemo {

    private static Logger logger = LoggerFactory.getLogger(SynchronizedDemo.class);
    
    public static void main(String[] args) {
        Object lock = new Object();
        AtomicInteger threadIndex = new AtomicInteger(0);
        ExecutorService executorService = Executors.newFixedThreadPool(5, r -> new Thread(r, "thread-" + threadIndex.getAndIncrement()));
        int jobCount = 5;
        List<Future<?>> futures = new ArrayList<>(jobCount); 
        for (int i = 0; i < jobCount; i++) {
            int jobIndex = i;
            Future<?> future = executorService.submit(() -> {
                long start = System.currentTimeMillis();
                synchronized (lock) {
                    try {
                        Thread.sleep(1000L);
                    } catch (InterruptedException e) {
                        logger.warn("Interrupted while sleeping for job:{}", jobIndex);
                    }
                }
                logger.info("jobIndex:{} cost {} ms", jobIndex, System.currentTimeMillis() - start);
            });
            futures.add(future);
        }
        futures.forEach(future -> {
            try {
                future.get();
            } catch (InterruptedException | ExecutionException e) {
                logger.warn("Failed to wait for jobs end", e.getMessage());
            }
        });
        executorService.shutdown();
    }

}
