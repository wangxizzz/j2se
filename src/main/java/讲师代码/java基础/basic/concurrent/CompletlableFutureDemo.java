/**
 * 
 */
package 讲师代码.java基础.basic.concurrent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 
 *
 * @author Guanying Piao
 *
 * 2018-06-30
 */
public class CompletlableFutureDemo {

    private static Logger logger = LoggerFactory.getLogger(CompletlableFutureDemo.class);
    
    public static void main(String[] args) {
        AtomicInteger threadIndex = new AtomicInteger(0);
        ExecutorService executorService = Executors.newFixedThreadPool(5, r -> new Thread(r, "thread-" + threadIndex.getAndIncrement()));
        ExecutorService finishExecutorService = Executors.newSingleThreadExecutor(r -> new Thread(r, "finisher"));
        int jobCount = 5;
        List<CompletableFuture<String>> futures = new ArrayList<>(jobCount); 
        for (int i = 0; i < jobCount; i++) {
            CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
                try {
                    Thread.sleep(ThreadLocalRandom.current().nextLong(1000L));
                } catch (InterruptedException e) {
                    logger.warn("Interrupted while waiting for permit");
                }
                return System.currentTimeMillis();
            }, executorService).thenCompose(time -> {
                return CompletableFuture.supplyAsync(() -> {
                    try {
                        Thread.sleep(ThreadLocalRandom.current().nextLong(1000L));
                    } catch (InterruptedException e) {
                        logger.warn("Interrupted while waiting for permit");
                    }
                    return System.currentTimeMillis();
                }, executorService);
            }).thenApplyAsync(String::valueOf, finishExecutorService);

            futures.add(future);
        }
        CompletableFuture<?> allFuture = CompletableFuture.allOf(futures.toArray(new CompletableFuture[5]));
        allFuture.whenComplete((holder, t) ->  {
            if (t != null) {
                logger.warn("Got exception while processing:{}", t.getMessage());                
            } else {
                List<String> results = new ArrayList<>(jobCount);
                for (CompletableFuture<String> future : futures) {
                    try {
                        String result = future.get();
                        results.add(result);
                    } catch (InterruptedException | ExecutionException e) {
                        logger.warn("Unexpected exception:{}", e.getMessage());
                    }
                }
                logger.info("results are:{}", results);
            }
        });
        
        try {
            allFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            logger.warn("Failed to wait for jobs to end");
        }
        executorService.shutdown();
        finishExecutorService.shutdown();
    }

}
