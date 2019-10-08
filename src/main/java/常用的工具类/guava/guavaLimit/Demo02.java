package 常用的工具类.guava.guavaLimit;

import com.google.common.collect.Maps;
import com.google.common.util.concurrent.RateLimiter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author wangxi
 * @Time 2019/10/7 21:43
 * 基于用户和具体接口做限流
 * 参考：https://my.oschina.net/hanchao/blog/1833612
 * guava的RateLimiter是采用了令牌桶的方式进行限流
 * 限制一秒内只能有N个线程执行，超过了就只能等待下一秒
 */
public class Demo02 {
    // service-QPS, 接口与qps的映射
    private static final Map<String, Double> resourceMap = new ConcurrentHashMap<>();
    // 用户与接口qps的映射
    private static final Map<String, RateLimiter> userResourceLimitMap = Maps.newConcurrentMap();

    static {
        // 初始化接口的qps
        resourceMap.putIfAbsent("fun01", 20D);
    }

    private static void updateResourceQps(String resource, double qps) {
        resourceMap.put(resource, qps);
    }

    private static void removeResource(String resource) {
        resourceMap.remove(resource);
    }
    // 基于用户和具体接口做限流
    public static boolean enter(String resource, String userKey) {
        Double qps = resourceMap.get(resource);
        if (qps == null || qps == 0d) {
            System.out.println("此接口 : " + resource + "没有限流,用户key : " + userKey);
            return false;
        }
        String finalUserKey = resource + userKey;
        RateLimiter limiter = userResourceLimitMap.get(finalUserKey);
        if (limiter == null) {
            limiter = RateLimiter.create(qps);
        }
        // 考虑多线程去修改
        RateLimiter putByOtherThread = userResourceLimitMap.put(finalUserKey, limiter);
        if (putByOtherThread != null) {
            limiter = putByOtherThread;
        }

        // 直接返回
        if (!limiter.tryAcquire()) {
            System.out.println("已被限流，userKey = " + finalUserKey);
            return false;
        } else {
            System.out.println("正常访问，userKey = " + finalUserKey);
            return true;
        }
    }
}
