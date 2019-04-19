package 多线程.Java并发编程.day1线程的创建和synchronized;

/**
 * @Author:王喜
 * @Description :创建线程第一种方式
 * @Date: 2018/4/26 0026 17:09
 */
public class MyThread extends Thread{

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() +"线程启动成功！");
    }


    /**
     * 线程可以按照顺序start，但是会并发执行（不再按照start的前后顺序影响）
     * 下面的结果可以是：
     *          thread2线程启动成功！
                thread1线程启动成功！
     * @param args
     */
    public static void main(String[] args) {
        Thread thread1 = new MyThread();
        thread1.setName("thread");
//        Thread thread2 = new MyThread("thread2");

        MyThread myThread = new MyThread();
        Thread thread01 = new Thread(myThread, "thread01");
        Thread thread02 = new Thread(myThread, "thread02");
        thread01.start();
        thread02.start();
    }
}
