package 多线程.Java并发编程.day16等待通知模式接口Condition;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author:王喜
 * @Description :
 * @Date: 2018/5/1 0001 15:21
 */
public class MyQueue {
    /**
     * Condition接口也提供了类似object的监视器方法，与Lock配合使用也可以实现等待/通知模式
     *
     * 和synchronized一样，在调用wait和notify等方法之前都必须要先获取锁，
     * 同样使用Condition对象的await和signal方法的时候也是要先获取到锁！
     */
    //1、需要一个承装元素的集合
    private final LinkedList<Object> list = new LinkedList<>();
    //2、需要一个计数器
    private final AtomicInteger count = new AtomicInteger(0);
    //3、需要指定上限和下限
    private final int maxSize = 5;
    private final int minSize = 0;

    //5、初始化锁对象
    private final Lock lock = new ReentrantLock();
    private Condition notEmpty = lock.newCondition();
    private Condition notFull = lock.newCondition();

    /**
     * put方法
     */
    public void put(Object obj) {
        lock.lock();
        //达到最大无法添加，进入等到
        while (count.get() == maxSize) {
            try {
                notFull.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        list.add(obj); //加入元素
        count.getAndIncrement(); //计数器增加
        System.out.println(" 元素 " + obj + " 被添加 ");
        notEmpty.signal(); //通知另外一个阻塞的线程方法
        lock.unlock();
    }

    /**
     * get方法
     */
    public Object get() {
        lock.lock();
        Object temp;
        //达到最小，没有元素无法消费，进入等到
        while (count.get() == minSize) {
            try {
                notEmpty.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        count.getAndDecrement();
        temp = list.removeFirst();
        System.out.println(" 元素 " + temp + " 被消费 ");
        notFull.signal();
        lock.unlock();
        return temp;
    }

    private int size() {
        return count.get();
    }

    public static void main(String[] args) throws Exception {

        final MyQueue myQueue = new MyQueue();
        initMyQueue(myQueue);

        Thread t1 = new Thread(() -> {
            myQueue.put("h");
            myQueue.put("i");
        }, "t1");

        Thread t2 = new Thread(() -> {
            try {
                Thread.sleep(2000);
                myQueue.get();
                Thread.sleep(2000);
                myQueue.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t2");

        t1.start();
        Thread.sleep(1000);
        t2.start();

    }

    private static void initMyQueue(MyQueue myQueue) {
        myQueue.put("a");
        myQueue.put("b");
        myQueue.put("c");
        myQueue.put("d");
        myQueue.put("e");
        System.out.println("当前元素个数：" + myQueue.size());
    }
}
