package reacitive.rxJava.rxJava反应式编程;

import com.google.common.collect.Lists;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import org.junit.Test;

/**
 * @author wangxi created on 2020/6/7 10:52
 * @version v1.0
 */
public class Chapter03 {

    private void log(Object msg) {
        System.out.println(Thread.currentThread().getName() + ":" + msg);
    }

    /**
     * doOnNext()只是简单地接收来自上游Observable的每个事件并将其传递给下游，它不能以任何方式对事件进行修改
     *
     * 每个操作符是生成了一个新的 Observable
     */
    @Test
    public void test01() throws InterruptedException {
        // RxJava会为整个管道只创建一个Worker实例
        // 主要是为了确保事件能够按顺序进行处理。Observable回调是单线程顺序执行。
        Observable.just(8,9,10)
                .doOnNext(i -> log("A : " + i))
                .filter(i -> i % 3 > 0)
                .doOnNext(i -> log("B: " + i))
                .map(i -> "#" + i * 10)
                .doOnNext(i -> log("C: " + i))
                .filter(i -> i.length() < 4)
                .subscribe(s -> log("D : " + s));
    }

    /**
     * flatMap并发操作
     */
    @Test
    public void test02() throws InterruptedException {
        long start = System.currentTimeMillis();
        Observable.fromIterable(Lists.newArrayList(1,2,3,4,11,1,1,1,2,1))
                .flatMap(this::getData, 3)
                .subscribeOn(Schedulers.io())
                .subscribe(x -> System.out.println(x));

        Thread.currentThread().join();
        System.out.println(System.currentTimeMillis() - start);
    }

    private Observable<Integer> getData(int i) {
        log("进入getData");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Observable.just(i);
    }
}
