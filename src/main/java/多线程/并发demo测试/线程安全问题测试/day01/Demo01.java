package 多线程.并发demo测试.线程安全问题测试.day01;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * <Description>
 * 同一个对象创建100个线程，去同时访问对象的属性。
 * 结果： 线程不安全。在多次测试下，得到9999
 * @author wangxi
 */
public class Demo01 {
    private Integer count = 0;

    private AtomicInteger num = new AtomicInteger(0);  // 这样可以获取到10000

    private void fun() {
        for (int i = 0; i < 10000; i++) {
            Thread thread = new Thread(() -> {
                // 这样写法可以保证原子性可见性，因此不用加volatile(加volatile反而不行)
                synchronized (count) {
                    count++;
                }
                num.getAndIncrement();
            });
            thread.start();
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(count);
        //System.out.println(num);
    }

    public static void main(String[] args) {
        new Demo01().fun();
    }
}

