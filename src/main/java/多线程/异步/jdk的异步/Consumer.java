package 多线程.异步.jdk的异步;

import lombok.extern.slf4j.Slf4j;
import 多线程.异步.User;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Slf4j
public class Consumer {
    public static void main(String[] args) {
        Provider1 provider1 = new Provider1();
        CompletableFuture<User> completableFuture1 = provider1.getUer();
        // CompletableFuture的get()会阻塞的。
//        try {
//            completableFuture1.get();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
        completableFuture1.whenComplete((v, t) -> {
            if (t != null) {
                log.error("错误。。。。");
                throw new RuntimeException(t);
            } else {
                System.out.println(v);
            }
        });
        Provider2 provider2 = new Provider2();
        CompletableFuture<User> completableFuture2 = provider2.getUer();
        completableFuture2.whenComplete((v, t) -> {
            if (t != null) {
                log.error("错误。。。。");
                throw new RuntimeException(t);
            } else {
                System.out.println(v);
            }
        });

        System.out.println("aaaaaaaaaa");
        System.out.println("程序依然往下跑中。。。。");

        /**
         * 利用手机秒表计时：程序耗时10.30s...不是20s
         * 验证成功！！
         */
    }
}
