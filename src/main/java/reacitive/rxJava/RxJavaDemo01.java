package reacitive.rxJava;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;


/**
 * @author wangxi created on 2020/6/3 23:03
 * @version v1.0
 *
 * 1、https://yq.aliyun.com/articles/649659?spm=a2c4e.11153940.0.0.76352b5fkV9xYn
 * 2、https://yq.aliyun.com/articles/649658
 * 3、https://www.jianshu.com/p/0cd258eecf60
 */
@Slf4j
public class RxJavaDemo01 {
    @Test
    public void test01() {
        Observable.create((ObservableOnSubscribe<Integer>) e -> {
            log.info("subscribe");
            log.info( "currentThread name: {}" ,Thread.currentThread().getName());
            e.onNext(1);
            e.onNext(2);
            e.onNext(3);
            e.onComplete();
        }).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                log.info( "onSubscribe");
            }

            @Override
            public void onNext(Integer integer) {
                log.info( "onNext: {}", integer);
            }

            @Override
            public void onError(Throwable e) {
                log.info("onError: {}", e.getMessage());
            }

            @Override
            public void onComplete() {
                log.info("onComplete");
            }
        });
    }

    @Test
    public void test02() {
        Integer[] arrays = {1,2,3,4};  // 非要是Integer类型数组
        Observable.fromArray(arrays).subscribe(new Observer<Integer>() {
        //Observable.just(1, 2, 3).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("onSubscribe");
            }

            @Override
            public void onNext(Integer integer) {
                System.out.println(integer);
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("onError");
            }

            @Override
            public void onComplete() {
                System.out.println("onComplete");
            }
        });
    }
}
