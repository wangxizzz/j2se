package 多线程.java并发编程之美.ch1并发基础.threadlocal;

/**
 * @ClassName InheritableThreadLocalDemo
 * @Description InheritableThreadLocal提供了一个特性，让子线程可以访问在父线程中设置的本地
 * 变量
 * @Author wxi.wang
 * @Date 2019/2/20 16:25
 */
public class InheritableThreadLocalDemo {   // 60
    /**
     * 原理介绍：当父
     * 线程创建子线程时，ThreadLocalMap构造函数会把父线程中inheritableThreadLocals 变量里面的本地变量
     * 复制一份保存到子线程的inheritableThreadLocals 变量里面。
     *
     * 应用场景：
     * 1.子线程需要使用存放在threadlocal 变量中的用户登录信息
     * 2.一些中间件需要把统一的id 追踪的整个调用链路记录下来
     */
    private static InheritableThreadLocal<String> threadLocal = new InheritableThreadLocal<>();
    public static void main(String[] args) {
        threadLocal.set("aaa");

        new Thread(() -> {
            System.out.println("子线程中：" + threadLocal.get());  // get() 获取的是当前线程
        }).start();

        System.out.println("main线程中：" + threadLocal.get());
    }
}