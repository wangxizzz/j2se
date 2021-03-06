package reacitive.reactor.基础语法;

import org.junit.Test;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author wangxi created on 2020/6/4 22:57
 * @version v1.0
 *
 * 示例：
 * 1、https://cloud.tencent.com/developer/article/1192054
 */
public class BasicDemo02 {
    // 顾名思义就是每隔一定时间，发射一个数据（从0开始），上面的示例表示每隔500毫秒，从0开始递增，发射1个数字

    /**
     * toStream()方法把 Flux 序列转换成 Java 8 中的 Stream 对象，
     * 再通过 forEach()方法来进行输出。这是因为序列的生成是异步的，
     * 而转换成 Stream 对象可以保证主线程在序列生成完成之前不会退出，从而可以正确地输出序列中的所有元素
     */
    @Test
    public void fluxIntervalTest() throws InterruptedException {
        Flux.interval(Duration.of(500, ChronoUnit.MILLIS)).subscribe(System.out::println);
        // toStream()阻塞
        Flux.interval(Duration.of(500, ChronoUnit.MILLIS)).toStream().forEach(System.out::println);

        //防止程序过早退出，放一个CountDownLatch拦住
//        CountDownLatch latch = new CountDownLatch(1);
//        latch.await();
    }


    @Test
    public void fluxBufferTest() throws InterruptedException {
        Flux.range(0, 10).buffer(3).subscribe(System.out::println);

        System.out.println("--------------");
        // 每隔1s发出一个元素，收集2s
        Flux.interval(Duration.of(1, ChronoUnit.SECONDS))
                .bufferTimeout(2, Duration.of(2, ChronoUnit.SECONDS))
                .subscribe(System.out::println);

        //防止程序过早退出，放一个CountDownLatch拦住
        CountDownLatch latch = new CountDownLatch(1);
        latch.await();
    }


    /**
     * 背压模拟
     */
    @Test
    public void backUpTest() {
        Flux.range(1, 6)    // 1
                .doOnRequest(n -> System.out.println("Request " + n + " values..."))    // 2
                .subscribe(new BaseSubscriber<Integer>() {  // 3
                    @Override
                    protected void hookOnSubscribe(Subscription subscription) { // 4
                        System.out.println("Subscribed and make a request...");
                        request(1); // 5
                    }

                    @Override
                    protected void hookOnNext(Integer value) {  // 6
                        try {
                            TimeUnit.SECONDS.sleep(1);  // 7
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println("Get value [" + value + "]");    // 8
                        request(1); // 9
                    }
                });
    }
}
