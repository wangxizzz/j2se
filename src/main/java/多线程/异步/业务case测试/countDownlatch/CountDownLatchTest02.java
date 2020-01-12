package 多线程.异步.业务case测试.countDownlatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @Author wangxi
 * @Time 2020/1/12 19:42
 *
 */
public class CountDownLatchTest02 {
    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(10);
        CountDownLatch latch = new CountDownLatch(2);
        /**
         * 提交异步任务
         */
        service.execute(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                latch.countDown();
            }
            System.out.println("task1执行完毕");
        });

        service.execute(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                latch.countDown();
            }
            System.out.println("task2执行完毕");
        });

        try {
            System.out.println("开始等待获取结果");
            /**
             * 当没有设置超时时间时，会一直等待所有子线程的结束
             */
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("main over");

        service.shutdown();
    }
}
