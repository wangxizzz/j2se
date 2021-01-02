package 多线程.业务实例.热点探测demo;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author wangxi created on 2021/1/2 16:34
 * @version v1.0
 */
@Slf4j
public class HotKeyDetectorTest {
    public static void main(String[] args) throws InterruptedException {
        HotKeyDetector instance = HotKeyDetector.INSTANCE;

        Executor executor = Executors.newFixedThreadPool(2000);
        new Thread(() -> {
            instance.init();
        }).start();

        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
               log.info("map : {}", instance.map);
            }
        }).start();

        Random random = new Random();

        while (true) {
            Thread.sleep(200);
            for (int i = 0; i < 20; i++) {
                executor.execute(() -> instance.statistic("wangxi" + random.nextInt(5)));
            }
        }


    }
}
