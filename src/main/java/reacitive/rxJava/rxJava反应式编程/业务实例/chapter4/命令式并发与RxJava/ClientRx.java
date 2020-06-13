package reacitive.rxJava.rxJava反应式编程.业务实例.chapter4.命令式并发与RxJava;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import org.apache.commons.lang3.tuple.Pair;

import java.util.concurrent.TimeUnit;

/**
 * @author wangxi created on 2020/6/12 23:09
 * @version v1.0
 *
 * Rx的本质是 组合 Observable
 */
public class ClientRx {

    // 注入bean

    Flight flight = new Flight();
    Passenger passenger = new Passenger();
    Ticket ticket = new Ticket();

    public void rxInvoke01() throws InterruptedException {
        String flightNo = "MH123";
        long passengerId = 123L;
        Observable<Flight> flightObservable =
                flight.rxFindFlight(flightNo)
                .subscribeOn(Schedulers.io())   // process切换线程池，采用的subscribeOn
                .timeout(3, TimeUnit.SECONDS);
        Observable<Passenger> passengerObservable =
                passenger.rxFindPassenger(passengerId)
                .subscribeOn(Schedulers.computation());

        // bookTicket非Rx
        Observable<Ticket> ticketObservable = flightObservable
                .zipWith(passengerObservable, (f, p) -> ticket.bookTicket(f, p));

        ticketObservable.subscribe(this::sendMail);
        // 订阅
        Thread.sleep(2000);
    }

    // bookTicket 为 Rx
    public void rxInvoke02() throws InterruptedException {
        String flightNo = "MH123";
        long passengerId = 123L;

        Observable<Ticket> ticketObservable = flight.rxFindFlight(flightNo)
                .zipWith(passenger.rxFindPassenger(passengerId), (f, p) -> Pair.of(f, p))
                .map(pair -> ticket.rxBookTicket(pair.getLeft(), pair.getRight()))
                .flatMap(x -> x);

        Thread.sleep(2000);
        ticketObservable.subscribe(this::sendMail);

    }

    public static void main(String[] args) throws Exception {
        ClientRx clientRx = new ClientRx();
        long start = System.currentTimeMillis();
        clientRx.rxInvoke02();

        System.out.println("执行时间: " + (System.currentTimeMillis() - start));
    }

    public void sendMail(Ticket ticket) {

    }
}
