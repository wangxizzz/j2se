package 多线程.java并发编程之美.ch1并发基础.守护线程;

/**
 * @Author wangxi
 * @Time 2019/12/14 22:23
 * 启动main函数，相当于启动一个JVM进程
 * 同时main线程随之启动，也是用户线程，同时也会有jvm守护线程(gc线程)启动
 * 守护线程会随着最后一个user线程的退出而退出
 */
public class DaemonThread01 {
    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            for (;;) {

            }
        });
        thread.setDaemon(true);
        thread.start();

        System.out.println("main thread over");
    }
}
