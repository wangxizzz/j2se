package reacitive.rxJava.业务实例.命令式并发与RxJava;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import org.apache.commons.lang3.tuple.Pair;
import reactor.core.scheduler.Scheduler;

import java.util.concurrent.TimeUnit;

/**
 * @author wangxi created on 2020/6/12 23:09
 * @version v1.0
 */
public class ClientRx {

    // 注入bean

    Flight flight = new Flight();
    Passenger passenger = new Passenger();
    Ticket ticket = new Ticket();

    public void rxInvoke01() {
        String flightNo = "MH123";
        long passengerId = 123L;
        Observable<Flight> flightObservable =
                flight.rxFindFlight(flightNo);
//                .subscribeOn(Schedulers.io())
//                .timeout(3, TimeUnit.SECONDS);
        Observable<Passenger> passengerObservable = passenger.rxFindFlight(passengerId);

        // bookTicket非Rx
        Observable<Ticket> ticketObservable = flightObservable
                .zipWith(passengerObservable, (f, p) -> ticket.bookTicket(f, p));
        // 订阅
//        ticketObservable.subscribe(this::sendMail);
    }


    public void rxInvoke02() throws InterruptedException {
        String flightNo = "MH123";
        long passengerId = 123L;
        Observable<Ticket> ticketObservable = flight.rxFindFlight(flightNo)
                .zipWith(passenger.rxFindFlight(passengerId), (f, p) -> Pair.of(f, p))
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
