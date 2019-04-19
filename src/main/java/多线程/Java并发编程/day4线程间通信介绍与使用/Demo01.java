package 多线程.Java并发编程.day4线程间通信介绍与使用;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author:王喜
 * @Description :使用Object类中的wait(),notify()方法实现通信
 *                 注意：只有多个线程共享一段代码时，才具有线程间通信的价值，否则都是独立的，同步通信没影响
 * @Date: 2018/4/26 0026 20:52
 */
public class Demo01 {

    //准备一个list集合
    static class MyList {
        List<String> list = new ArrayList<>();

        public void add() {
            list.add("我是元素");
        }
        public int size() {
            return list.size();
        }
    }
    //为了测试，都为static类
    static class ThreadA extends Thread {
        private MyList list;

        public ThreadA(MyList list) {
            this.list = list;
        }
        @Override
        public void run() {
            //调用wait()、sleep()时，会有异常
            try {
                synchronized (list) {
                    if (list.size() != 5) {
                        System.out.println("threadA等待中" + System.currentTimeMillis());
                        //wait()方法是Object类的方法，随便一个对象都可以调用
                        list.wait();  //等待并释放自己持有的锁
                        System.out.println("threadA等待结束" + System.currentTimeMillis());
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class ThreadB extends Thread {
       public MyList list;
        public ThreadB(MyList list) {
            this.list = list;
        }
        @Override
        public void run() {
            //一定要是同一把锁(原因见类头)
            synchronized (list) {
                for (int i = 0; i < 10; i++) {
                    list.add();
                    if (list.size() == 5) {
                        list.notify();  //notify方法并不释放当前持有锁
                        System.out.println("已发出通知");
                    }
                    System.out.println("添加了" + (i + 1) + "个元素!");
                }
            }
        }
    }

    public static void main(String[] args) {
        MyList list = new MyList();
        try {
//            Object lock = new Object();
            ThreadA a = new ThreadA(list);
            a.start();
            Thread.sleep(50);
            ThreadB b = new ThreadB(list);
            b.start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
