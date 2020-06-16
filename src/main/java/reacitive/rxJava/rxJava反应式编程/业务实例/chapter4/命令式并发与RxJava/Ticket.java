package reacitive.rxJava.rxJava反应式编程.业务实例.chapter4.命令式并发与RxJava;

import io.reactivex.Observable;

import java.util.concurrent.CompletableFuture;

/**
 * @author wangxi created on 2020/6/12 22:58
 * @version v1.0
 */
public class Ticket {

    // 阻塞
    public Ticket bookTicket(Flight flight, Passenger passenger) {

        System.out.println("bookTicket 被调用 threadName = " + Thread.currentThread().getName());
        return new Ticket();
    }

    // 响应式
    public Observable<Ticket> rxBookTicket(Flight flight, Passenger passenger) {

        return Observable.just(bookTicket(flight, passenger));
    }

    public CompletableFuture<Ticket> bookTicketAsync(Flight flight) {
        return CompletableFuture.supplyAsync(() -> bookTicket(flight, null));
    }
}
