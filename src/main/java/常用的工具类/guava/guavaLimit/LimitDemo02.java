package 常用的工具类.guava.guavaLimit;

import com.google.common.util.concurrent.RateLimiter;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author wangxi created on 2020/11/29 17:43
 * @version v1.0
 */
public class LimitDemo02 {
    public static void main(String[] args) throws InterruptedException {
        final AtomicInteger count = new AtomicInteger(0);

        // 使用 SmoothBursty
//        final RateLimiter limiter = RateLimiter.create(5);

        //使用SmoothWarmingUp
        final RateLimiter limiter = RateLimiter.create(5, 500, TimeUnit.MILLISECONDS);

        Thread.sleep(2000);//先睡两秒，此时桶里累计的令牌是5个，达到上限
        Thread[] threads = new Thread[1000];
        for (int i = 0; i < 1000; i++) {
            threads[i] = new Thread(() -> {
                limiter.acquire();//acquire()实际调用的是acquire(1)，也就是消耗一个令牌
                System.out.println(new Date() + "---" + count.incrementAndGet());
            });
        }

        for (int i = 0; i < 1000; i++) {
            threads[i].start();
        }
        for (int i = 0; i < 1000; i++) {
            threads[i].join();
        }
    }
}
