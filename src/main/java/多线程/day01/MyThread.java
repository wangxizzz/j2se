package 多线程.day01;

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
