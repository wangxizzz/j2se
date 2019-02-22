package 多线程.java并发编程之美.chapter1;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName InterruptDemo
 * @Description 测试中断，更多中断内容，参照书
 * @Author wxi.wang
 * @Date 2019/2/19 14:23
 */
@Slf4j
public class InterruptDemo {
    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
           for (;;) {

           }
        });
        // thread.setDaemon(true);   // 默认是非守护线程
        thread.start();
        thread.interrupt();
        log.info("thread status is {}", thread.isInterrupted());
        log.info("status is {}", Thread.interrupted());   // 判断当前线程是否被中断
    }
}