package reacitive.rxJava.rxJava反应式编程.业务实例.chapter5.completableFuture与stream;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import org.apache.commons.lang3.tuple.Pair;
import reacitive.rxJava.rxJava反应式编程.业务实例.chapter4.命令式并发与RxJava.Flight;
import reacitive.rxJava.rxJava反应式编程.业务实例.chapter4.命令式并发与RxJava.Ticket;

import java.util.ArrayList;

/**
 * @author wangxi created on 2020/6/16 22:16
 * @version v1.0
 *
 * 使用rx方式包装CompletableFuture
 */
public class RxClient {

    User user = new User();
    GeoLocation geoLocation = new GeoLocation();
    Flight flight = new Flight();
    Ticket ticket = new Ticket();
    TravelAgency travelAgency = new TravelAgency();

    public Observable<User> rxFindById(long id) {
        return RxUtil.observe(user.findByIdAsync(id));
    }

    public Observable<GeoLocation> rxLocate() {
        return RxUtil.observe(geoLocation.locateAsync());
    }

    public Observable<Ticket> rxBook(Flight flight) {
        return RxUtil.observe(ticket.bookTicketAsync(flight));
    }

    // 实现方式一
    public void fun01() {
        // agencies来源于Observable
        Flight defaultFlight = new Flight();
        Observable<TravelAgency> agencies = Observable.fromIterable(new ArrayList<>());
        long id = 1L;
        Observable<User> rxUser = rxFindById(id);
        Observable<GeoLocation> rxLocate = rxLocate();
        Observable<Ticket> rxTicket = rxUser
                .zipWith(rxLocate, (rxU, rxLo) -> {
            Maybe<Flight> first = agencies.map(agency -> agency.rxSearchFlight(rxU, rxLo))
                    .flatMap(x -> x)
                    .firstElement();
            return first.toObservable();
        })
                .flatMap(x -> x)
                .flatMap(f -> rxBook(f));

    }

    // 实现方式二
    public void fun02() {
        // agencies来源于Observable
        Flight defaultFlight = new Flight();
        Observable<TravelAgency> agencies = Observable.fromIterable(new ArrayList<>());
        long id = 1L;
        Observable<User> rxUser = rxFindById(id);
        Observable<GeoLocation> rxLocate = rxLocate();
        Observable<Ticket> ticketObservable = rxUser.zipWith(rxLocate, (u, l) -> Pair.of(u, l))
                .flatMap(pair -> {
                    return agencies.flatMap(agency -> agency.rxSearchFlight(pair.getLeft(), pair.getRight()));
                })
                .firstElement()
                .toObservable()
                .flatMap(f -> rxBook(f));

    }
}
