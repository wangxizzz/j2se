package 多线程.Java并发编程.day6CountDownLatch和CyclicBarrier;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * @Author:王喜
 * @Description :集齐7颗龙珠召唤神龙  用CountDownLatch实现
 * @Date: 2018/4/27 0027 19:58
 */
public class SummonDragonDemo {

    private final static int MAX_COUNT_NUM = 7;   //需要集齐龙珠的个数
    private static CountDownLatch countDownLatch = new CountDownLatch(MAX_COUNT_NUM);
    public static void main(String[] args) throws InterruptedException {
        //分别用7个线程去找龙珠
        for (int i = 1; i <= 7; i++) {
            int index = i;
            new Thread(() -> {
                try {
                    System.out.println("第" + index + "颗龙珠已收集到！");
                    //模拟收集第i个龙珠,随机模拟不同的寻找时间
                    Thread.sleep(new Random().nextInt(3000));
//                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //每收集到一颗龙珠,需要等待的颗数减1
                countDownLatch.countDown();
            }).start();
        }
        /**
         * for循环会把7个线程先创建好，然后7个线程并发的去执行自己的逻辑。
         * 下面这句话是主线程在执行
         */
        System.out.println("7个线程创建完毕");

        //等待检查，即上述7个线程执行完毕之后，执行await后边的代码（在这里是主线程运行），否则会一直阻塞
        countDownLatch.await();
        System.out.println("集齐七颗龙珠！召唤神龙！");
    }
}

