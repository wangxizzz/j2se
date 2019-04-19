package 多线程.Java并发编程.day5Lock锁与线程间通信;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author:王喜
 * @Description : 不加锁
 * @Date: 2018/4/27 0027 18:32
 */
public class RunWithoutLock {

    public static void main(String[] args) {
        Lock lock = new ReentrantLock();

        new Thread(() -> runMethod(lock, 0), "thread1").start();
        new Thread(() -> runMethod(lock, 5000), "thread2").start();
        new Thread(() -> runMethod(lock, 1000), "thread3").start();
        new Thread(() -> runMethod(lock, 5000), "thread4").start();
        new Thread(() -> runMethod(lock, 1000), "thread5").start();
    }
    private static void runMethod(Lock lock, long sleepTime) {
        /**
         * 如果不加锁，上面5个线程就会并发的执行下面这段代码，并且没有先后次序
         */
//        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "进来了");
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println(Thread.currentThread().getName() + "执行完了");
//            lock.unlock();
        }
    }
}
