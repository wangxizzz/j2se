package reacitive.rxJava.rxJava反应式编程.业务实例.chapter5.completableFuture与stream;

import java.util.concurrent.CompletableFuture;

/**
 * @author wangxi created on 2020/6/16 22:15
 * @version v1.0
 */
public class GeoLocation {

    private GeoLocation locate() {

        return new GeoLocation();
    }

    public CompletableFuture<GeoLocation> locateAsync() {
        return CompletableFuture.supplyAsync(this::locate);
    }
}
