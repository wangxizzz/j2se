package reacitive.rxJava.业务实例.命令式并发与RxJava;

import io.reactivex.Observable;

/**
 * @author wangxi created on 2020/6/12 22:58
 * @version v1.0
 */
public class Ticket {

    // 阻塞
    public Ticket bookTicket(Flight flight, Passenger passenger) {

        System.out.println("bookTicket 被调用");
        return new Ticket();
    }

    // 响应式
    public Observable<Ticket> rxBookTicket(Flight flight, Passenger passenger) {

        return Observable.just(new Ticket());
    }
}
