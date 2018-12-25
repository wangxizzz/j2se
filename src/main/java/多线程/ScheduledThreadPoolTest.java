package 多线程;

import com.google.common.base.Stopwatch;

import java.util.concurrent.ExecutionException;
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
        Stopwatch stopwatch = Stopwatch.createStarted();
        ScheduledExecutorService service = Executors.newScheduledThreadPool(10, (r) -> {
            Thread t = new Thread(r);
            t.setDaemon(false);  // 设置为非后台线程,不会随着main的结束而结束
            return t;
        });
        // 定期调用任务,隔2s
        service.scheduleAtFixedRate(() -> System.out.println("ssss"), 1L, 2L, TimeUnit.SECONDS);
        ScheduledFuture<Integer> schedule = service.schedule(() -> {
            return 1;
        }, 3L, TimeUnit.SECONDS);
        Integer integer = schedule.get();
        System.out.println(integer);
        long millis = stopwatch.elapsed(TimeUnit.SECONDS);
        System.out.println(millis);
    }
}
