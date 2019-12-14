package 多线程.java并发编程之美.ch1并发基础.线程中断;

/**
 * @Author wangxi
 * @Time 2019/12/14 22:06
 *
 * interrupted()与isInterrupted()区别
 */
public class IsInterrupted {
    public static void main(String[] args) {
        Thread threadOne = new Thread(() -> {
            for (;;) {
                // 在这里可以调用interrupted()，重置threadOne线程的中断标志
            }
        });
        threadOne.start();
        // 中断线程，设置标志位
        threadOne.interrupt();
        // 获取中断标志
        System.out.println(threadOne.isInterrupted());

        // 获取中断标志位，并重置(注意：重置的是当前线程的标志位)所以此时仍然是main线程
        System.out.println(threadOne.interrupted());

        // 获取中断标志位，并重置(注意：重置的是当前线程的标志位)
        System.out.println(Thread.interrupted());
        // 获取中断标志
        System.out.println(threadOne.isInterrupted());

        System.out.println("main thread is over!");
    }
}
