package reacitive.reactor.基础语法;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

/**
 * @author wangxi created on 2020/6/6 17:12
 * @version v1.0
 *
 * 本类例子参考：https://www.ibm.com/developerworks/cn/java/j-cn-with-reactor-response-encode/index.html
 */
public class BasicDemo01 {
    /**
     * 创建flux的方式
     *
     *
     * just()：可以指定序列中包含的全部元素。创建出来的 Flux 序列在发布这些元素之后会自动结束。
     * fromArray()，fromIterable()和 fromStream()：可以从一个数组、Iterable 对象或 Stream 对象中创建 Flux 对象。
     * empty()：创建一个不包含任何元素，只发布结束消息的序列。
     * error(Throwable error)：创建一个只包含错误消息的序列。
     * never()：创建一个不包含任何消息通知的序列。
     * range(int start, int count)：创建包含从 start 起始的 count 个数量的 Integer 对象的序列。
     * interval(Duration period)和 interval(Duration delay, Duration period)：创建一个包含了从 0 开始递增的 Long 对象的序列。其中包含的元素按照指定的间隔来发布。除了间隔时间之外，还可以指定起始元素发布之前的延迟时间。
     * intervalMillis(long period)和 intervalMillis(long delay, long period)：与 interval()方法的作用相同，只不过该方法通过毫秒数来指定时间间隔和延迟时间。
     */
    @Test
    public void test03() {
        Flux.just("Hello", "World").subscribe(System.out::println);
        Flux.fromArray(new Integer[] {1, 2, 3}).subscribe(System.out::println);
        Flux.empty().subscribe(System.out::println);
        Flux.range(1, 10).subscribe(System.out::println);
        Flux.interval(Duration.of(10, ChronoUnit.SECONDS)).subscribe(System.out::println);
        Flux.interval(Duration.of(1, ChronoUnit.SECONDS)).subscribe(System.out::println);
    }

    @Test
    public void test04() {
        Flux.just(1, 2)
                .concatWith(Mono.error(new IllegalStateException()))
                .subscribe(System.out::println, System.err::println);

    }

    /**
     * generate()方法通过同步和逐一的方式来产生 Flux 序列
     * next()方法只能最多被调用一次
     */
    @Test
    public void test05() {
        Flux.generate(sink -> {
            sink.next("Hello");
            // 通过complete结束序列
            sink.complete();
        }).subscribe(System.out::println);

        final Random random = new Random();
        Flux.generate(ArrayList::new, (list, sink) -> {
            int value = random.nextInt(100);
            list.add(value);
            sink.next(value);
            if (list.size() == 10) {
                sink.complete();
            }
            return list;
        }).subscribe(System.out::println);

    }

    /**
     * create()方法与 generate()方法的不同之处在于所使用的是 FluxSink 对象。
     * FluxSink 支持同步和异步的消息产生，并且可以在一次调用中产生多个元素。可以多次调用next()
     * 在代码在一次调用中就产生了全部的 10 个元素。
     */
    @Test
    public void test06() {
        Flux.create(sink -> {
            for (int i = 0; i < 2; i++) {
                sink.next(i + 100);
            }
            sink.complete();
        }).subscribe(System.out::println);
    }

    /**
     * 创建Mono
     */
    @Test
    public void test07() {
        Mono.fromSupplier(() -> "Hello").subscribe(System.out::println);
        Mono.justOrEmpty(Optional.of("Hello")).subscribe(System.out::println);
        Mono.create(sink -> sink.success("Hello")).subscribe(System.out::println);
    }

    /**
     * 第一行语句输出的是 5 个包含 20 个元素的数组；
     * 第二行语句输出的是 2 个包含了 10 个元素的数组；
     * 第三行语句输出的是 5 个包含 2 个元素的数组。每当遇到一个偶数就会结束当前的收集；
     * 第四行语句输出的是 5 个包含 1 个元素的数组，数组里面包含的只有偶数。
     */
    @Test
    public void test08() {
        Flux.range(1, 100).buffer(20).subscribe(System.out::println);
        Flux.interval(Duration.of(100, ChronoUnit.MILLIS)).bufferTimeout(1001, Duration.of(1001, ChronoUnit.MILLIS)).take(2).toStream().forEach(System.out::println);
        Flux.range(1, 10).bufferUntil(i -> i % 2 == 0).subscribe(System.out::println);
        Flux.range(1, 10).bufferWhile(i -> i % 2 == 0).subscribe(System.out::println);
    }

    /**
     * filter
     *
     * zipWith 操作符把当前流中的元素与另外一个流中的元素按照一对一的方式进行合并。
     * 在合并时可以不做任何处理，由此得到的是一个元素类型为 Tuple2 的流；
     * 也可以通过一个 BiFunction 函数对合并的元素进行处理，所得到的流的元素类型为该函数的返回值。
     */
    @Test
    public void test09() {
        Flux.range(1, 10).filter(i -> i % 2 == 0).subscribe(System.out::println);

        Flux.just("a", "b")
                .zipWith(Flux.just("c", "d"))
                .subscribe(System.out::println);
        Flux.just("a", "b")
                .zipWith(Flux.just("c", "d"), (s1, s2) -> String.format("%s-%s", s1, s2))
                .subscribe(System.out::println);
    }

