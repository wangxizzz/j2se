package 多线程.Java并发编程.day1线程的创建和synchronized;

import java.util.concurrent.Executor;

/**
 * @Author:王喜
 * @Description : 测试对象锁  也可见SynchronizedDemo01类 详细的可以参照博客
 * @Date: 2018/4/26 0026 19:03
 */
public class MultiThread {
    private int num = 200;

    public synchronized void printNum(String threadName, String tag) {
        if (tag.equals("a")) {
            num = num - 100;
            System.out.println(threadName + " tag a,set num over!");
        } else {
            num = num - 400;
            System.out.println(threadName + " tag b,set num over!");
        }
        System.out.println(threadName + " tag " + tag + ", num = " + num);
    }

    public static void main(String[] args) {
        MultiThread multiThread1 = new MultiThread();
        MultiThread multiThread2 = new MultiThread();

        new Thread(() -> {
            multiThread1.printNum("thread1", "a");
        }).start();

        new Thread(() -> {
            //这是共享同一把锁
//            multiThread1.printNum("thread2", "b");
            //这是两把不同的锁，他们的执行互不干扰
            multiThread2.printNum("thread2", "b");
        }).start();
    }
}
