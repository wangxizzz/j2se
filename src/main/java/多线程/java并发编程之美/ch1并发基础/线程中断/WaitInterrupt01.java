package 多线程.java并发编程之美.ch1并发基础.线程中断;

import lombok.extern.slf4j.Slf4j;

/**
 * @Author wangxi
 * @Time 2019/12/14 21:51
 * 中断wait()等待
 */
@Slf4j
public class WaitInterrupt01 {

    private static Object object = new Object();

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            synchronized (object) {
                try {
                    object.wait();
                } catch (InterruptedException e) {
                    log.error("thread is interrupted", e);
                }
            }
        });
        thread.start();
        Thread.sleep(500);
        // 在主线程中中断子线程thread
        thread.interrupt();
    }
}
