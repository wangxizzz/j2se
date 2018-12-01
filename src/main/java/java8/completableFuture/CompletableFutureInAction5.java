package java8.completableFuture;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

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
            // 第一个CompletableFuture是上面那个，第二个是下面这个
        }).runAfterBoth(CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + " is running.");
            return 2;
        }), () -> System.out.println("done"));

        System.out.println("====================================");

        CompletableFuture.supplyAsync(() -> {
            System.out.println("I am future 1");
            // 调用get()，休眠一下
            return CompletableFutureInAction1.get();
            // applyToEither两个CompletableFuture中有一个运行完就可以了
            // 第一个CompletableFuture是上面那个，第二个是下面这个
        }).applyToEither(CompletableFuture.supplyAsync(() -> {
            System.out.println("I am future 2");
            return CompletableFutureInAction1.get();
        }), v -> v * 10)
                // 只要有一个运行完成之后就可以执行函数式方法v -> v*10 ,然后执行thenApply()
                .thenAccept(System.out::println);

        CompletableFuture.supplyAsync(() -> {
            System.out.println("I am future 1");
            return CompletableFutureInAction1.get();
            // acceptEither表示只要有一个执行完成之后，就会执行Consumer函数System.out::println
            // 应用场景：比如开两个线程从两个地方下载，如果哪个先下好，就直接用那个，另外一个丢弃。
        }).acceptEither(CompletableFuture.supplyAsync(() -> {
            System.out.println("I am future 2");
            return CompletableFutureInAction1.get();
        }), System.out::println);

        CompletableFuture.supplyAsync(() -> {
            System.out.println("I am future 1");
            return CompletableFutureInAction1.get();
            // runAfterEither表示一个CompletableFuture完成之后执行Runnable的run()
        }).runAfterEither(CompletableFuture.supplyAsync(() -> {
            System.out.println("I am future 2");
            return CompletableFutureInAction1.get();
        }), () -> System.out.println("done."));

        List<CompletableFuture<Double>> collect = Arrays.asList(1, 2, 3, 4)
                .stream()
                .map(i -> CompletableFuture.supplyAsync(CompletableFutureInAction1::get))
                .collect(Collectors.toList());
        // allOf表示等collect中所有CompletableFuture执行完毕之后，执行thenRun()，输出done，也可以替换为ThenApply(),不过需要先转换为数组
        CompletableFuture.allOf(collect.toArray(new CompletableFuture[collect.size()]))
                .thenRun(() -> System.out.println("done"));

        // anyOff表示等collect中任意一个CompletableFuture执行完毕之后，执行thenRun()，输出done，其他的不关心。也可以替换为ThenApply(),不过需要先转换为数组
        CompletableFuture.anyOf(collect.toArray(new CompletableFuture[collect.size()]))
                .thenRun(() -> System.out.println("done"));

        // 让主线程等待上面线程运行完毕
        Thread.currentThread().join();
    }
}
