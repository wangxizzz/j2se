package 多线程.异步.业务case测试.countDownlatch;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @Author wangxi
 * @Time 2020/1/12 18:11
 * 注意：CountDownLatch没有超时异常！
 * 测试有超时时间的await方法
 *
 * 总结见summary的源码分析
 */
public class CountDownLatchTest01 {
    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(10);
        /**
         * 初始化AQS的state变量值
         */
        CountDownLatch latch = new CountDownLatch(2);
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                latch.countDown();
            }
            System.out.println("future1 over");
            return "future1..";
        }, service);

        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                latch.countDown();
            }
            System.out.println("future2 over");
            return "future2..";
        }, service);

        /**
         * 分析：
         * latch.await()没有设置超时时间，那么main线程会一直等待上面的异步任务执行完毕。
         *
         * latch.await(1, TimeUnit.MILLISECONDS);设置了1ms的超时时间，意思是，main线程等待1ms就直返回，不等子线程是否完成
         */
        try {
            System.out.println("开始等待");
            // 这里只等待了 1 毫秒
            boolean success =  latch.await(1, TimeUnit.MILLISECONDS);
            if (!success) {
                System.out.println("超时");
            }
            System.out.println("等待结束");
        } catch (InterruptedException e) {
            System.out.println("被中断。。。");
        }

        // 获取结果
        try {
            /**
             * 如果上面await设置了超时时间，但是子任务都没执行完，那么在这里get结果，仍然会阻塞住。
             */
            //System.out.println(future1.get());
            //System.out.println(future2.get());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("main over.");
        service.shutdown();
    }
}
