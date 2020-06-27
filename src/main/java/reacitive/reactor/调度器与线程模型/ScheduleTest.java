package reacitive.reactor.调度器与线程模型;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

/**
 * @author wangxi created on 2020/6/27 22:14
 * @version v1.0
 */
public class ScheduleTest {

    private static void log(Object msg) {
        System.out.println(Thread.currentThread().getName() + ":" + msg);
    }


    @Test
    public void testScheduling() {
        Flux.range(0, 2)
                .doOnNext(x -> log("1: " + x))
                .publishOn(Schedulers.newParallel("myParallel"))
                .doOnNext(x -> log("2: " + x))
                .subscribeOn(Schedulers.newElastic("myElastic"))
                .doOnNext(x -> log("3: " + x))
                .blockLast();
    }
}
