/**
 * 
 */
package 讲师代码.java基础.basic.concurrent;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
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
public class ListenableFutureDemo {

    private static Logger logger = LoggerFactory.getLogger(ListenableFutureDemo.class);
    
    public static void main(String[] args) {
        AtomicInteger threadIndex = new AtomicInteger(0);
        ExecutorService finisher = Executors.newSingleThreadExecutor(r -> new Thread(r, "finisher"));
        ExecutorService executorService = Executors.newFixedThreadPool(5,
                r -> new Thread(r, "thread-" + threadIndex.getAndIncrement()));
        ListeningExecutorService listeningExecutorService =
                MoreExecutors.listeningDecorator(executorService);
        int jobCount = 5;
        List<Future<?>> futures = new ArrayList<>(jobCount); 
        for (int i = 0; i < jobCount; i++) {
            int jobIndex = i;
            ListenableFuture<?> listenableFuture = listeningExecutorService.submit(() -> {
                try {
                    Thread.sleep(ThreadLocalRandom.current().nextLong(1000L));
                } catch (InterruptedException e) {
                    logger.warn("Interrupted while sleeping");
                }
            });
            listenableFuture.addListener(
                    () -> logger.info("job:{} finished", jobIndex), 
                    finisher);
            futures.add(listenableFuture);
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
