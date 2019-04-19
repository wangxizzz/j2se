package 多线程.Java并发编程.day14无锁CAS操作以及Java中Atomic;

import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * @Author:王喜
 * @Description :
 * @Date: 2018/4/29 0029 21:44
 */
public class AtomicIntegerArrayDemo {
    private static int[] value = {1, 2, 3, 4, 5};
    private static AtomicIntegerArray atomic =
            new AtomicIntegerArray(value);

    public static void main(String[] args) {
        atomic.getAndSet(2, 100);
        System.out.println(atomic.get(2));
    }
}
