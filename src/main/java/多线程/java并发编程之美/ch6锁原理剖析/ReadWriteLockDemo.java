package 多线程.java并发编程之美.ch6锁原理剖析;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @Author wangxi
 * @Time 2020/1/5 20:13
 *
 * 测试ReadWriteLock
 */
public class ReadWriteLockDemo {
    public static void main(String[] args) {
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        ReentrantReadWriteLock.ReadLock readLock = lock.readLock();
        readLock.lock();
        readLock.unlock();

        ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();
        writeLock.lock();
        writeLock.unlock();
    }
}
