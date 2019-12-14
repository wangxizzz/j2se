package 多线程.java并发编程之美.ch1并发基础.等待通知机制;

import lombok.extern.slf4j.Slf4j;

/**
 * @Author wangxi
 * @Time 2019/12/14 20:26
 * 死锁的例子。满足死锁的4个必要条件
 *
 * 下面是一个死锁的例子  死锁的必要条件:
 * （1）互斥条件。线程A获取到锁，B就无法获取锁了
 * （2）持有并等待。
 * （3）不可剥夺条件。线程A获取到锁，除非A自己释放或者退出synchronized代码区，否则不可剥夺
 * （4）循环等待
 *
 */
@Slf4j
public class DeadLock01 {

    private static Object resourceA = new Object();
    private static Object resourceB = new Object();

    public void fun01() {
        synchronized (resourceA) {
            try {
                log.info("threadA get resourceA");
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (resourceB) {

            }
        }
    }

    public void fun02() {
        synchronized (resourceB) {
            log.info("threadB get resourceB");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (resourceA) {

            }
        }
    }

    public static void main(String[] args) {
        DeadLock01 deadLock01 = new DeadLock01();
        new Thread(() -> {
            deadLock01.fun01();
        }).start();

        new Thread(() -> {
            deadLock01.fun02();
        }).start();
    }
}
