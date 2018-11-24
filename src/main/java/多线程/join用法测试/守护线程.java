package 多线程.join用法测试;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 注意：此程序不能在Junit环境下测试。
 *
 * 守护线程(Daemon)会随着主线程(main)的消亡而退出
 * 测试线程池中的守护线程
 */
public class 守护线程 {
    public static void main(String[] args) {
        ExecutorService e = Executors.newFixedThreadPool(2);
        e.execute(() -> {
            System.out.println("aaa");
        });
        /**
         * 此时如果不调用shutdown()，程序将不会结束。因为此时FixedThreadPool里面的线程不是Daemon，默认为false
         */
        e.shutdown();

        // 自定义线程工厂的
        ExecutorService e1 = Executors.newFixedThreadPool(2, (r) -> {
            // 自定义线程的创建，把创建的Thread设置为Daemon，因此不需要shutdown()
            Thread t = new Thread(r);
            t.setDaemon(true);
            return t;
        });

    }
}
