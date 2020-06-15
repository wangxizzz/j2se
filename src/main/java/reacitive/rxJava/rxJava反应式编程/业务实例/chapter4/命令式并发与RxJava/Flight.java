package reacitive.rxJava.rxJava反应式编程.业务实例.chapter4.命令式并发与RxJava;

import io.reactivex.Observable;

/**
 * @author wangxi created on 2020/6/12 22:57
 * @version v1.0
 */
public class Flight {

    // 阻塞代码
    public Flight findFlight(String flightNo) {

        System.out.println("findFlight 被调用 threadName = " + Thread.currentThread().getName());
        return doGetFlight();
    }

    public Observable<Flight> rxFindFlight(String flightNo) {
        // 包装为反应式
        return Observable.defer(() -> Observable.just(findFlight(flightNo)));
    }

    public Flight doGetFlight() {
        // 阻塞调用(dubbo、db)
        return new Flight();
    }
}
