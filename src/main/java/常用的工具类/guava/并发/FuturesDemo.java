/**
 * 
 */
package 常用的工具类.guava.并发;

import com.google.common.base.Function;
import com.google.common.util.concurrent.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;


public class FuturesDemo {

    private static Logger logger = LoggerFactory.getLogger(FuturesDemo.class);
    
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        AtomicInteger threadIndex = new AtomicInteger(0);
        // 定义一个定长线程池
        ExecutorService executorService = Executors.newFixedThreadPool(5, r -> new Thread(r, "thread-" + threadIndex.getAndIncrement()));
        //
        ListeningExecutorService listeningExecutorService = MoreExecutors.listeningDecorator(executorService);
        ExecutorService finishExecutorService = Executors.newSingleThreadExecutor(r -> new Thread(r, "finisher"));
        int jobCount = 2;
        List<ListenableFuture<String>> listenableFutures = new ArrayList<>(jobCount);
        for (int i = 0; i < jobCount; i++) {
            
            ListenableFuture<Long> firstFuture = listeningExecutorService.submit(() -> {
                try {
                    Thread.sleep(ThreadLocalRandom.current().nextLong(1000L));
                } catch (InterruptedException e) {
                    logger.warn("Interrupted while waiting for permit");
                }
                return 1L;
            });
            
            ListenableFuture<Long> secondFuture = Futures.transformAsync(firstFuture, new AsyncFunction<Long, Long>() {
                @Override
                public ListenableFuture<Long> apply(Long input) throws Exception {
                    return listeningExecutorService.submit(() -> {
                        try {
                            Thread.sleep(ThreadLocalRandom.current().nextLong(1000L));
                        } catch (InterruptedException e) {
                            logger.warn("Interrupted while waiting for permit");
                        }
                        return 2L;
                    });
                }                
            }, MoreExecutors.directExecutor());
            
            ListenableFuture<String> thirdFuture = Futures.transform(secondFuture, new Function<Long, String>() {
                @Override
                public String apply(Long input) {
                    return input + "aaa";
                }
            }, finishExecutorService);

            listenableFutures.add(thirdFuture);
        }
        logger.info("==================================");

        ListenableFuture<List<String>> allFuture = Futures.successfulAsList(listenableFutures);
        Futures.addCallback(allFuture, new FutureCallback<List<String>>() {
            @Override
            public void onSuccess(List<String> result) {
                logger.info("job done times:{}", result);
            }
            @Override
            public void onFailure(Throwable t) {
                logger.warn("Got exception:{}", t.getMessage());
            }
        }, finishExecutorService);
        
        try {
            logger.info("执行get() is {}", allFuture.get());
        } catch (InterruptedException | ExecutionException e) {
            logger.warn("Failed to wait for jobs to end");
        }
        executorService.shutdown();
        finishExecutorService.shutdown();
    }

}
