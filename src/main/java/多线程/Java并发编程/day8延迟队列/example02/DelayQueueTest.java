package 多线程.Java并发编程.day8延迟队列.example02;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.DelayQueue;

/**
 * @author wangxi03 created on 2020/12/29 2:40 下午
 * @version v1.0
 *
 * 以上网的例子
 */
public class DelayQueueTest extends Thread {
    DelayQueue queue =  new DelayQueue();
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    public static void main(String[] args) {
        DelayQueueTest wangba = new DelayQueueTest();
        wangba.start();

        wangba.shangji("A", 5);
        wangba.shangji("B", 2);
        wangba.shangji("C", 4);
    }

    public void shangji(String name, int money) {
        WangMing wm = new WangMing(name, System.currentTimeMillis() + money * 1000L);
        queue.add(wm);
        System.out.println(name + "开始上网，时间：" + format.format(new Date()) +
                "，预计下机时间为：" + format.format(new Date(wm.getEndTime())));
    }

    public void xiaji(WangMing wm) {
        System.out.println(wm.getName() + "下机，时间：" + format.format(new Date(wm.getEndTime())));
    }

    public void run() {
        while (true) {
            try {
                WangMing wm = (WangMing) queue.take();
                xiaji(wm);
            } catch (InterruptedException e) {
            }
        }
    }
}
