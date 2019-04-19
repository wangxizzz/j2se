package 多线程.Java并发编程.day5Lock锁与线程间通信;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author:王喜
 * @Description : 加锁
 * @Date: 2018/4/27 0027 18:32
 */
public class RunWithLock {
    public static void main(String[] args) {

        Lock lock = new ReentrantLock();

        new Thread(() -> runMethod(lock, 5), "thread1").start();
        new Thread(() -> runMethod(lock, 5000), "thread2").start();
        new Thread(() -> runMethod(lock, 1000), "thread3").start();
        new Thread(() -> runMethod(lock, 5000), "thread4").start();
        new Thread(() -> runMethod(lock, 1000), "thread5").start();
    }

    private static void runMethod(Lock lock, long sleepTime) {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "上锁了");
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println(Thread.currentThread().getName() + "释放锁锁了");
            lock.unlock();
        }
    }
}
