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
public class AtomicIntegerDemo {

    private static Logger logger = LoggerFactory.getLogger(AtomicIntegerDemo.class);
    
    public static void main(String[] args) {
        AtomicInteger threadIndex = new AtomicInteger(0);
        ExecutorService executorService = Executors.newFixedThreadPool(5,
                r -> new Thread(r, "thread-" + threadIndex.getAndIncrement()));
        int jobCount = 5;
        List<Future<?>> futures = new ArrayList<>(jobCount); 
        AtomicInteger counter = new AtomicInteger(0);
        for (int i = 0; i < jobCount; i++) {
            Future<?> future = executorService.submit(() -> {
                for (int j = 0; j < 1000; j++) {
                    counter.incrementAndGet();
                }
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
        logger.info("count:{}", counter.get());
        executorService.shutdown();
    }

}
