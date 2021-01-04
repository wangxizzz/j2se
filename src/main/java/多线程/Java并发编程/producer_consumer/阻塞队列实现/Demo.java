package 多线程.Java并发编程.producer_consumer.阻塞队列实现;

import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @author wangxi03 created on 2021/1/4 5:44 下午
 * @version v1.0
 */
public class Demo {
    public static void main(String[] args) {
        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(12);
        Demo demo = new Demo();
        new Thread(() -> {
            try {
                demo.produce(queue);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                demo.consume(queue);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void produce(BlockingQueue<Integer> queue) throws InterruptedException {
        while (true) {
            queue.put(new Random().nextInt(1000));
            Thread.sleep(1000);
        }
    }

    private void consume(BlockingQueue<Integer> queue) throws InterruptedException {
        while (true) {
            System.out.println("元素消费: " + queue.take());
        }
    }
}
