package 多线程.java并发编程之美.chapter1.监视器锁;

import lombok.extern.slf4j.Slf4j;

/**
 * @author wxi.wang
 * 19-1-24
 * 当前线程调用共享变量的wait()阻塞挂起之后,如果被其他线程打断,会抛出InterruptedException
 */
@Slf4j
public class WaitDemo02 {
    private static Object resourceA = new Object();

    public static void main(String[] args) {
        Thread threadA = new Thread(() -> {
            synchronized (resourceA) {
                log.info("threadA get resourceA lock");
                try {
                    log.info("thread A release resourceA and wait");
                    resourceA.wait();
                } catch (InterruptedException e) {
                    log.error("threadA is interrupted", e);
                }
            }
        });

        threadA.start();   // 调用start()并不是立马就执行run(),而是处于 除cpu资源没获取,其他都就绪的状态

        // 休眠1s
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("begin to interrupt threadA");
        threadA.interrupt();
        log.info("end interrupt thread A");
    }
}
