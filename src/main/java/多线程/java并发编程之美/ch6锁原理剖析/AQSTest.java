package 多线程.java并发编程之美.ch6锁原理剖析;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author wangxi created on 2020/12/29 00:45
 * @version v1.0
 */
public class AQSTest {
    public static void main(String[] args) {
        Lock lock = new ReentrantLock();
        for (int i = 0; i < 40; i++) {
            new Thread(() -> {
                try {
                    lock.lock();
                    System.out.println("获得锁");
                    Thread.sleep(3000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }, "thread :" + i).start();
        }
    }
}
