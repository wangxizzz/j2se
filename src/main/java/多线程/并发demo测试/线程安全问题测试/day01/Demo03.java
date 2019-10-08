package 多线程.并发demo测试.线程安全问题测试.day01;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * <Description>
 * 测试 ConcurrentHashMap 也需要加synchronized关键字
 * https://blog.csdn.net/u014082714/article/details/52200759
 * @author wangxi
 */
public class Demo03 {

    public void fun() throws InterruptedException {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        ExecutorService pool = Executors.newCachedThreadPool();
        // 起了8个线程
        for (int i = 0; i < 8; i++) {
            pool.execute(new MyTask(map));
        }
        pool.shutdown();
        pool.awaitTermination(1, TimeUnit.DAYS);

        System.out.println(map.get(MyTask.KEY));
    }

    public static void main(String[] args) throws InterruptedException {
        Demo03 demo03 = new Demo03();
        for (int i = 0; i < 10; i++) {
            demo03.fun();
        }
    }

}
// 一个文件可以有多个类，但是只能有一个public类
class MyTask implements Runnable{
    public static final String KEY = "key";

    private ConcurrentHashMap<String, Integer> map;

    public MyTask(ConcurrentHashMap<String, Integer> map) {
        this.map = map;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            this.addUnSafe();
        }
    }
    // 线程不安全
    // ConcurrentHashMap的线程安全指的是，它的每个方法单独调用（即原子操作）都是线程安全的
    private void addUnSafe() {
        if (!map.containsKey(KEY)) {
            map.put(KEY, 1);
        } else {
            // +1 非原子操作,包含了map.get(), +1, map.put()写会内存
            map.put(KEY, map.get(KEY) + 1);
        }
    }

    private void addSafe() {
        if (!map.containsKey(KEY)) {
            map.put(KEY, 1);
        } else {
            /**
             * 降低锁的粒度，或者可以直接在调用方加锁。
             * synchronized (map) {
             *     this.addUnSafe();
             * }
             * 此时可以直接使用HashMap,因为put()加锁了
              */
            synchronized (map) {
                map.put(KEY, map.get(KEY) + 1);
            }
        }
    }
}

