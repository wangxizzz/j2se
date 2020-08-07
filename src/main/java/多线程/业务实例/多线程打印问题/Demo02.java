package 多线程.业务实例.多线程打印问题;

/**
 *
 * @author wangxi created on 2020/8/7 10:03 AM
 * @version v1.0
 *
 * 启动两个线程, 一个输出 1,3,5,7…99, 另一个输出 2,4,6,8…100 最后 STDOUT 中按序输出 1,2,3,4,5…100
 */
public class Demo02 {

    private static final Object lock = new Object();


    public void print1() throws InterruptedException {
        for (int i = 1; i <= 100; i = i + 2) {
            synchronized (lock) {
                System.out.println(i);
                // 唤醒阻塞在lock上的线程
                lock.notify();
                lock.wait();
            }

        }
    }

    public void print2() throws InterruptedException {
        for (int i = 2; i <= 100; i = i + 2) {
            synchronized (lock) {
                System.out.println(i);
                lock.notify();
                lock.wait();
            }
        }
    }

    public static void main(String[] args) throws Exception {

        Demo02 demo02 = new Demo02();

        new Thread(() -> {
            try {
                demo02.print1();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        Thread.sleep(20);

        new Thread(() -> {
            try {
                demo02.print2();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
