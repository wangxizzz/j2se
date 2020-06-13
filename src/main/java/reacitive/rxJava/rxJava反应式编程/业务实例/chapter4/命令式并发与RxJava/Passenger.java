package reacitive.rxJava.rxJava反应式编程.业务实例.chapter4.命令式并发与RxJava;

import io.reactivex.Observable;

/**
 * @author wangxi created on 2020/6/12 22:57
 * @version v1.0
 */
public class Passenger {

    // 阻塞
    public Passenger findPassenger(long passengerId) {

        System.out.println("findPassenger 被调用, threadName = " + Thread.currentThread().getName());
        return new Passenger();
    }

    public Observable<Passenger> rxFindPassenger(long passengerId) {
        // 包装为反应式

        return Observable.defer(() -> Observable.just(findPassenger(passengerId)));
    }
}
