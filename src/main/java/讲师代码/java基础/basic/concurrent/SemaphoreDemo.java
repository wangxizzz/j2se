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
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 
 *
 * @author Guanying Piao
 *
 * 2018-06-30
 */
public class SemaphoreDemo {

    private static Logger logger = LoggerFactory.getLogger(SemaphoreDemo.class);
    
    public static void main(String[] args) {
        AtomicInteger threadIndex = new AtomicInteger(0);
        ExecutorService executorService = Executors.newFixedThreadPool(5,
                r -> new Thread(r, "thread-" + threadIndex.getAndIncrement()));
        int jobCount = 5;
        List<Future<?>> futures = new ArrayList<>(jobCount); 
        Semaphore semaphore = new Semaphore(3);
        for (int i = 0; i < jobCount; i++) {
            int jobIndex = i;
            Future<?> future = executorService.submit(() -> {
                boolean acquired = false;
                long start = System.currentTimeMillis();
                try {
                    if (acquired = semaphore.tryAcquire(1000, TimeUnit.MILLISECONDS)) {
                        Thread.sleep(1000L);
                        logger.info("job:{} acquired permit, cost {} ms",
                                jobIndex, System.currentTimeMillis() - start);
                    } else {
                        logger.info("job:{} failed to acquired permit, cost {} ms",
                                jobIndex, System.currentTimeMillis() - start);
                    }
                } catch (InterruptedException e) {
                    logger.warn("Interrupted while waiting for permit");
                } finally {
                    if (acquired) {
                        semaphore.release();
                    }
                }
            });
            futures.add(future);
        }
        futures.forEach(future -> {
            try {
                future.get();
            } catch (InterruptedException | ExecutionException e) {
                logger.warn("Failed to wait for jobs to end");
            }
        });
        executorService.shutdown();
    }

}
