package 多线程.Java并发编程.day04wait与notify;

/**
 * <Description>
 *    调用wait、notify先要获取锁
 *  https://www.jianshu.com/p/84ee31ed548f
 * @author wangxi
 */
public class ThreadA {
    public static void main(String[] args) {
        ThreadB b = new ThreadB();
        b.start();
        synchronized (b) {
            try {
                System.out.println("阻塞中....");
                b.wait();  // 唤醒时，从wait这里往下执行逻辑
                System.out.println("被唤醒...");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(b.total);
    }


}
class ThreadB extends Thread {
    public int total;
    @Override
    public void run() {
        synchronized (this) {
            for (int i = 0; i < 10000; i++) {
                total++;
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            notifyAll();
        }
    }
}

