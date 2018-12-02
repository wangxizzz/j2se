package 常用的工具类.guava.并发;

import com.google.common.collect.Comparators;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Lists;
import com.google.common.collect.Multiset;
import com.google.common.io.Files;
import com.google.common.io.Resources;
import com.google.common.util.concurrent.*;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Auther: wxi.wang
 * @Date: 18-11-28 15:35
 * @Description:
 */
public class Demo01 {
    public static final Logger LOGGER = LoggerFactory.getLogger(常用的工具类.guava.并发.Demo01.class);
    /**
     * guava的并发
     */
    @Test
    public void test04() throws ExecutionException, InterruptedException {
        // 创建一个线程池
        ListeningExecutorService service = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(4));
        // 提交一任务
        ListenableFuture<String> future = service.submit(() -> {
            String s = "aaa";
            return s;
        });
        LOGGER.info(future.get());
        LOGGER.info("===============================");
        // 设置监听，执行Runnable里的run()
        future.addListener(() -> {
            LOGGER.info("给future设置监听");
        }, service);

        // 设置回调函数
        Futures.addCallback(future, new FutureCallback<String>() {
            @Override
            public void onSuccess(@Nullable String result) {
                LOGGER.info("成功了");
                LOGGER.info("result is {}", result);
                try {
                    LOGGER.info("future is {}", future.get());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                LOGGER.info("失败了");
            }
        }, service);
    }
}
