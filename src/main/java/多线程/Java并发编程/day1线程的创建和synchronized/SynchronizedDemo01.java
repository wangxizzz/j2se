package 多线程.Java并发编程.day1线程的创建和synchronized;

/**
 * @Author:王喜
 * @Description :
 * @Date: 2018/4/26 0026 18:28
 */
public class SynchronizedDemo01 extends Thread{
    private int count = 5;

    @Override
    public void run() {
        count--;
        System.out.println(Thread.currentThread().getName() + "    " + count);
    }

    public static void main(String[] args) {
        /**
         * 关键字synchronized取得的锁都是对象锁，而不是把一段代码或方法当做锁，
         * 所以下面注释代码中哪个线程先执行synchronized 关键字的方法，
         * 那个线程就持有该方法所属对象的锁，五个对象，线程获得的就是五个不同对象的不同的锁，
         *
         * 他们的执行互不影响的。
         */
        SynchronizedDemo01 thread01 = new SynchronizedDemo01();
        SynchronizedDemo01 thread02 = new SynchronizedDemo01();
        SynchronizedDemo01 thread03 = new SynchronizedDemo01();
        SynchronizedDemo01 thread04 = new SynchronizedDemo01();
        SynchronizedDemo01 thread05 = new SynchronizedDemo01();
        thread01.start();
        thread02.start();
        thread03.start();
        thread04.start();
        thread05.start();

        /**
         * 不加synchronized修饰run()时，会出现下面这种情况
         *   thread3    2
             thread1    2
             thread2    2
             thread4    1
             thread5    0
            使用下面代码5个线程拥有同一把对象锁myThread，数据之间会有影响的
         */
//        SynchronizedDemo01 myThread = new SynchronizedDemo01();
//        Thread thread1 = new Thread(myThread, "thread1");
//        Thread thread2 = new Thread(myThread, "thread2");
//        Thread thread3 = new Thread(myThread, "thread3");
//        Thread thread4 = new Thread(myThread, "thread4");
//        Thread thread5 = new Thread(myThread, "thread5");
//        thread1.start();
//        thread2.start();
//        thread3.start();
//        thread4.start();
//        thread5.start();
    }
}
