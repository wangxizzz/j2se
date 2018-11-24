package java8.completableFuture;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;


public class CompletableFutureInAction2 {

    public static void main(String[] args)
            throws InterruptedException {
        AtomicBoolean finished = new AtomicBoolean(false);
        ExecutorService executor = Executors.newFixedThreadPool(2, r -> {
            Thread t = new Thread(r);
            t.setDaemon(false);  // 设置为非守护线程，否则的话会随着main线程的消亡而结束
            return t;
        });
        CompletableFuture.supplyAsync(CompletableFutureInAction1::get, executor)
                .whenComplete((v, t) -> {
                    Optional.of(v).ifPresent(System.out::println);
                    finished.set(true);
                });

        System.out.println("====i am no ---block----");
/*        while (!finished.get()) {
            Thread.sleep(1);
        }*/
        executor.shutdown();
    }
}
