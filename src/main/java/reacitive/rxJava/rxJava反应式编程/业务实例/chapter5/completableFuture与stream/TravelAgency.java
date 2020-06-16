package reacitive.rxJava.rxJava反应式编程.业务实例.chapter5.completableFuture与stream;

import reacitive.rxJava.rxJava反应式编程.业务实例.chapter4.命令式并发与RxJava.Flight;

import java.util.concurrent.CompletableFuture;

/**
 * @author wangxi created on 2020/6/16 22:29
 * @version v1.0
 */
public class TravelAgency {
    public CompletableFuture<Flight> searchFlightAysnc(User user, GeoLocation location) {
        return CompletableFuture.supplyAsync(() -> search(user, location));
    }

    public Flight search(User user, GeoLocation location) {
        return new Flight();
    }
}
