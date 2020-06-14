package reacitive.rxJava.rxJava反应式编程.业务实例.chapter4.schedules与并发;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import org.junit.Test;

import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * @author wangxi created on 2020/6/14 10:47
 * @version v1.0
 *
 *
 */
public class ObserveOnDemo {

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

    private Observable<String> sample() {
        return Observable.create(observe -> {
            //Thread.sleep(500);
            log("onSubscribe");
            observe.onNext("A");
            observe.onNext("B");
            observe.onComplete();
        });
    }

    /**
     * 在create方法(数据管道中)，只要没涉及到线程切换操作，那么就是串行执行的。
     *
     * observeOn()会在管道链的某个地方出现，但是与subscribeOn()不同，observeOn()的位置非常重要。
     * 不管在observeOn()之前是哪个Scheduler在运行操作符（如果存在Scheduler），但是在该方法之后，
     * 就会由提供的Scheduler来运行所有的工作。
     *
     * observeOn之前的所有操作符都是在客户端线程中运行的，这正是RxJava的默认行为。
     * 但是在observeOn()之后，所有的操作符都会在提供的Scheduler中运行
     */
    @Test
    public void test01() {
        log("starting");
        Observable<String> obs = sample();
        obs.doOnNext(x -> log("found 1: " + x))
                .observeOn(schedulerA)
                .doOnNext(x -> log("found 2 : " + x))
                .subscribe(x -> log("Got : " + x),
                        Throwable::printStackTrace, () -> log("completed"));
        log("existed");

    }

    /**
     * 演示多线程池切换
     *
     * 注意observeOn的定义：observeOn 表示后续 就会由提供的Scheduler来运行所有的工作
     */
    @Test
    public void test02() throws InterruptedException {
        log("starting");
        Observable<String> obs = sample();
        obs
                .doOnNext(x -> log("found 1: " + x))
                .subscribeOn(schedulerA)   // subscribeOn的位置可以随便放，它决定了create方法的执行线程
                .observeOn(schedulerB)
                .doOnNext(x -> log("found 2 : " + x))
                .observeOn(schedulerC)
                .doOnNext(x -> log("found 3 : " + x))
                .subscribe(x -> log("Got : " + x),
                        Throwable::printStackTrace, () -> log("completed"));
        log("existed");

        Thread.currentThread().join();
    }

    /**
     * 更复杂的操作符.实现并行
     */
    @Test
    public void test03() throws InterruptedException {
        log("created");
        // lambda中用subscriber 更加合适，因为是订阅者来执行create里的方法
        Observable<String> obs = Observable.create(subscriber -> {
            log("subscribed");
            subscriber.onNext("A");
            subscriber.onNext("B");
            subscriber.onNext("C");
            subscriber.onNext("D");
            subscriber.onComplete();
        });
        log("created");
        obs.subscribeOn(schedulerA)
                .flatMap(record -> store(record).subscribeOn(schedulerB))
                .observeOn(schedulerC)
                .subscribe(x -> log("Got: " + x),
                        Throwable::printStackTrace, () -> log("completed"));

        log("existed");

        Thread.currentThread().join();

    }

    private Observable<UUID> store(String s) {
        return Observable.create(subscriber -> {
           log("store : " + s);
           // 这里有一些繁重的工作
            subscriber.onNext(UUID.randomUUID());
            subscriber.onComplete();
        });
    }
}
