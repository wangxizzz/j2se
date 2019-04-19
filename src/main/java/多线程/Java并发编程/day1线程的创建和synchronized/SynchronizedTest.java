package 多线程.Java并发编程.day1线程的创建和synchronized;

/**
 * @Author: wangxi
 * @Description : 测试synchronized的作用范围
 * @Date: 2018/9/7 0007 10:08
 */
public class SynchronizedTest {
    // 锁住类所有对象。类的不同对象需要排队等候
    public synchronized static void f1() {
        System.out.println("进入f1()...");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("退出f1()..");
    }
    // 锁住调用方法的对象
    public synchronized void f2() {
        System.out.println("进入f2()...");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("退出f2()..");
    }

    public static void main(String[] args) {
        SynchronizedTest s1 = new SynchronizedTest();
        SynchronizedTest s2 = new SynchronizedTest();
//        s1.f1();
//        s2.f1();

        new Thread(() -> {
            s1.f2();
        }).start();

        new Thread(() -> {
            s2.f2();
        }).start();
    }

}
