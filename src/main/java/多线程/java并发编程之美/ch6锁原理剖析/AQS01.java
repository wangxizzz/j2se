package 多线程.java并发编程之美.ch6锁原理剖析;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @Author wangxi
 * @Time 2019/12/29 00:31
 */
public class AQS01 {
    public static void main(String[] args) throws InterruptedException {
        ReentrantLock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

        try {
            lock.lock();
            condition.await();
        } finally {
            lock.unlock();
        }
    }
}
