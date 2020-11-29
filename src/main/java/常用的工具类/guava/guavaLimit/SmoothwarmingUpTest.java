package 常用的工具类.guava.guavaLimit;

import com.google.common.util.concurrent.RateLimiter;

import java.util.concurrent.TimeUnit;

/**
 * @author wangxi created on 2020/11/29 21:16
 * @version v1.0
 *
 * 平滑预热限流
 */
public class SmoothwarmingUpTest {

    /**
     * RateLimiter的 SmoothWarmingUp是带有预热期的平滑限流，它启动后会有一段预热期，
     * 逐步将分发频率提升到配置的速率。
     *  比如下面代码中的例子，创建一个平均分发令牌速率为2，预热期为3s
     * 。由于设置了预热时间是3秒，令牌桶一开始并不会0.5秒发一个令牌，
     * 而是形成一个平滑线性下降的坡度，频率越来越高，在3秒钟之内达到原本设置的频率，以后就以固定的频率输出。
     * 这种功能适合系统刚启动需要一点时间来“热身”的场景。
     */

    public static void main(String[] args) {
        testSmoothwarmingUp();
    }

    public static void testSmoothwarmingUp() {
        /**
         * 第二个参数为5，表示在5s内以不同的时间放过5个请求。之后会按照固定速率放过请求
         */
        RateLimiter r = RateLimiter.create(2, 5, TimeUnit.SECONDS);

        while (true) {

            System.out.println("get 1 tokens: " + r.acquire(1) + "s");

            System.out.println("get 1 tokens: " + r.acquire(1) + "s");

            System.out.println("get 1 tokens: " + r.acquire(1) + "s");

            System.out.println("get 1 tokens: " + r.acquire(1) + "s");

            System.out.println("get 1 tokens: " + r.acquire(1) + "s");
            System.out.println("get 1 tokens: " + r.acquire(1) + "s");

            System.out.println("end");
        }
    }
}
