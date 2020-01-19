package 多线程.java并发编程之美.ch9问题分析;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * author: wxi.wang
 * Date: 2020/1/19 10:41
 * Description: 自定义线程工厂
 */
public class NamedThreadPool {

    public static ExecutorService service = new ThreadPoolExecutor(5, 5, 60, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(20), new NamedThreadPoolFactory("wangxi-threadPool"));

    public static void main(String[] args) {
        service.execute(() -> {
            throw new NullPointerException();
        });
    }

    public static class NamedThreadPoolFactory implements ThreadFactory {
        private static final AtomicInteger poolNumber = new AtomicInteger(1);
        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;

        NamedThreadPoolFactory(String name) {
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() :
                    Thread.currentThread().getThreadGroup();
            namePrefix = name + "-" +
                    poolNumber.getAndIncrement() +
                    "-thread-";
        }

        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r,
                    namePrefix + threadNumber.getAndIncrement(),
                    0);
            if (t.isDaemon())
                t.setDaemon(false);
            if (t.getPriority() != Thread.NORM_PRIORITY)
                t.setPriority(Thread.NORM_PRIORITY);
            return t;
        }
    }
}
