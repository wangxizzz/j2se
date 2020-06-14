package reacitive.rxJava.rxJava反应式编程.业务实例.chapter4.schedules与并发;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * @author wangxi created on 2020/6/13 21:14
 * @version v1.0
 */
public class OnSubscribeDemo {

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



    /**
     * 注意：方法输出的顺序
     *
     * 只有在调用了 subscribe时，底层真正的逻辑才会执行。
     *
     * 执行原理：
     *      在subscribe()和create()之间有一个内在却隐含的连接。
     *      每次在Observable上调用subscribe()的时候，
     *      就会调用它的OnSubscribe回调方法（该方法包装了传递给create()的lambda表达式）。
     *      它接收你的Subscriber作为参数。默认情况下，这会在同一个线程中执行，并且是阻塞的，
     *      换言之，无论在create()中做什么，都会阻塞subscribe()。
     *      如果你的create()休眠几秒，那么subscribe()就会阻塞。
     *      如果在Observable.create()和你的Subscriber（作为回调的lambda表达式）之间存在操作符，
     *      所有的操作符也都会在调用subscribe()的线程中执行。
     *      RxJava默认在Observable和Subscriber之间并没有插入任何的并发基础设施。
     *      这背后的原因在于Observable一般是由其他并发机制支撑的，比如事件循环或自定义线程，
     *      所以Rx将控制权完全交给你，而不做任何强加的约定
     */
    @Test
    public void test01() {
        Observable<String> sample = sample();
        log("starting");
        Observable<String> obs = sample.map(x -> x)
                .filter(x -> true);
        log("transformed");

        obs.subscribe(x -> log("Got: " + x),
                Throwable::printStackTrace, () -> log("completed"));

        log("existing");
    }

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
     * 声明式地定义的OnSubscribe回调方法会在所选的Scheduler中执行。
     * 不管在create()中进行何种操作，这些任务都会由独立的scheduler承担，而subscribe()不会再阻塞
     *
     * 注意：在真正的逻辑没执行完，main thread就会退出
     */
    @Test
    public void test02() {
        log("starting");
        Observable<String> obs = sample();
        log("creating");
        obs.subscribeOn(schedulerA)
                .subscribe(x -> log("Got: " + x),
                        Throwable::printStackTrace, () -> log("completed"));
    }

    /**
     * 在开始之前，你必须要知道，在采用Rx的成熟应用程序中，很少会用到subscribeOn()
     * 正常情况下，Observable源于本来就已经是异步的源
     * subscribeOn()仅用于一些特殊的场景
     * ，也就是你已经知道底层Observable是同步的（create()是阻塞的）时候。
     * 但是，相对于在create()中手动编写线程相关的代码，subscribeOn()是一个好得多的方案
     *
     * 下面的写法是不合理的。
     */
    @Test
    public void test03() {
        Observable.create(observe -> {
            new Thread(() -> {
                observe.onNext("A");
                observe.onNext("B");
                observe.onComplete();
            }, "async-1").start();
        });
    }

    /**
     * 大多数情况下，Observable来自异步的事件源，并且默认它们都是异步的。
     * 因此，subscribeOn()的使用场景非常有限，主要用于完善现有API和库的工作
     */


    /**
     * 如果在Observable和subscribe()之间调用两次subscribeOn()，将会如何运行？
     * 答案非常简单：最靠近原始Observable的subscribeOn()胜出
     */
    @Test
    public void test04() {
        log("starting");
        Observable<String> obs = sample();
        log("creating");
        obs.subscribeOn(schedulerA)
                .subscribeOn(schedulerB)  // 浪费，会产生额外开销
                .subscribeOn(schedulerC)
                .subscribe(x -> log("Got: " + x),
                        Throwable::printStackTrace, () -> log("completed"));
    }

    /**
     *RxJava会为整个回调事件流管道只创建一个Worker实例，
     * 主要是为了确保事件能够按顺序进行处理。
     */
    @Test
    public void test05() throws InterruptedException {
        log("starting");
        Observable<String> obs = sample();
        log("creating");
        obs.doOnNext(this::log)
                .map(x -> x  + "1")
                .doOnNext(this::log)
                .map(x -> x + "2")
                .subscribeOn(schedulerA)  // subscribeOn的顺序无所谓
                .doOnNext(this::log)
                .subscribe(x -> log("Got: " + x),
                        Throwable::printStackTrace, () -> log("completed"));

        Thread.sleep(500);
    }

    /**
     * 整个回调事件流管道为单线程，那如果有耗时的操作呢？
     * 在数据管道实现并行. 错误写法
     */
    @Test
    public void test06() throws InterruptedException {
        /**
         * 错误写法。doPerchase()事件执行仍然是串行的。
         *
         * 因为5个商品的购买在单个事件流中
         *
         */
        Observable.just("a", "b", "c", "d", "e")
                // 线程切换是错误的
                .subscribeOn(schedulerA)
                .map(x -> doPurchase(x, 1))
                .reduce((x, y) -> x.add(y))
                .subscribe(x -> System.out.println(x));

        Thread.currentThread().join();
    }

    /**
     * 整个回调事件流管道为单线程，那如果有耗时的操作呢？
     * 在数据管道实现并行。把耗时操作改为多个事件流。
     *
     * 声明式并发
     */
    @Test
    public void test07() throws InterruptedException {
        Observable.just("a", "b", "c", "d", "e")
                .flatMap(x -> purchase(x, 1).subscribeOn(schedulerA))
                .reduce((x, y) -> x.add(y))
                .subscribe(x -> System.out.println(x));
        Thread.currentThread().join();
    }

    private Observable<BigDecimal> purchase(String productName, long quality) {
        // 底层的Observable是阻塞的
        return Observable.fromCallable(() -> doPurchase(productName, quality));
    }

    private BigDecimal doPurchase(String productName, long quality) throws InterruptedException {
        log("purchasing " + quality + " " + productName);
        // 真正执行逻辑. 很耗时
        Thread.sleep(2000);
        log("done " + quality + " " + productName);
        return BigDecimal.ONE;
    }

    /**
     * 分组，批处理操作
     */
    @Test
    public void test08() throws InterruptedException {
        Observable<BigDecimal> totalPrice = Observable.just("bread", "butter", "egg", "milk",
                "tomato", "cheese", "tomato", "egg", "egg")
                .groupBy(prod -> prod)
                .map(grouped -> grouped.count().map(quality -> {
                    String productName = grouped.getKey();
                    return Pair.of(productName, quality);
                }))
                .flatMap(order -> order.toObservable())
                .flatMap(pair -> purchase(pair.getLeft(), pair.getRight()).subscribeOn(schedulerB))
                .reduce((x, y) -> x.add(y))
                .subscribeOn(schedulerA)
                .toObservable();

        totalPrice.subscribe(x -> log(x));

        // 离管道中 最开始的 Observable最近的 线程池被使用。
        Thread.currentThread().join();
    }
}
