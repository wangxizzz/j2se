package 常用的工具类.guava.并发;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author wxi.wang
 * 18-12-1
 */
public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(4);
        int jobCounts = 3;
        List<CompletableFuture<Integer>> completableFutures = new ArrayList<>(jobCounts);
        MyMutableInt index = new MyMutableInt(0);
        for (int i = 0; i < jobCounts; i++) {
            CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> 100, service)
                    .thenApply((x) -> x + index.myIncrement())
                    .thenCompose(x -> CompletableFuture.supplyAsync(() -> x + index.myIncrement()))
                    .thenCompose(y -> CompletableFuture.supplyAsync(() -> y + index.myIncrement()));

            completableFutures.add(future);
        }
        // 遍历completableFutures，完成后执行回调
        for (CompletableFuture<Integer> c : completableFutures) {
            c.whenCompleteAsync((v ,t) -> {
                Optional.ofNullable(v).ifPresent((y) -> LOGGER.info("结果为{}",y));
                Optional.ofNullable(t).ifPresent((y) -> LOGGER.info("{}",y));
            }, service);
        }
        completableFutures.forEach((f) -> {
            try {
                f.get();
            } catch (InterruptedException | ExecutionException e) {
                LOGGER.warn("{}", e);
            }
        });
        service.shutdown();
    }
}
