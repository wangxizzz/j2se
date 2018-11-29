/**
 * 
 */
package 讲师代码.java基础.basic.concurrent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 
 *
 * @author Guanying Piao
 *
 * 2018-06-30
 */
public class CountDownLatchDemo {

    private static Logger logger = LoggerFactory.getLogger(CountDownLatchDemo.class);
    
    public static void main(String[] args) {
        AtomicInteger threadIndex = new AtomicInteger(0);
        ExecutorService executorService = Executors.newFixedThreadPool(5,
                r -> new Thread(r, "thread-" + threadIndex.getAndIncrement()));
        int jobCount = 5;
        List<Future<?>> futures = new ArrayList<>(jobCount); 
        CountDownLatch countDownLatch = new CountDownLatch(jobCount);
        for (int i = 0; i < jobCount; i++) {
            int jobIndex = i;
            Future<?> future = executorService.submit(() -> {
                long start = System.currentTimeMillis();
                try {
                    Thread.sleep(ThreadLocalRandom.current().nextLong(1000L));
                    logger.info("job:{} finished, cost {} ms",
                            jobIndex, System.currentTimeMillis() - start);
                } catch (InterruptedException e) {
                    logger.warn("Interrupted while sleeping");
                } finally {
                    countDownLatch.countDown();
                }
            });
            futures.add(future);
        }        
        try {
            countDownLatch.await();
            logger.info("all jobs done");
        } catch (InterruptedException e) {
            logger.warn("Failed to wait for jobs to end");
        }
        executorService.shutdown();
    }

}
