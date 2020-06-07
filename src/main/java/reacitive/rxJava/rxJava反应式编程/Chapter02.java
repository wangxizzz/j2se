package reacitive.rxJava.rxJava反应式编程;

import io.reactivex.Observable;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static reacitive.rxJava.rxJava反应式编程.RxJavaDataEntity.load;

/**
 * @author wangxi created on 2020/6/6 21:56
 * @version v1.0
 */
public class Chapter02 {

    private void log(Object msg) {
        System.out.println(Thread.currentThread().getName() + ":" + msg);
    }

    @Test
    public void test01() {
        Observable<Object> ints = Observable.create(subscriber -> {
            log("create");
            subscriber.onNext(42);
            subscriber.onComplete();
        }).cache();

        log("starting");
        ints.subscribe(i -> log("元素1: " + i));

        ints.subscribe(i -> log("元素2: " + i));
    }

    /**
     * main线程的无限循环
     */
    @Test
    public void test02() {
        Observable.create(subscriber -> {
            BigDecimal i = BigDecimal.ZERO;
            while (true) {
                subscriber.onNext(i);
                i = i.add(BigDecimal.ONE);
            }
        }).subscribe(x -> log(x));

    }

    @Test
    public void test03() {

    }

    /**
     * 错误在create中并发调用,不符合rx的使用规范.
     *
     * 用RxJava的惯用操作符就可以避免这种复杂性，比如merge()和flatMap()
     */
    Observable<RxJavaDataEntity> loadAll(List<Integer> ids) {
        return Observable.create(subscriber -> {
            ExecutorService service = Executors.newFixedThreadPool(10);
            AtomicInteger countDown = new AtomicInteger(ids.size());
            ids.forEach(id -> {
                service.submit(() -> {
                    RxJavaDataEntity dataEntity = load(id);
                    subscriber.onNext(dataEntity);
                    if (countDown.decrementAndGet() == 0) {
                        service.shutdown();
                        subscriber.onComplete();
                    }
                });
            });
        });
    }

    /**
     * 异常传播
     */
    Observable<RxJavaDataEntity> rxLoad01(int id) {
        return Observable.create(subscriber -> {
           try {
               subscriber.onNext(load(id));
               // 完成事件. 否则就是无穷流
               subscriber.onComplete();
           } catch (Exception e) {
               // 把错误消息传达给订阅者
               subscriber.onError(e);
           }
        });
    }

    /**
     * rxLoad02 与rxLoad01语义完全相同,且更加简洁。load(id)同样有try-catch
     * @param id
     * @return
     */
    Observable<RxJavaDataEntity> rxLoad02(int id) {
        return Observable.fromCallable(() -> {
            return load(id);
        });
    }

    @Test
    public void test04() {
        rxLoad02(1).subscribe(x -> log(x));
    }

    /**
     * timer与interval
     * Observable.interval()会从零开始生成一系列long类型的连续数字
     */
    @Test
    public void test05() {
        Observable.timer(1, TimeUnit.SECONDS)
                .subscribe(zero -> log(zero));

        System.out.println("========");

        Observable.interval(1, TimeUnit.SECONDS)
                .subscribe(x -> log(x));
    }

    @Test
    public void test06() {

    }
}
