package 多线程.线程池;

import com.google.common.base.Stopwatch;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author wxi.wang
 * 18-12-24
 * 测试可调度的线程池
 */
@Slf4j
public class ScheduledThreadPoolTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ScheduledExecutorService service = Executors.newScheduledThreadPool(10, (r) -> {
            Thread t = new Thread(r);
            t.setDaemon(false);  // 设置为非后台线程,不会随着main的结束而结束
            return t;
        });
//        scheduleAtFixedRateTest(service);

        scheduleWithFixedDelayTest(service);
//        scheduleTest(service);

        //service.shutdown();
    }

    public static void scheduleAtFixedRateTest(ScheduledExecutorService service) {
        Random random = new Random();
        // 调度任务，那任务的执行时间算进去了
        service.scheduleAtFixedRate(
                () -> {
                    try {
                        log.info("ssss");
                        Thread.sleep(random.nextInt(5000));
                    } catch (Exception e) {

                    }
                }, 1L, 1L, TimeUnit.SECONDS);
    }

    public static void scheduleTest(ScheduledExecutorService service) {
        service.schedule(() -> {
            System.out.println("aaaa");
        }, 2L, TimeUnit.SECONDS);
    }

    public static void scheduleWithFixedDelayTest(ScheduledExecutorService service) {
        service.scheduleWithFixedDelay(
                () -> {
                    try {
                        log.info("bbb");
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }, 0L, 1L, TimeUnit.SECONDS);
    }
}
