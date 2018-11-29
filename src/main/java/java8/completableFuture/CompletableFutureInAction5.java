package java8.completableFuture;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static java.util.stream.Collectors.toList;

public class CompletableFutureInAction5 {
    public static void main(String[] args) throws InterruptedException {
        // 以下起的都是后台线程，会随着main的结束而over
        CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + " is running.");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 1;
            // runAfterBoth表示两个CompletableFuture都结束后执行的方法逻辑
        }).runAfterBoth(CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + " is running.");
            return 2;
        }), () -> System.out.println("done"));

        System.out.println("====================================");

        CompletableFuture.supplyAsync(() -> {
            System.out.println("I am future 1");
            return CompletableFutureInAction1.get();
            // applyToEither两个CompletableFuture中有一个运行完就可以了
        }).applyToEither(CompletableFuture.supplyAsync(() -> {
            System.out.println("I am future 2");
            return CompletableFutureInAction1.get();
        }), v -> v * 10)
                .thenAccept(System.out::println);

        CompletableFuture.supplyAsync(() -> {
            System.out.println("I am future 1");
            return CompletableFutureInAction1.get();
        }).acceptEither(CompletableFuture.supplyAsync(() -> {
            System.out.println("I am future 2");
            return CompletableFutureInAction1.get();
        }), System.out::println);

        CompletableFuture.supplyAsync(() -> {
            System.out.println("I am future 1");
            return CompletableFutureInAction1.get();
        }).runAfterEither(CompletableFuture.supplyAsync(() -> {
            System.out.println("I am future 2");
            return CompletableFutureInAction1.get();
        }), () -> System.out.println("done."));

        List<CompletableFuture<Double>> collect = Arrays.asList(1, 2, 3, 4)
                .stream()
                .map(i -> CompletableFuture.supplyAsync(CompletableFutureInAction1::get))
                .collect(toList());

        CompletableFuture.anyOf(collect.toArray(new CompletableFuture[collect.size()]))
                .thenRun(() -> System.out.println("done"));

        // 让主线程等待上面线程运行完毕
        Thread.currentThread().join();
    }
}
