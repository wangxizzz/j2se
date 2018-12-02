package 常用的工具类.guava.并发;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author wxi.wang
 * 18-12-1
 */
public class Test {
    @org.junit.Test
    public void test01() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> 1)
                .thenApply(i -> Integer.sum(i, 10))
                .whenComplete((v, t) -> {
                    System.out.println(v);
                });

    }
}
