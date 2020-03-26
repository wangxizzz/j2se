package 多线程.线程池;

import com.google.common.base.Stopwatch;

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
public class ScheduledThreadPoolTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ScheduledExecutorService service = Executors.newScheduledThreadPool(10, (r) -> {
            Thread t = new Thread(r);
            t.setDaemon(false);  // 设置为非后台线程,不会随着main的结束而结束
            return t;
        });
//        scheduleAtFixedRateTest(service);

//        scheduleWithFixedDelayTest(service);
//        scheduleTest(service);

        scheduleTest(service);
        service.shutdown();
    }

    public static void scheduleAtFixedRateTest(ScheduledExecutorService service) {

        // 定期调用任务,初始延迟1s,间隔2s执行(不管任务此时成功与否)
        ScheduledFuture<?> ssss = service.scheduleAtFixedRate(
                () -> {
                    try {
                        System.out.println("ssss");
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
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
                        System.out.println("bbb");
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }, 0L, 1L, TimeUnit.SECONDS);
    }
}
