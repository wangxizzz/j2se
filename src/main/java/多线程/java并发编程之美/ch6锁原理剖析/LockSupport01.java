package 多线程.java并发编程之美.ch6锁原理剖析;

import java.util.concurrent.locks.LockSupport;

/**
 * @Author wangxi
 * @Time 2019/12/29 00:00
 *
 * 测试线程挂起 LockSupport.park()挂起线程与unpark()
 */
public class LockSupport01 {
    // 一个main方法是对应一个JVM进程的
    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            System.out.println("thread begin park.");
            LockSupport.park();
            System.out.println("thread unparking");
        });
        thread.start();
        System.out.println("main thread begin unpark");
        // 需要传入被挂起线程 作为参数
        LockSupport.unpark(thread);
    }
}
