package 多线程.业务实例.死循环;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author wangxi created on 2020/9/12 21:07
 * @version v1.0
 */
public class Test {

    static boolean flag = true;

    static AtomicInteger count = new AtomicInteger(0);

    public static void main(String[] args) {
        new Thread(() -> {
            while (flag) {
                // synchronized 保证内存可见性
                // 这里打印与不打印最终结果会是什么样
                System.out.println("test");
                count.getAndIncrement();
            }
        }).start();

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        flag = false;

        System.out.println(count.get());
    }

    public static void fun01() {

    }
}
