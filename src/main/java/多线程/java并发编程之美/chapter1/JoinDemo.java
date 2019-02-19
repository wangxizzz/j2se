package 多线程.java并发编程之美.chapter1;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName JoinDemo
 * @Description
 * @Author wxi.wang
 * @Date 2019/2/19 11:35
 */
@Slf4j
public class JoinDemo {
    public static void main(String[] args) {
        Thread threadOne = new Thread(() -> {
            for (;;) {

            }
        });

        Thread main = Thread.currentThread();
        Thread threadTwo = new Thread(() -> {
            try {
                Thread.sleep(1000);  // 调用sleep方法，让出CPU资源处于阻塞状态，不释放以获取的资源。时间到后处于就绪状态。
            } catch (InterruptedException e) {
                System.out.println("threadTwo.." + e);
            }
            main.interrupt();  // 在其他线程调用main中断方法
        });

        threadOne.start();
        threadTwo.start();
        try {
            threadOne.join();  // 等待threadOne返回
        } catch (InterruptedException e) {
            log.error("main.....", e);
        }
    }
}