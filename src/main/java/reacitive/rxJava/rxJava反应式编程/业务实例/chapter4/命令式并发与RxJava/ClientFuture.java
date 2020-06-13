package reacitive.rxJava.rxJava反应式编程.业务实例.chapter4.命令式并发与RxJava;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author wangxi created on 2020/6/12 23:09
 * @version v1.0
 */
public class ClientFuture {
    // 注入bean

    Flight flight = new Flight();
    Passenger passenger = new Passenger();
    Ticket ticket = new Ticket();
    ExecutorService executorService = Executors.newFixedThreadPool(10);

    public void futureInvoke() throws InterruptedException {
        String flightNo = "MH123";
        long passengerId = 123L;
        CompletableFuture<Flight> flightFuture = CompletableFuture.supplyAsync(() -> flight.findFlight(flightNo), executorService);
        CompletableFuture<Passenger> passengerFuture = CompletableFuture.supplyAsync(() -> passenger.findPassenger(passengerId), executorService);

        Thread.sleep(2000);
    }

    public static void main(String[] args) throws InterruptedException {
        ClientFuture clientFuture = new ClientFuture();
        long start = System.currentTimeMillis();
        clientFuture.futureInvoke();

        System.out.println("执行时间: " + (System.currentTimeMillis() - start));

        clientFuture.executorService.shutdown();
    }
}
