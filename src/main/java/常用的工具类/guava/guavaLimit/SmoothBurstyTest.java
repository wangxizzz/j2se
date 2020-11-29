package 常用的工具类.guava.guavaLimit;

import com.google.common.util.concurrent.RateLimiter;

/**
 * @author wangxi created on 2020/11/29 21:03
 * @version v1.0
 *
 * 基于令牌桶,可应对突发流量
 */
public class SmoothBurstyTest {
    public static void main(String[] args) {
        //test01();

        test02();
    }

    /**
     * RateLimiter由于会累积令牌，所以可以应对突发流量。
     * 在下面代码中，有一个请求会直接请求5个令牌，但是由于此时令牌桶中有累积的令牌，足以快速响应。  
     * RateLimiter在没有足够令牌发放时，采用滞后处理的方式，
     * 也就是前一个请求获取令牌所需等待的时间由下一次请求来承受，也就是代替前一个请求进行等待。
     */
    private static void test02() {
        RateLimiter r = RateLimiter.create(5);

        while (true) {

            System.out.println("get 5 tokens: " + r.acquire(5) + "s");

            System.out.println("get 1 tokens: " + r.acquire(1) + "s");   // 这一行的输出，就可以体现出(滞后效应，需要替前一个请求进行等待)，注意令牌桶是以一定的速率放入令牌，不是一次性放入的。

            System.out.println("get 1 tokens: " + r.acquire(1) + "s");

            System.out.println("get 1 tokens: " + r.acquire(1) + "s");

            System.out.println("end");
        }
    }

    private static void test01() {
        RateLimiter r = RateLimiter.create(5);
        while (true) {
            System.out.println("get 1 tokens: " + r.acquire() + "s");
        }
    }
}
