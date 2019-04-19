package 多线程.Java并发编程.day14无锁CAS操作以及Java中Atomic;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author:王喜
 * @Description : atomic包下的原子类都是基于无锁的CAS
 * @Date: 2018/4/29 0029 21:28
 */
public class AtomicIntegerDemo {
    private static AtomicInteger integer = new AtomicInteger();

    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10000; i++) {
                    System.out.println(integer.incrementAndGet());
                }
            }
        }).start();
    }
}
