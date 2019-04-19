package 多线程.Java并发编程.day6CountDownLatch和CyclicBarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @Author:王喜
 * @Description :集齐7颗龙珠召唤神龙  用CyclicBarrier实现
 * @Date: 2018/4/27 0027 21:16
 */
public class SummonDragonDemo2 {

    private static final int THREAD_COUNT_NUM = 7;

    public static void main(String[] args) {

        //设置第一个屏障点，等待召集齐7位法师
        CyclicBarrier callMasterBarrier = new CyclicBarrier(THREAD_COUNT_NUM, new Runnable() {
            @Override
            public void run() {
                System.out.println("7个法师召集完毕，同时出发，去往不同地方寻找龙珠！");
                summonDragon();
            }
        });
        //召集齐7位法师
        for (int i = 1; i <= THREAD_COUNT_NUM; i++) {
            int index = i;
            new Thread(() -> {
                try {
                    System.out.println("召集第" + index + "个法师");
                    /**
                     * 调用await()方法表示当前线程到达了屏障点
                     */
                    callMasterBarrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }).start();
        }
        System.out.println("阻塞的线程的数量" + callMasterBarrier.getNumberWaiting());
        System.out.println("不阻塞不线程！！！");
    }

    /**
     * 召唤神龙：1、收集龙珠；2、召唤神龙
     */
    private static void summonDragon() {
        //设置第二个屏障点，等待7位法师收集完7颗龙珠，召唤神龙
        CyclicBarrier summonDragonBarrier = new CyclicBarrier(THREAD_COUNT_NUM, new Runnable() {
            @Override
            public void run() {
                System.out.println("集齐七颗龙珠！召唤神龙！");
            }
        });
        //收集7颗龙珠
        for (int i = 1; i <= THREAD_COUNT_NUM; i++) {
            int index = i;
            new Thread(() -> {
                try {
                    System.out.println("第" + index + "颗龙珠已收集到！");
                    summonDragonBarrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
