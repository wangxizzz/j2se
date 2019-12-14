package 多线程.java并发编程之美.ch1并发基础.等待通知机制;

import lombok.extern.slf4j.Slf4j;

/**
 * @Author wangxi
 * @Time 2019/12/14 17:52
 * wait()只释放当前共享变量持有的锁，对其他共享变量无作用
 * 永久阻塞。不满足死锁的循环等待条件，所以非死锁
 */
@Slf4j
public class BlockDemo01 {
    public static Object resourceA = new Object();
    public static Object resourceB = new Object();
    public void fun01() {
        synchronized (resourceA) {
            System.out.println(Thread.currentThread().getName());
            log.info("ThreadA get resourceA");
            log.info("ThreadA try to get resourceB");
            synchronized (resourceB) {
                log.info("ThreadA get resourceB");
                log.info("ThreadA release resourceA");
                try {
                    resourceA.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public void fun02() {
        synchronized (resourceA) {
            log.info("ThreadB get resourceA");
            log.info("ThreadB try to get resourceB");
            synchronized (resourceB) {
                log.info("ThreadB get resourceB");
            }
        }
    }

    public static void main(String[] args) {
        BlockDemo01 demo02 = new BlockDemo01();
        new Thread(() -> {
            demo02.fun01();
        }).start();

        new Thread(() -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            demo02.fun02();
        }).start();
    }
}
