package 多线程.java并发编程之美.chapter1;

/**
 * @author wxi.wang
 * 19-1-22
 */
public class MyThread extends Thread{
    @Override
    public void run() {
        System.out.println("..................");
    }

    public static void main(String[] args) {
        new MyThread().start();
    }
}
