package 常用的工具类.ratelimit;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Author wangxi
 * @Time 2020/4/15 20:42
 *
 * https://segmentfault.com/a/1190000016359991
 * https://juejin.im/entry/57cce5d379bc440063066d09  这篇文章比较各种方式比较直观
 * 有时间需要总结下这2篇文章
 * 滑动窗口本质解决了：在 第1s末到第2s初，流量达到服务承受最大值的问题。
 * 固定计数是无法解决上述问题的
 */
public class SlidingWindowRateLimiter implements Runnable{
    private final long maxVisitPerSecond;

    private static final int DEFAULT_BLOCK = 10;
    private final int block;
    private final AtomicLong[] countPerBlock;

    private AtomicLong count;
    private volatile int index;

    public SlidingWindowRateLimiter(int block, long maxVisitPerSecond) {
        this.block = block;
        this.maxVisitPerSecond = maxVisitPerSecond;
        countPerBlock = new AtomicLong[block];
        for (int i = 0 ; i< block ; i++) {
            countPerBlock[i] = new AtomicLong();
        }
        count = new AtomicLong(0);
    }

    public boolean isOverLimit() {
        return currentQPS() > maxVisitPerSecond;
    }

    public long currentQPS() {
        return count.get();
    }

    public boolean visit() {
        countPerBlock[index].incrementAndGet();
        count.incrementAndGet();
        return isOverLimit();
    }

    public void run() {
        System.out.println(isOverLimit());
        System.out.println(currentQPS());
        System.out.println("index:" + index);
        // 环形队列的设计
        index = (index + 1) % block;
        // 下一行代码是为了模拟窗口整体滑动时所接受的流量。
        // 注意：整个窗口的滑动是总体时间1s，并不是单个格子的时间，意思是说过了第1s，窗口才会整体往右滑动一格。
        // 如果整体窗口以每一格的时间向前滑动，那么在1s末2s初时，整个窗口已经位于2.1s~3.0s之间了，仍然无法处理临界问题
        long val = countPerBlock[index].getAndSet(0);
        // 使用是个计数器来模拟每个窗口的计数器之和
        count.addAndGet(-val);
    }

    public static void main(String[] args) {
        /**
         * 注意：如果限制qps是100，划分10格，那么每一格的时间是1000ms/10=0.1s。
         * 每一格接收的请求总量是：100/10=10
         */
        SlidingWindowRateLimiter slidingWindowRateLimiter = new SlidingWindowRateLimiter(10, 1000);
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(slidingWindowRateLimiter, 100, 100, TimeUnit.MILLISECONDS);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    slidingWindowRateLimiter.visit();
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (true) {
//                    slidingWindowRateLimiter.visit();
//                    try {
//                        Thread.sleep(10);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }).start();
    }
}
