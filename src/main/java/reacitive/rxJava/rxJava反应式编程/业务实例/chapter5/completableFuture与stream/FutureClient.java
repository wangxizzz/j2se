package reacitive.rxJava.rxJava反应式编程.业务实例.chapter5.completableFuture与stream;

import reacitive.rxJava.rxJava反应式编程.业务实例.chapter4.命令式并发与RxJava.Flight;
import reacitive.rxJava.rxJava反应式编程.业务实例.chapter4.命令式并发与RxJava.Ticket;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * @author wangxi created on 2020/6/16 22:16
 * @version v1.0
 */
public class FutureClient {

    // 注入bean

    User user = new User();
    GeoLocation geoLocation = new GeoLocation();
    Flight flight = new Flight();
    Ticket ticket = new Ticket();

    /**
     * 业务含义如下：
     * 构建参数，遍历所有供应商，返回第一个查询完成的机票Flight，然后去book
     *
     * 样例异步地获取User和GeoLocation信息。这两个操作是独立的，可以并发运行。
     * 但是想要获取它们的结果，显然不能阻塞和浪费客户端线程。这就是thenCombine()做的事情。
     * 它接收两个CompletableFuture（user和location），并在两者都完成的时候，异步执行一个回调。
     * 有意思的是，这个回调可以返回一个值，这个值将会成为最终形成的CompletableFuture的新内容
     *
     * CompletableFuture.applyToEither()操作符接收两个CompletableFuture，
     * 并将给定的转换函数应用到第一个完成的CompletableFuture上。如果你有两个同种类型的任务，
     * 并且只关心第一个完成的，那么applyToEither()转换是非常有用的
     *
     * applyToEither()只能与两个CompletableFuture组合使用，
     * 而看上去有点怪异的anyOf()能够接收任意数量的CompletableFuture。
     * 幸而，还可以使用前两个Future来调用applyToEither()，
     * 然后将得到的结果（前两个中较快的一个）应用到第三个上游Future中（从而得到前三个中最快的一个）。
     * 通过递归调用applyToEither()，我们能够得到整体最快的CompletableFuture。
     * 这个便利的技巧可以通过reduce()操作符来实现。最后一个提示是Function中的identity()方法，
     * 这是applyToEither()需要的，我们必须提供一个转换功能来处理得到的第一个结果。
     * 如果想要原样保留结果，那么可以使用一个恒等函数，写成f -> f或(Flight f) -> f的形式。
     *
     * 最终实现的CompletableFuture<Flight>会在最快的TravelAgency响应时完成，这个过程是异步进行的。
     * thenCombine()的结果还有点小问题。不管传递给thenCombine()的转换内容是什么，
     * 返回的结果都会包装在一个CompletableFuture中。我们的场景中返回的是CompletableFuture<Flight>，
     * 所以thenCombine()的结果类型就是CompletableFuture<CompletableFuture<Flight>>。
     * 在使用Observable的时候，双重包装也是很常见的问题，可以使用相同的技巧来应对这两种情况：flatMap()!
     * 。但是，就像map()在Future中被称为thenApply()一样，flatMap()    也被称为thenCompose()。
     */
    // 最终的写法
    public void funAsync() {
        long userId = 1L;
        List<TravelAgency> travelAgencies = new ArrayList<>();
        CompletableFuture<User> userFuture = user.findByIdAsync(userId);
        CompletableFuture<GeoLocation> geoLocationFuture = geoLocation.locateAsync();

        CompletableFuture<Ticket> ticketFuture = userFuture
                .thenCombine(geoLocationFuture, ((userResult, locationResult) -> {
            return travelAgencies.stream()
                    .map(agency -> agency.searchFlightAysnc(userResult, locationResult))
                    .reduce((f1, f2) -> f1.applyToEither(f2, x -> identity(x)))
                    .get();
            // 在所有供应商search机票任务，找到第一个完成的，后续任务丢弃。上述使用reduce的技巧
        }))
                // 类似flatMap压平操作
                .thenCompose(x -> x)
                .thenCompose(flightRes -> ticket.bookTicketAsync(flight));
    }


    // 上述业务写法2
    // 在所有供应商search机票任务，找到第一个完成的，后续任务丢弃。使用anyOf的写法
    public void fun01() {
        long userId = 1L;
        List<TravelAgency> travelAgencies = new ArrayList<>();
        CompletableFuture<User> userFuture = user.findByIdAsync(userId);
        CompletableFuture<GeoLocation> geoLocationFuture = geoLocation.locateAsync();

        CompletableFuture<Ticket> ticketCompletableFuture = userFuture.thenCombine(geoLocationFuture, ((userResult, locationResult) -> {
            List<CompletableFuture<Flight>> collects = travelAgencies.stream()
                    .map(agency -> agency.searchFlightAysnc(userResult, locationResult))
                    .collect(Collectors.toList());
            CompletableFuture[] futureArray = collects.toArray(new CompletableFuture[0]);
            // thenApply()会对Future返回的值执行动态转换，这类似于Observable.map()
            // anyOf 底层返回的是 Object类型
            return CompletableFuture.anyOf(futureArray).thenApply(x -> (Flight) x);
        })).thenCompose(x -> x)
                .thenCompose(flightRes -> ticket.bookTicketAsync(flight));

    }

    private Flight identity(Flight flight) {
        return flight;
    }
}
