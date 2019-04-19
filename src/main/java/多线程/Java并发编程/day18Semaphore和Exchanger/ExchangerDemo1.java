package 多线程.Java并发编程.day18Semaphore和Exchanger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Exchanger;

/**
 * @Author:王喜
 * @Description :
 * @Date: 2018/5/2 0002 19:46
 */
public class ExchangerDemo1 {

    private final static Exchanger<List<Integer>> EXCHANGER = new Exchanger<>();

    public static void main(String[] args) {
        new Thread( () -> {
            List<Integer> list = new ArrayList<>(2);
            list.add(1);
            list.add(2);
            try {
                //线程会在这里阻塞。
                list = EXCHANGER.exchange(list);
            }catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("thread1" + list);
        }).start();

        new Thread( () -> {
            List<Integer> list = new ArrayList<>(2);
            list.add(3);
            list.add(4);
            try {
                list = EXCHANGER.exchange(list);
            }catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("thread2" + list);
        }).start();
    }
}
