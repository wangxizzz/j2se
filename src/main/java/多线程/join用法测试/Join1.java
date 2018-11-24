package 多线程.join用法测试;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 第二种办法可以让main线程等待其他线程运行完的方式
 */
public class Join1 {
    public static void main(String[] args) throws InterruptedException {
        // 创建一个完成的标志变量，需要对所有线程可见
        AtomicBoolean finished = new AtomicBoolean(false);
        new Thread(() -> {
            try {
                Thread.sleep(2000);
                finished.set(true);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("aaaaaaaaaaa");
        }).start();
        while (!finished.get()) {
            Thread.sleep(1);
        }
        System.out.println("bbbbbbbbbbbbb");
    }
}
