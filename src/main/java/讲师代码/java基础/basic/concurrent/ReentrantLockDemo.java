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
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 
 *
 * @author Guanying Piao
 *
 * 2018-06-30
 */
public class ReentrantLockDemo {

    private static Logger logger = LoggerFactory.getLogger(ReentrantLockDemo.class);
    
    public static void main(String[] args) {
        AtomicInteger threadIndex = new AtomicInteger(0);
        ExecutorService executorService = Executors.newFixedThreadPool(5,
                r -> new Thread(r, "thread-" + threadIndex.getAndIncrement()));
        int jobCount = 5;
        List<Future<?>> futures = new ArrayList<>(jobCount); 
        Lock lock = new ReentrantLock();
        for (int i = 0; i < jobCount; i++) {
            int jobIndex = i;
            Future<?> future = executorService.submit(() -> {
                boolean locked = false;
                long start = System.currentTimeMillis();
                try {
                    if (locked = lock.tryLock(1000L, TimeUnit.MILLISECONDS)) {
                        Thread.sleep(1000L);
                        logger.info("jobIndex:{} got lock, cost {} ms",
                                jobIndex, System.currentTimeMillis() - start);
                    } else {
                        logger.info("jobIndex:{} failed to get lock, cost {} ms",
                                jobIndex, System.currentTimeMillis() - start);
                    }
                } catch (InterruptedException e) {
                    logger.warn("Interrupted while waiting for lock");
                } finally {
                    if (locked) {
                        lock.unlock();
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
