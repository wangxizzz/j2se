package 多线程.java并发编程之美.ch6锁原理剖析;

import java.util.concurrent.locks.LockSupport;

/**
 * @Author wangxi
 * @Time 2020/1/12 22:58
 *
 * 测试LockSupport API  测试park超时
 */
public class LockSupport02 {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            System.out.println("thread begin park.");
            // 超过 0.1s，自己返回
            LockSupport.parkNanos(100000000L);
            System.out.println("thread unparking");
        });
        thread.start();
        Thread.sleep(3000);
        System.out.println("main thread begin unpark");
        // 需要传入被挂起线程 作为参数
        LockSupport.unpark(thread);
    }
}
