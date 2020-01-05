package 多线程.java并发编程之美.ch6锁原理剖析;

import sun.misc.Unsafe;

/**
 * @Author wangxi
 * @Time 2020/1/4 00:03
 * 测试unsafe的函数
 */
public class UnsafeDemo {
    private static final Unsafe unSafe = Unsafe.getUnsafe();
    private static long stateOffset;
    private volatile int state = 0;

    static {
        try {
            // 获取state变量在UnSafeDemo类的偏移量
            stateOffset = unSafe.objectFieldOffset(UnsafeDemo.class.getDeclaredField("state"));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        UnsafeDemo demo = new UnsafeDemo();
        // 把state值设置为1
        boolean success = unSafe.compareAndSwapInt(demo, stateOffset, 0, 1);
        System.out.println(success);
    }
}
