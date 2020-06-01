package 常用的工具类.caffeine;

import com.github.benmanes.caffeine.cache.AsyncLoadingCache;
import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import com.github.benmanes.caffeine.cache.RemovalListener;
import lombok.NonNull;
import org.junit.Test;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author wangxi created on 2020/5/31 17:55
 * @version v1.0
 */
public class CaffeineDemo {

    @Test
    public void test01() {
        LoadingCache<Integer, String> loadingCache = Caffeine.newBuilder()
                .expireAfterWrite(1, TimeUnit.SECONDS)
                .refreshAfterWrite(500, TimeUnit.MILLISECONDS)
                .maximumSize(10) // 缓存最大数量
                .executor(Executors.newSingleThreadExecutor())
                .removalListener((RemovalListener<Integer, String>) (integer, s, removalCause) -> {
                    System.out.println("key:" + integer + " value:" + s + " cause:"+removalCause);
                })
                .build(new CacheLoader<Integer, String>() {
                    @Nullable
                    @Override
                    public String load(@NonNull Integer i) throws Exception {
                        return i.toString();
                    }

                    @Nullable
                    @Override
                    public Map<Integer, String> loadAll(Iterable<? extends Integer> keys) {
                        Map<Integer, String> map = new HashMap<>();
                        for (Integer i : keys) {
                            map.put(i, i.toString());
                        }
                        return map;
                    }
                });

    }

    @Test
    public void test02() throws ExecutionException, InterruptedException {
        AsyncLoadingCache<String, String> asyncLoadingCache = Caffeine.newBuilder()
                .maximumSize(10_000)
                .expireAfterWrite(10, TimeUnit.MINUTES)
                // Either: Build with a synchronous computation that is wrapped as asynchronous
                .buildAsync(new CacheLoader<String, String>() {
                    @org.checkerframework.checker.nullness.qual.Nullable
                    @Override
                    public String load(@org.checkerframework.checker.nullness.qual.NonNull String key) throws Exception {
                        return createExpensiveGraph(key);
                    }

                    @Override
                    public @org.checkerframework.checker.nullness.qual.NonNull Map<String, String> loadAll(@org.checkerframework.checker.nullness.qual.NonNull Iterable<? extends String> keys) {
                        return null;
                    }
                });
        // Or: Build with a asynchronous computation that returns a future
        // .buildAsync((key, executor) -> createExpensiveGraphAsync(key, executor));

        String key = "name1";

        // 查询并在缺失的情况下使用异步的方式来构建缓存
        CompletableFuture<String> graph = asyncLoadingCache.get(key);
        System.out.println(graph.get());
        // 查询一组缓存并在缺失的情况下使用异步的方式来构建缓存
        List<String> keys = new ArrayList<>();
        keys.add(key);
        CompletableFuture<Map<String, String>> graphs = asyncLoadingCache.getAll(keys);
        // 异步转同步
        LoadingCache<String, String> synchronous = asyncLoadingCache.synchronous();
    }

    private String createExpensiveGraph(String key) {

        return key + "-1";
    }

    /**
     *
     */
    @Test
    public void test03() {

    }
}
