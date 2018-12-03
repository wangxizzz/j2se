package 常用的工具类.guava.并发;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author wxi.wang
 * 18-12-1
 */
public final class CompletableFutures {
    private CompletableFutures() {}
    static <V> CompletableFuture<List<V>> successfulAsList(Iterable<? extends CompletableFuture<? extends V>> futures) {
        CompletableFuture<List<V>> allFuture = CompletableFuture.supplyAsync(() -> {
           List<V> list = new ArrayList<>();
           for (CompletableFuture<? extends V> c : futures) {
               c.whenCompleteAsync((v, t) -> {
                   try {
                       list.add(c.get());
                   } catch (InterruptedException | ExecutionException e) {
                       e.printStackTrace();
                   }
               });
           }
           return list;
        });
        return allFuture;
    }

}
