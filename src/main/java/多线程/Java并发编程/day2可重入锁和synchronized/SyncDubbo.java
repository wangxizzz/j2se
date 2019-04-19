package 多线程.Java并发编程.day2可重入锁和synchronized;

/**
 * @Author:王喜
 * @Description :Synchronized锁重入
 *          可重入锁的概念就是：自己可以获取自己的内部锁
 * @Date: 2018/4/26 0026 19:30
 */
public class SyncDubbo {

    /**
     * 线程去请求别的对象持有的锁时，该线程会阻塞。
     *  但是若请求自己持有的锁时，如果是可重入锁，则会请求成果，否则失败
     */

    public synchronized void method1() {
        System.out.println("method1");
        method2();
    }
    public synchronized void method2() {
        System.out.println("method2");
        method3();
    }
    public synchronized void method3() {
        System.out.println("method3");
    }
    public static void main(String[] args) {
        SyncDubbo syncDubbo = new SyncDubbo();
        new Thread(() -> {
            //代码逻辑
            syncDubbo.method1();
        }).start();
    }
}
