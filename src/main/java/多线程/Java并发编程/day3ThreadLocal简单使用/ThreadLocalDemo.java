package 多线程.Java并发编程.day3ThreadLocal简单使用;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author:王喜
 * @Description :
 * @Date: 2018/4/26 0026 20:14
 */
public class ThreadLocalDemo {
    public static ThreadLocal<List<String>> threadLocal = new ThreadLocal<>();

    public void setThreadLocal(List<String> values) {
        threadLocal.set(values);
    }

    public void getThreadLocal() {
        System.out.println(Thread.currentThread().getName());
        threadLocal.get().forEach(name -> System.out.println(name));
    }

    public static void main(String[] args) throws InterruptedException {

        final ThreadLocalDemo threadLocal = new ThreadLocalDemo();
        new Thread(() -> {
            List<String> params = new ArrayList<>(3);
            params.add("张三");
            params.add("李四");
            params.add("王五");
            threadLocal.setThreadLocal(params);
            threadLocal.getThreadLocal();
        }).start();

        new Thread(() -> {
            try {
                Thread.sleep(1000);
                List<String> params = new ArrayList<>(2);
                params.add("Chinese");
                params.add("English");
                threadLocal.setThreadLocal(params);
                threadLocal.getThreadLocal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