    /**
     * reduce 和 reduceWith 操作符对流中包含的所有元素进行累积操作，得到一个包含计算结果的 Mono 序列。
     * 累积操作是通过一个 BiFunction 来表示的。在操作时可以指定一个初始值。如果没有初始值，则序列的第一个元素作为初始值。
     */
    @Test
    public void test10() {
        Flux.range(1, 100).reduce((x, y) -> x + y).subscribe(System.out::println);
        Flux.range(1, 100000).reduceWith(() -> 100, (x, y) -> x + y).subscribe(System.out::println);

    }

    /**
     * merge 和 mergeSequential 操作符用来把多个流合并成一个 Flux 序列。
     * 不同之处在于 merge 按照所有流中元素的实际产生顺序来合并，
     * 而 mergeSequential 则按照所有流被订阅的顺序，以流为单位进行合并。
     */
    @Test
    public void test11() {
        Flux.merge(Flux.interval(Duration.ofMillis(0L), Duration.ofMillis(100L)).take(5), Flux.interval(Duration.ofMillis(50L), Duration.ofMillis(100L)).take(5))
                .toStream()
                .forEach(System.out::println);
        Flux.mergeSequential(Flux.interval(Duration.ofMillis(0L), Duration.ofMillis(100L)).take(5), Flux.interval(Duration.ofMillis(50L), Duration.ofMillis(100L)).take(5))
                .toStream()
                .forEach(System.out::println);
    }

    /**
     * flatMap 和 flatMapSequential 操作符把流中的每个元素转换成一个流，再把所有流中的元素进行合并。
     * flatMapSequential 和 flatMap 之间的区别与 mergeSequential 和 merge 之间的区别是一样的。
     */
    @Test
    public void test12() {
        Flux.just(5, 10)
                .flatMap(x -> Flux.interval(Duration.ofMillis(x * 10L), Duration.ofMillis(100L)).take(x))
                .toStream()
                .forEach(System.out::println);
    }

    /**
     * 处理正常与异常消息
     */
    @Test
    public void test13() {
        Flux.just(1, 2)
                .concatWith(Mono.error(new IllegalStateException()))
                .subscribe(System.out::println, System.err::println);

        System.out.println("===========================");
        // 出错时返回默认值
        Flux.just(1, 2)
                .concatWith(Mono.error(new IllegalStateException()))
                .onErrorReturn(0)
                .subscribe(System.out::println);
        System.out.println("===========================");
        // 出错时切换另一个流（新版本不支持）
//        Flux.just(1, 2)
//                .concatWith(Mono.error(new IllegalStateException()))
//                .sw(Mono.just(0))
//                .subscribe(System.out::println);

        System.out.println("===========================");
        // 出错时 根据异常类型选择要使用的产生元素的流
        Flux.just(1, 2)
                .concatWith(Mono.error(new IllegalArgumentException()))
                .onErrorResume(e -> {
                    if (e instanceof IllegalStateException) {
                        return Mono.just(0);
                    } else if (e instanceof IllegalArgumentException) {
                        return Mono.just(-1);
                    }
                    return Mono.empty();
                })
                .subscribe(System.out::println);
        System.out.println("===========================");
        // 当出现错误时，还可以通过 retry 操作符来进行重试。
        // 重试的动作是通过重新订阅序列来实现的。在使用 retry 操作符时可以指定重试的次数
        Flux.just(1, 2)
                .concatWith(Mono.error(new IllegalStateException()))
                .retry(1)
                .subscribe(System.out::println);
    }

    /**
     * 使用 create()方法创建一个新的 Flux 对象，其中包含唯一的元素是当前线程的名称。
     * 接着是两对 publishOn()和 map()方法，其作用是先切换执行时的调度器，再把当前的线程名称作为前缀添加
     */
    @Test
    public void test14() {
        Flux.create(sink -> {
            sink.next(Thread.currentThread().getName());
            sink.complete();
        })
                .publishOn(Schedulers.single())
                .map(x -> String.format("[%s] %s", Thread.currentThread().getName(), x))
                .publishOn(Schedulers.elastic())
                .map(x -> String.format("[%s] %s", Thread.currentThread().getName(), x))
                .subscribeOn(Schedulers.parallel())
                .toStream()
                .forEach(System.out::println);
        /**
         * toStream()会把异步流转换为同步流输出元素。
         * 因此先会是 parallel线程，然后以当前线程名称作为前缀，依次执行map操作。
         */
    }

    /**
     * reactor的调试
     */
    @Test
    public void test15() {
        StepVerifier.create(Flux.just("a", "b"))
                .expectNext("a")
                .expectNext("b")
                .verifyComplete();
    }

    /**
     * 日志记录
     */
    @Test
    public void test16() {
        Flux.range(1, 2).log("Range").subscribe(System.out::println);
    }

    /**
     * 热序列
     *
     * 通过 publish()方法把一个 Flux 对象转换成 ConnectableFlux 对象。
     * 方法 autoConnect()的作用是当 ConnectableFlux 对象有一个订阅者时就开始产生消息。
     * 代码 source.subscribe()的作用是订阅该 ConnectableFlux 对象，让其开始产生数据。
     * 接着当前线程睡眠 5 秒钟，第二个订阅者此时只能获得到该序列中的后 5 个元素，因此所输出的是数字 5 到 9
     */
    @Test
    public void test17() throws InterruptedException {
        final Flux<Long> source = Flux.interval(Duration.ofMillis(1000L))
                .take(10)
                .publish()
                .autoConnect();
        source.subscribe();
        Thread.sleep(5000);
        source
                .toStream()
                .forEach(System.out::println);
    }
}
