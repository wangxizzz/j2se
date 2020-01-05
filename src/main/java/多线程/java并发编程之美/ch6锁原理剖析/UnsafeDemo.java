package 多线程.java并发编程之美.ch6锁原理剖析;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @Author wangxi
 * @Time 2020/1/4 00:03
 * 测试unsafe的函数
 */
public class UnsafeDemo {
    private static Unsafe unSafe;
    private static long stateOffset;
    private volatile int state = 0;

    static {
        try {
            // 利用反射创建unSafe实例
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unSafe = (Unsafe)field.get(null);
            // 获取state变量在UnSafeDemo类的偏移量
            stateOffset = unSafe.objectFieldOffset(UnsafeDemo.class.getDeclaredField("state"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        UnsafeDemo demo = new UnsafeDemo();
        // 获取类加载器
        System.out.println(UnsafeDemo.class.getClassLoader());
        // 把state值设置为1
        boolean success = unSafe.compareAndSwapInt(demo, stateOffset, 0, 1);
        System.out.println(success);
    }
}
