package 多线程.java并发编程之美.ch1并发基础.线程中断;

/**
 * @Author wangxi
 * @Time 2019/12/14 22:03
 * 线程中断标志
 */
public class InterruptDemo01 {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                System.out.println("hello");
            }
            System.out.println("被中断、、、");
        });
        thread.start();
        Thread.sleep(500);

        thread.interrupt();
    }
}
