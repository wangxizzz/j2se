package 多线程.Java并发编程.day5Lock锁与线程间通信;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
/**
 * @Author:王喜
 * @Description : 测试tryLock(),可中断方法
 * @Date: 2018/4/27 0027 18:57
 */

public class Lock其他API {
    /**
     * 假如线程A和线程B使用同一个锁LOCK，此时线程A首先获取到锁LOCK.lock()，并且始终持有不释放。
     * 如果此时B要去获取锁，有四种方式：注意，有四种不同的方式获取锁！！！

     1.LOCK.lock():此方式不能中断， 此方式会始终处于等待中，即使调用B.interrupt()也不能中断，
     除非线程A调用LOCK.unlock()释放锁。

     2.LOCK.lockInterruptibly():获取可中断锁 此方式会等待，但当调用B.interrupt()会被中断等待，
     并抛出InterruptedException异常，如果不调用调用B.interrupt()，就会与lock()一样始终处于等待中，直到线程A释放锁。

     3.LOCK.tryLock(): 该处不会等待，获取不到锁并直接返回false，去执行下面的逻辑。

     4.LOCK.tryLock(10, TimeUnit.SECONDS)：该处会在10秒时间内处于等待中，但当调用B.interrupt()
     会被中断等待，并抛出InterruptedException。10秒时间内如果线程A释放锁，会获取到锁并返回true，否则10秒过后会获取不到锁并返回false，去执行下面的逻辑。

     */
    static int count = 0;

    public static void runMethod(Lock lock) {
        /**
         * tryLock()，该处不会等待，获取不到锁并直接返回false，去执行下面的逻辑。
         * lock()方法表示未获得到锁，会一直去获取
         */
        boolean tryLock = lock.tryLock();
        System.out.println(Thread.currentThread().getName() + " "+ tryLock);
//        lock.lock();
        try {

//            System.out.println(Thread.currentThread().getName() + "  获得锁");
//            for (int i = 0; i < 300; i++) {
//                count++;
//            }

            if (tryLock) {
                System.out.println(Thread.currentThread().getName() + "  获得锁");
                for (int i = 0; i < 300; i++) {
                    count++;
                }
            }

        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            //如果不加上释放锁的语句时，那么只有一个线程进入代码块执行
            System.out.println(Thread.currentThread().getName() + "释放锁");
            lock.unlock();
        }


    }

    public static void main(String[] args) {
        Lock lock = new ReentrantLock();
        for (int i = 0; i < 30; i++) {
            new Thread(() -> runMethod(lock),"thread" + i).start();
        }

        try {
            Thread.sleep(2000);
            System.out.println(count);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
