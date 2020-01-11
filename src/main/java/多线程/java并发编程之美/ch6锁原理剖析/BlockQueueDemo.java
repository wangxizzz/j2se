package 多线程.java并发编程之美.ch6锁原理剖析;

import java.util.Comparator;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * @Author wangxi
 * @Time 2020/1/8 20:53
 * 测试JDK的阻塞队列
 */
public class BlockQueueDemo {

    public static void main(String[] args) {
        LinkedBlockingQueue<String> linkedBlockingQueue = new LinkedBlockingQueue<>();

        // 定义排序器
        Comparator<Integer> comparator = Comparator.naturalOrder();
        comparator = comparator.reversed();
        // 堆排序
        PriorityBlockingQueue<Integer> priorityBlockingQueue = new PriorityBlockingQueue<>(3, comparator);
        priorityBlockingQueue.offer(3);
        priorityBlockingQueue.offer(1);
        priorityBlockingQueue.offer(2);
        priorityBlockingQueue.offer(4);
        priorityBlockingQueue.offer(4);
        for (int i = 0; i < 5; i++) {
            System.out.println(priorityBlockingQueue.poll());
        }
//        System.out.println(priorityBlockingQueue.toString());

        System.out.println(Integer.MAX_VALUE + 100);
    }
}
