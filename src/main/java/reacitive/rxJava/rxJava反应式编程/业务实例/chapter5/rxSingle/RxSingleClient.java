package reacitive.rxJava.rxJava反应式编程.业务实例.chapter5.rxSingle;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import org.junit.Test;

/**
 * @author wangxi created on 2020/6/21 13:19
 * @version v1.0
 *
 * Single语义：
 *
 * 使用Single作为Observable，只发布一个值和完成通知（或错误通知）。
 * Single缺少Observable支持的操作符的时候，cache()就是一个例子
 */
public class RxSingleClient {
    @Test
    public void fun01() {
        Single.just("hello")
                .subscribe(x -> System.out.println(x));

        // Single本身就表示只发送单个元素，因此不需要 OnCompleted()方法
        Single.error(new RuntimeException("ex"))
                .observeOn(Schedulers.io())
                .subscribe(x -> System.out.println(x), Throwable::printStackTrace);
    }


}
