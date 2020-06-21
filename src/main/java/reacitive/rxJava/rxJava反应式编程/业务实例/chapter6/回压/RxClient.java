package reacitive.rxJava.rxJava反应式编程.业务实例.chapter6.回压;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import org.junit.Test;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * @author wangxi created on 2020/6/20 21:59
 * @version v1.0
 */
public class RxClient {

    private void log(Object msg) {
        System.out.println(Thread.currentThread().getName() + ":" + msg);
    }

    private ThreadFactory threadFactory(String pattern) {
        return new ThreadFactoryBuilder()
                .setNameFormat(pattern)
                .build();
    }

    Scheduler schedulerA = Schedulers.
            from(Executors.newFixedThreadPool(10, threadFactory("Sched-A-%d")));

    Scheduler schedulerB = Schedulers.
            from(Executors.newFixedThreadPool(10, threadFactory("Sched-B-%d")));

    Scheduler schedulerC = Schedulers.
            from(Executors.newFixedThreadPool(10, threadFactory("Sched-C-%d")));

    @Test
    public void fun01() {
        // main线程单线程执行，生产与消费耦合，因此只能单个元素顺序输出
        Observable.range(1, 1000000)
                .map(Dish::new)
                .subscribe(x -> {
                    System.out.println("washing : " + x);
                    Thread.sleep(50);
                });
    }

    /**
     * 生产与消费解耦
     *
     * Observable在一个线程(main)中生产事件，而Subscriber(schedulerA)在另一个线程中消费事件
     *
     * 由range产生的Observable 理论上支持背压，但是下面的例子确没有体现。
     */
    @Test
    public void fun02() throws InterruptedException {
        Observable.range(1, 100000)
                .map(Dish::new)
//                .doOnNext(this::log)
                .observeOn(Schedulers.io())
                .subscribe(x -> {
                    log("washing : " + x);
                    Thread.sleep(50);
                }, Throwable::printStackTrace);

        // main线程发射完元素瞬息间就结束了
        Thread.currentThread().join();
    }

    private Observable<Integer> myRange(int from, int count) {
        return Observable.create(subscriber -> {
            int i = from;
            while (i < from + count) {
                if (!subscriber.isDisposed()) {
                    subscriber.onNext(i++);
                } else {
                    return;
                }
            }
            subscriber.onComplete();
        });
    }

    @Test
    public void fun03() throws InterruptedException {
        myRange(1, 100000)
                .map(Dish::new)
                .observeOn(Schedulers.io())
                .subscribe(x -> {
                    log("washing : " + x);
                    Thread.sleep(50);
                }, Throwable::printStackTrace);
        // main线程发射完元素瞬息间就结束了
        Thread.currentThread().join();
    }
}
