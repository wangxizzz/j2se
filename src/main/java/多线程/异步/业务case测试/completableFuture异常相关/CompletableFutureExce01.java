package 多线程.异步.业务case测试.completableFuture异常相关;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;

/**
 * @author wangxi created on 2020/6/7 22:08
 * @version v1.0
 */
@Slf4j
public class CompletableFutureExce01 {
    public static void main(String[] args) throws InterruptedException {
        CompletableFuture.supplyAsync(() -> {

            int i = 1/0;

            return 1;
        }).whenComplete((v, e) -> {
           if (e != null) {
               log.error("aaa", e);
           }
            System.out.println(v);
        }).exceptionally(e -> {
            log.error("bbbb", e);
            return null;
        });
        /**
         * 异常在whenComplete和exceptionally都捕获了。
         */
        Thread.currentThread().join();
    }
}
