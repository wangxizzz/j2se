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
     * 下面是一个死锁的例子  死锁的必要条件: 持有并等待
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
}
