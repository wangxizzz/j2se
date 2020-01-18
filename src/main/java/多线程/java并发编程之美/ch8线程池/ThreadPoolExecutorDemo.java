package 多线程.java并发编程之美.ch8线程池;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author wangxi
 * @Time 2020/1/12 10:43
 *
 * 测试线程池
 */
public class ThreadPoolExecutorDemo {
    public static void main(String[] args) {
        System.out.println(Integer.toBinaryString(~1));
        System.out.println(1 > 2 || 1 == 1);
        System.out.println(1 > 2 || 1 == 10);

        ExecutorService service = Executors.newFixedThreadPool(2);

    }

    @Test
    public void test() {
        int i = 0;
        int j = 0;
        retry :
        for ( ;i < 3; i++) {
            System.out.println("---");
            for (; j < 3; j++) {
//                if (i == 1) {
                        // 直接跳出二层for循环
//                    break  retry;
//                }
                if (j == 0) {
                    // 相当于break，跳出内层循环，i++，重新执行外层循环
                    continue retry;
                }
                System.out.println(i + ":" + j);
            }
        }
        System.out.println(i);
        System.out.println(j);
    }
}
