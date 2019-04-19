package 多线程.Java并发编程;


import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author:王喜
 * @Description : 每次访问得到不同的结果--->线程不安全
 * @Date: 2018/4/27 0027 16:50
 */
public class 并发访问出错一 {
    static int count = 0;
    private static void runMethod() {
//        lock.lock();
        for (int i = 0; i < 23000; i++) {
            count++;
        }
    }

    public static void main(String[] args) {
//        Lock lock = new ReentrantLock();
        //启动100个线程
        for (int i = 0; i < 100; i++) {
            new Thread(() -> runMethod(),"thread0" + i).start();
        }
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(count);
    }

    /**
     * 解决上述的问题：
     *  1.在runMethod()加上synchronized关键字
     *  2.加上lock锁，把lock当做参数传入到runMethod()
     */
}
