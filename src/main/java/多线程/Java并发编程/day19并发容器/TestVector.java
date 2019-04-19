package 多线程.Java并发编程.day19并发容器;

import java.util.Vector;

/**
 * @Author: wangxi
 * @Description :   可见JVM虚拟机书P388页
 * @Date: 2018/6/1 0001 23:05
 */
public class TestVector {
    private static Vector<Integer> vector = new Vector<>();

    public static void unsafe() {
        while (true) {
            for (int i = 0; i < 1000; i++) {
                vector.add(i);
            }

            new Thread(() -> {
                for (int i = 0; i < vector.size(); i++) {
                    vector.remove(i);
                    try {
                        Thread.sleep(100);   //vector是一块共享数据，虽然remove()同步，但是任然会有问题。
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

            new Thread(() -> {
                for (int i = 0; i < vector.size(); i++) {
                    System.out.println(vector.get(i));
                }
            }).start();

            if (Thread.activeCount() > 200) {
                break;
            }
        }
    }

    public static void safe() {
        while (true) {
            for (int i = 0; i < 100; i++) {
                vector.add(i);
            }

            new Thread(() -> {
                synchronized (vector) {
                    for (int i = 0; i < vector.size(); i++) {
                        vector.remove(i);
                        try {
                            Thread.sleep(100);   //vector是一块共享数据，虽然remove()同步，但是任然会有问题。
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();

            new Thread(() -> {
                synchronized (vector) {
                    for (int i = 0; i < vector.size(); i++) {
                        System.out.println(vector.get(i));
                    }
                }
            }).start();

            if (Thread.activeCount() > 100) {
                break;
            }
        }
    }
    public static void main(String[] args) {
       unsafe(); //会出现java.lang.ArrayIndexOutOfBoundsException: Array index out of range: 991

       safe();  //不会产生异常
    }
}
