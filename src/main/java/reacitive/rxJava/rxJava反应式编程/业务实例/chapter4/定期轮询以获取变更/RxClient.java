package reacitive.rxJava.rxJava反应式编程.业务实例.chapter4.定期轮询以获取变更;

import io.reactivex.Observable;

import java.util.concurrent.TimeUnit;

/**
 * @author wangxi created on 2020/6/13 17:32
 * @version v1.0
 */
public class RxClient {

    Item item = new Item();

    public Observable<Item> observeNewItems() {
        return Observable.interval(1, TimeUnit.SECONDS)
                .flatMapIterable(i -> item.query())
                // 返回不同的item
                .distinct();
    }

    /**
     * 应用场景如下：
     *
     * 我们就可以使用定期轮询(interval)替换大量的Thread.sleep()调用和手动缓存。
     * 它适用于很多场景，比如文件传输协议（File Transfer Protocol, FTP）轮询、Web抓取等
     */
}
