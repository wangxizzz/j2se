package java8.completableFuture;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class CompletableFutureInAction3 {

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(2, r -> {
            Thread t = new Thread(r);
            t.setDaemon(false);
            return t;
        });

       /* CompletableFuture.supplyAsync(CompletableFutureInAction1::get, executor)
                .thenApply((value) -> CompletableFutureInAction3.multiply(value))
                .whenComplete((v, t) -> Optional.ofNullable(v).ifPresent(System.out::println));*/

        /**
         * 下面代码的需求如下：我有一系列商品ID(productionIDs),现在我需要查出这些商品的价格，
         * 通过queryProduction()，然后用10倍的价格卖出，通过multiply函数。
         */
        List<Integer> productionIDs = Arrays.asList(1, 2, 3, 4, 5);
        // 下面可以并行执行
        List<Double> result = productionIDs
                .stream()
                .map(i -> CompletableFuture.supplyAsync(() -> queryProduction(i), executor))
                .map(future -> future.thenApply(CompletableFutureInAction3::multiply))
                .map(CompletableFuture::join).collect(Collectors.toList()); // 这个join表示把各个线程算出来的结果 加入容器中。具体可以参照javadoc

        System.out.println(result);
        executor.shutdown();
    }

    private static double multiply(double value) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return value * 10d;
    }

    /**
     * 通过商品id查询商品的价格
     * @param i 商品Id
     * @return 商品价格
     */
    private static double queryProduction(int i) {
        return CompletableFutureInAction1.get();
    }
}
