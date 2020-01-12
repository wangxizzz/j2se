package 多线程.异步.业务case测试.countDownlatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @Author wangxi
 * @Time 2020/1/12 19:42
 *
 */
public class CountDownLatchTest03 {
    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(10);
        CountDownLatch latch = new CountDownLatch(2);
        /**
         * 不关注返回结果
         */
        Future<String> future1 =  service.submit(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                latch.countDown();
            }
            return "task1";
        });

        Future<String> future2 = service.submit(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                latch.countDown();
            }
            return "task2";
        });

        try {
            System.out.println("开始等待获取结果");
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("main over");

        service.shutdown();
    }
}
