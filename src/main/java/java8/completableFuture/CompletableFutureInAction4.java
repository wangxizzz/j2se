package java8.completableFuture;

import org.junit.Test;

import java.util.concurrent.CompletableFuture;


public class CompletableFutureInAction4 {

    public static void main(String[] args) throws InterruptedException {

        CompletableFuture.supplyAsync(() -> 1)
                // thenApply()会对Future返回的值执行动态转换，这类似于Observable.map()
                .thenApply(i -> Integer.sum(i, 10))
                .whenComplete((v, t) -> System.out.println(v));  // v代表value，t表示异常

        CompletableFuture.supplyAsync(() -> 1)
                // 多了一个异常的处理t
                .handle((v, t) -> Integer.sum(v, 10))
                .whenComplete((v, t) -> System.out.println(v))
                .thenRun(System.out::println);  // 可以进一步配一个Runnable

        CompletableFuture.supplyAsync(() -> 1)
                .thenApplyAsync(i -> Integer.sum(i, 10))
                .thenAccept(System.out::println); // 输出结果

        CompletableFuture.supplyAsync(() -> 1)
                // i表示前面的返回值1，thenCompose表示可以把当前的异步交给另一个CompletableFuture处理
                .thenCompose(i -> CompletableFuture.supplyAsync(() -> 10 * i))
                .thenAccept(System.out::println);

        CompletableFuture.supplyAsync(() -> 1)
                // thenAcceptBoth()参数是一个BiFunction,表示有两个入参，一个是1，一个是2.0d
                .thenCombine(CompletableFuture.supplyAsync(() -> 2.0d), (r1, r2) -> r1 + r2)
                .thenAccept(System.out::println);

        CompletableFuture.supplyAsync(() -> 1)

                .thenAcceptBoth(CompletableFuture.supplyAsync(() -> 2.0d), (r1, r2) -> {
                    System.out.println(r1);
                    System.out.println(r2);
                    System.out.println(r1 + r2);
                });

        Thread.sleep(1000L);
    }



    @Test
    public void test01() {
        CompletableFuture.supplyAsync(() -> {
            try {
                return 1/0;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        })
                // thenApply()会对Future返回的值执行动态转换，这类似于Observable.map()
                .thenApply(i -> Integer.sum(i, 10))
                .whenComplete((v, t) -> {
                    if (t != null) {
                        t.printStackTrace();
                    }
                    System.out.println(v);
                });  // v代表value，t表示异常
    }
}
