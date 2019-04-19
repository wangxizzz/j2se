package 多线程.Java并发编程.day1线程的创建和synchronized;

/**
 * @Author:王喜
 * @Description :
 * @Date: 2018/4/26 0026 17:20
 */
public class MyRunnable implements Runnable{

    @Override
    public void run() {
        String name = Thread.currentThread().getName();
        System.out.println(name + "启动");
        for (int i = 0; i < 5; i++) {
            System.out.println(i);
            try {
                //毫秒为单位
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        MyRunnable myRunnable = new MyRunnable();
        Thread thread01 = new Thread(myRunnable, "t1");
        Thread thread02 = new Thread(myRunnable, "t2");
        thread01.start();
        thread02.start();
    }
}
