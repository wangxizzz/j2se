package 多线程.Java并发编程.producer_consumer.非阻塞队列实现.唤醒问题;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author wangxi created on 2020/8/7 10:07 AM
 * @version v1.0
 *
 * 程序报错原因分析： 大量线程阻塞在 get方法的 wait方法(wait方法是释放掉当前锁)，
 * 当put方法的线程调用notifyAll时，唤醒了阻塞的线程，那么就会同时走出If循环，并发取remove。如果是while循环，
 * 那么线程是出不去的
 */
public class Demo01 {
    private final int MAX = 5;
    private final ArrayList<Integer> list = new ArrayList<>();
    synchronized void put(int v) throws InterruptedException {
        System.out.println("put-1" + Thread.currentThread().getName());
        if (list.size() == MAX) {
            wait();
            System.out.println("put-2" + Thread.currentThread().getName());
        }
        System.out.println("put-3" + Thread.currentThread().getName());
        list.add(v);
        notifyAll();
    }

    synchronized int get() throws InterruptedException {
        // line 0
        System.out.println("gett-1" + Thread.currentThread().getName());
        if (list.size() == 0) {  // line 1
            System.out.println("get-2.1" + Thread.currentThread().getName());
            wait();  // line2
            // 唤醒后 继续从wait下往下走，如果是if,那么此时就走出去了if判断
            System.out.println("get-2" + Thread.currentThread().getName());
            // line 3
        }
        System.out.println("get-3" + Thread.currentThread().getName());
        int v = list.remove(0);  // line 4
        notifyAll(); // line 5
        return v;
    }

    synchronized int size() {
        return list.size();
    }

    public static void main(String[] args) throws InterruptedException {
        final Demo01 buf = new Demo01();
        ExecutorService es = Executors.newFixedThreadPool(11);
        for (int i = 0; i < 1; i++)
            es.execute(new Runnable() {

                @Override
                public void run() {
                    while (true ) {
                        try {
                            buf.put(1);
                            Thread.sleep(2000);
                        }
                        catch (InterruptedException e) {
                            e.printStackTrace();
                            break;
                        }
                    }
                }
            });
        for (int i = 0; i < 3; i++) {
            es.execute(new Runnable() {

                @Override
                public void run() {
                    while (true ) {
                        try {
                            buf.get();
                            Thread.sleep(2000);
                        }
                        catch (InterruptedException e) {
                            e.printStackTrace();
                            break;
                        }
                    }
                }
            });
        }

        es.shutdown();
        es.awaitTermination(1, TimeUnit.DAYS);
    }
}
