package 多线程.java并发编程之美.chapter1.监视器锁;

import lombok.extern.slf4j.Slf4j;

/**
 * @author wxi.wang
 * 19-1-24
 * wait只释放当前共享变量上面的锁
 */
@Slf4j
public class WaitDemo01 {
    /**
     * 下面是一个死锁的例子  死锁的必要条件之一: 持有并等待
     */
    private static Object resourceA = new Object();
    private static Object resourceB = new Object();

    public static void main(String[] args) {
        // 定义线程A
        new Thread(() -> {
            synchronized (resourceA) {
                log.info("thread A get resourceA lock");
                synchronized (resourceB) {
                    log.info("thread A get resourceB lock");
                    log.info("thread A release resourceA lock");
                    try {
                        resourceA.wait();       // resourceB一直没释放
                    } catch (InterruptedException e) {
                        log.error("thread A is interrupted", e);
                    }
                }
            }
        }).start();

        // 定义线程B
        new Thread(() -> {
            // 休眠2s
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                log.error("thread B is interrupted ");
            }
            synchronized (resourceA) {
                log.info("thread B get resourceA lock");

                log.info("thread B try to get resourceB lock");
                synchronized (resourceB) {
                    log.info("thread B get resourceB lock");
                }
            }
        }).start();
    }
    /**
     * 死锁的4个必要条件：
     *      1.互斥条件：一个资源每次只能被一个资源使用；
     *      2.占有且等待：一个进程因请求资源而阻塞时，对已获取的资源保持不放；
     *      3.不可强行占有：进程以获取的资源，在未使用完之前，不能被强行剥夺，除非自己释放；
     *      4.循环等待条件：若干进程之间形成一种头尾相接的循环等待资源关系。
     */
}
