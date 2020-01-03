package 多线程.java并发编程之美.ch6锁原理剖析;

import java.util.concurrent.locks.LockSupport;

/**
 * @Author wangxi
 * @Time 2019/12/29 00:00
 */
public class LockSupport01 {
    public static void main(String[] args) {
        System.out.println("aaa");
        // 线程挂起
        LockSupport.park();

        System.out.println("bbb");
    }
}
