package 多线程.java并发编程之美.ch1并发基础.等待通知机制;


import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

/**
 * @Author wangxi
 * @Time 2019/12/14 17:24
 *
 * 利用wait-notify实现生产者消费者
 */
@Slf4j
public class ProviderConsumer {
    public static int MAX_SIZE = 5;
    public static Queue<Integer> queue = new LinkedList<>();

    public void provider() {
        while (true) {
            synchronized (queue) {
                while (queue.size() == MAX_SIZE) {
                    try {
                        log.info("provider wait...");
                        queue.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        // 注意：这里需要唤醒，否则会造成死锁
                        queue.notify();
                    }
                }
                queue.add(new Random().nextInt(100));
                log.info("provider add . size = {}", queue.size());
                // 阻塞在queue上的线程被唤醒后，处于就绪状态，即除了cpu资源外其他资源全部都获取到了
                queue.notifyAll();
            }
        }
    }

    public void consumer() {
        while (true) {
            synchronized (queue) {
                // 防止虚假唤醒
                while (queue.isEmpty()) {
                    try {
                        log.info("consumer wait...");
                        queue.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        queue.notify();
                    }
                }
                queue.poll();
                log.info("consumer poll. queue size = {}", queue.size());
                queue.notifyAll();
            }
        }
    }

    public static void main(String[] args) {
        ProviderConsumer demo01 = new ProviderConsumer();
        new Thread(() -> {
            demo01.provider();
        }).start();
        new Thread(() -> {
            demo01.consumer();
        }).start();

    }
}
