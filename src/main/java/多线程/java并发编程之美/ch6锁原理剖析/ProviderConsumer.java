package 多线程.java并发编程之美.ch6锁原理剖析;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author wangxi
 * @Time 2020/1/5 19:52
 * 交替打印生产消费
 */
public class ProviderConsumer {
    public static final Lock lock = new ReentrantLock();
    public static final Condition notEmpty = lock.newCondition();
    public static final Condition notFull = lock.newCondition();
    public static final Queue<Integer> queue = new LinkedList<>();
    public static final int MAX_SIZE = 5;

    public void produce() {
        while (true) {
            try {
                lock.lock();
                while (queue.size() == MAX_SIZE) {
                    // 防止虚假唤醒
                    notFull.await();
                }
                queue.offer(1);
                System.out.println("生产一个元素 size = " + queue.size());
                notEmpty.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

    public void consume() {
        while(true) {
            try {
                lock.lock();
                while (queue.isEmpty()) {
                    notEmpty.await();
                }
                queue.poll();
                System.out.println("消费一个元素 size = " + queue.size());
                notFull.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        ProviderConsumer obj = new ProviderConsumer();
        new Thread(() -> {
            obj.produce();
        }).start();

        new Thread(() -> {
            obj.consume();
        }).start();
    }
}
