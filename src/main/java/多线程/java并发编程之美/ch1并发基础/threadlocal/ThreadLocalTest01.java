package 多线程.java并发编程之美.ch1并发基础.threadlocal;

/**
 * @Author wangxi
 * @Time 2019/12/15 10:34
 *
 */
public class ThreadLocalTest01 {

    public static ThreadLocal<String> threadLocal01 = new ThreadLocal<>();
    public static ThreadLocal<String> threadLocal02 = new ThreadLocal<>();

    public static void print() {
        System.out.println(Thread.currentThread().getName() + "--" + threadLocal01.get());
        threadLocal01.remove();
    }

    public static void main(String[] args) {
        /**
         * 一个线程可以关联多个ThreadLocal
         * 所以Thread类的ThreadLocal.ThreadLocalMap threadLocals = null
         * 需要设计成Map类型
         */
        new Thread(() -> {
            threadLocal01.set("aaaa");
            // 值会覆盖，ThreadLocalMap类似于HashMap
            threadLocal01.set("aaaa33");
            threadLocal02.set("bbbb");
            print();
        }).start();

        new Thread(() -> {
            threadLocal01.set("nnnn");
            threadLocal02.set("vvvvv");
            print();
        }).start();
    }
}
