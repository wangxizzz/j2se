package 多线程.异步.jdk的异步;

import lombok.extern.slf4j.Slf4j;
import 多线程.异步.User;

import java.util.concurrent.CompletableFuture;

/**
 * <Description>
 *
 * @author wangxi
 */
@Slf4j
public class Consumer2 {
    public static void main(String[] args) {
        Provider1 provider1 = new Provider1();
        for (int i = 0; i < 2; i++) {
            final int j = i;
            CompletableFuture<User> completableFuture1 = provider1.getUer();
            completableFuture1.whenComplete((v, t) ->{
                if (t != null) {
                    log.error("错误。。。。i = {}", j);
                    throw new RuntimeException(t);
                } else {
                    System.out.println(Thread.currentThread().getName());
                    System.out.println(v);
                }
            });
        }
    }
}

