package 多线程.业务实例.热点探测demo;

import io.netty.util.HashedWheelTimer;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;

/**
 * @author wangxi created on 2021/1/2 00:36
 * @version v1.0
 */
@Slf4j
public class HotKeyDetector {
    public static final HotKeyDetector INSTANCE = new HotKeyDetector();

    public Map<String, LongAdder> map = new ConcurrentHashMap<>();

    public int hotKeyWater = 80;

    HashedWheelTimer hashedWheelTimer = new HashedWheelTimer(1, TimeUnit.SECONDS);

    public void statistic(String key) {
        if (map.containsKey(key)) {
            LongAdder longAdder = map.get(key);
            longAdder.increment();
            map.put(key, longAdder);
        } else {
            LongAdder longAdder = new LongAdder();
            longAdder.increment();
            map.put(key, longAdder);
        }
    }

    public void init() {
        log.info("时间轮初始化,,,");
        hashedWheelTimer.newTimeout((x) -> {
            // 遍历map，判断哪些满足热点数据
            map.forEach((k, v) -> {
                if (v.longValue() > hotKeyWater) {
                    log.info("hot key : {}, value : {}", k, v);
                }
            });
            // map清空
            map = new ConcurrentHashMap<>();

            // 时间轮任务重放
            reput();
        }, 3, TimeUnit.SECONDS);
    }

    public void reput() {
        log.info("任务重放,,,,");
        hashedWheelTimer.newTimeout((x) -> {
            // 遍历map，判断哪些满足热点数据
            map.forEach((k, v) -> {
                if (v.longValue() > hotKeyWater) {
                    log.info("hot key : {}, value : {}", k, v);
                }
            });
            // map清空
            map = new ConcurrentHashMap<>();

            // 时间轮任务重放
            reput();
        }, 3, TimeUnit.SECONDS);
    }
}
