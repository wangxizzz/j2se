package reacitive.rxJava.rxJava反应式编程.业务实例.chapter5.completableFuture与stream;

import io.reactivex.Observable;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author wangxi created on 2020/6/21 13:34
 * @version v1.0
 */
public class RxUtil {
    /**
     * CompletableFuture 转换为Observable
     * @param future
     * @param <T>
     * @return
     */
    public static <T> Observable<T> observe(CompletableFuture<T> future) {
        return Observable.create(subscriber -> {
            future.whenComplete((v, e) -> {
                if (e != null) {
                    subscriber.onError(e);
                } else {
                    subscriber.onNext(v);
                    subscriber.onComplete();
                }
            });
        });
    }

    /**
     * observable 转换为CompletableFuture
     *
     * @param observable
     * @param <T>
     * @return
     */
    public static <T> CompletableFuture<T> toFuture(Observable<T> observable) {
        CompletableFuture<T> future = new CompletableFuture<>();
        observable
                .singleElement()
                .subscribe(future::complete, future::completeExceptionally);
        return future;
    }

    public static <T> CompletableFuture<List<T>> toFutureList(Observable<T> observable) {
        return toFuture(observable.toList().toObservable());
    }
}
