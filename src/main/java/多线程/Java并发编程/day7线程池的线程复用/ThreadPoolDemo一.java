package 多线程.Java并发编程.day7线程池的线程复用;

import java.util.concurrent.Executors;

/**
 * @Author:王喜
 * @Description : 不符合规范的创建线程池的方式
 * @Date: 2018/4/27 0027 21:40
 */
public class ThreadPoolDemo一 {
    /**
     *线程池不允许使用Executors去创建，而是通过ThreadPoolExecutor的方式，
     这样的处理方式让写的同学更加明确线程池的运行规则，规避资源耗尽的风险。
     说明：Executors各个方法的弊端：

     1）newFixedThreadPool和newSingleThreadExecutor:
       主要问题是堆积的请求处理队列可能会耗费非常大的内存，甚至OOM。
     2）newCachedThreadPool和newScheduledThreadPool:
       主要问题是线程数最大数是Integer.MAX_VALUE，可能会创建数量非常多的线程，甚至OOM。
     *
     */
    public static void main(String[] args) {
        Executors.newFixedThreadPool(5);
        Executors.newScheduledThreadPool(5);
        Executors.newSingleThreadExecutor();
        Executors.newCachedThreadPool();
    }
}
