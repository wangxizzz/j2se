package 多线程.java并发编程之美.chapter1.threadlocal;

/**
 * @ClassName ThreadLocalTest
 * @Description  测试ThreadLocal的不可继承性
 * @Author wxi.wang
 * @Date 2019/2/20 16:20
 */
public class ThreadLocalTest {
    private static ThreadLocal<String> threadLocal = new ThreadLocal<>();
    public static void main(String[] args) {
        threadLocal.set("aaa");

        new Thread(() -> {
            System.out.println("子线程中：" + threadLocal.get());  // get() 获取的是当前线程
        }).start();

        System.out.println("main线程中：" + threadLocal.get());
    }
}