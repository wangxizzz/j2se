package 常用的工具类.guava.并发;

import com.google.common.util.concurrent.ListenableFuture;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author wxi.wang
 * 18-12-1
 */
public final class CompletableFutures {
    private CompletableFutures() {}
    public static <V> CompletableFuture<List<V>> successfulAsList(Iterable<? extends CompletableFuture<? extends V>> futures) {
        futures.forEach((future) -> {
            System.out.println(future);
        });
        return null;
    }

    public static <V> ListenableFuture<List<V>> successfulAsList1(Iterable<? extends ListenableFuture<? extends V>> futures) {
        futures.forEach((future) -> {
            System.out.println(future.isDone());
        });
        return null;
    }
}
