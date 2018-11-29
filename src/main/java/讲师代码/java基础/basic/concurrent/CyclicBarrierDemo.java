/**
 * 
 */
package 讲师代码.java基础.basic.concurrent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutionException;
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
public class CyclicBarrierDemo {

    private static Logger logger = LoggerFactory.getLogger(CyclicBarrierDemo.class);
    
    public static void main(String[] args) {
        AtomicInteger threadIndex = new AtomicInteger(0);
        ExecutorService executorService = Executors.newFixedThreadPool(5,
                r -> new Thread(r, "thread-" + threadIndex.getAndIncrement()));
        int jobCount = 5;
        List<Future<?>> futures = new ArrayList<>(jobCount); 
        CyclicBarrier cyclicBarrier = new CyclicBarrier(jobCount);
        for (int i = 0; i < jobCount; i++) {
            int jobIndex = i;
            Future<?> future = executorService.submit(() -> {
                long start = System.currentTimeMillis();
                try {
                    Thread.sleep(ThreadLocalRandom.current().nextLong(1000L));
                    logger.info("job:{} stage 1 finished, cost {} ms",
                            jobIndex, System.currentTimeMillis() - start);
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    logger.warn("Interrupted while sleeping");
                } catch (BrokenBarrierException e) {
                    logger.warn("BrokenBarrier, abort...");
                }
                logger.info("begin stage 2 job...");
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
