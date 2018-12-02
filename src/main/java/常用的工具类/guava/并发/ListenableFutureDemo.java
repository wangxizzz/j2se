/**
 * 
 */
package 常用的工具类.guava.并发;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;


public class ListenableFutureDemo {

    private static Logger logger = LoggerFactory.getLogger(ListenableFutureDemo.class);
    
    public static void main(String[] args) {
        AtomicInteger threadIndex = new AtomicInteger(0);
        // 定义一个单线程池
        ExecutorService finisher = Executors.newSingleThreadExecutor((r) -> {
            // 给线程命名
            Thread t = new Thread(r, "finisher");
            return t;
        });
        // 定义一个５定长线程池
        ExecutorService executorService = Executors.newFixedThreadPool(5,
                r -> new Thread(r, "thread-" + threadIndex.getAndIncrement()));

        ListeningExecutorService listeningExecutorService =
                MoreExecutors.listeningDecorator(executorService);
        int jobCount = 5;
        List<Future<?>> futures = new ArrayList<>(jobCount); 
        for (int i = 0; i < jobCount; i++) {
            int jobIndex = i;
            // 提交任务
            ListenableFuture<?> listenableFuture = listeningExecutorService.submit(() -> {
                try {
                    Thread.sleep(ThreadLocalRandom.current().nextLong(1000L));
                } catch (InterruptedException e) {
                    logger.warn("Interrupted while sleeping");
                }
            });
            // 给任务设置监听
            listenableFuture.addListener(
                    () -> logger.info("job:{} finished", jobIndex), 
                    finisher);
            futures.add(listenableFuture);
        }
        // 把每个任务的值取出来
        futures.forEach(future -> {
            try {
                logger.info("future is {}", future.get());
            } catch (InterruptedException | ExecutionException e) {
                logger.warn("Failed to wait for jobs to end");
            }
        });
        executorService.shutdown();
        finisher.shutdown();
    }

}
