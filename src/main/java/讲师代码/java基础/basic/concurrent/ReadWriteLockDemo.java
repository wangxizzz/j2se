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
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 
 *
 * @author Guanying Piao
 *
 * 2018-06-30
 */
public class ReadWriteLockDemo {

    private static Logger logger = LoggerFactory.getLogger(ReadWriteLockDemo.class);
    
    public static void main(String[] args) {
        AtomicInteger threadIndex = new AtomicInteger(0);
        ExecutorService executorService = Executors.newFixedThreadPool(5,
                r -> new Thread(r, "thread-" + threadIndex.getAndIncrement()));
        int jobCount = 50;
        List<Future<?>> futures = new ArrayList<>(jobCount); 
        ReadWriteLock lock = new ReentrantReadWriteLock();
        Lock writeLock = lock.writeLock();
        Lock readLock = lock.readLock();
        for (int i = 0; i < jobCount; i++) {
            int jobIndex = i;
            Future<?> future = executorService.submit(() -> {
                long start = System.currentTimeMillis();
                try {
                    writeLock.lock();
                    logger.info("jobIndex:{} got write lock, cost {} ms",
                            jobIndex, System.currentTimeMillis() - start);
                    Thread.sleep(1000L);                    
                } catch (InterruptedException e) {
                    logger.warn("Interrupted while waiting for lock");
                } finally {
                    writeLock.unlock();
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
        futures.clear();
        for (int i = 0; i < jobCount; i++) {
            int jobIndex = i;
            Future<?> future = executorService.submit(() -> {
                long start = System.currentTimeMillis();
                try {
                    readLock.lock();
                    logger.info("jobIndex:{} got read lock, cost {} ms",
                            jobIndex, System.currentTimeMillis() - start);
                    Thread.sleep(1000L);                    
                } catch (InterruptedException e) {
                    logger.warn("Interrupted while waiting for lock");
                } finally {
                    readLock.unlock();
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
