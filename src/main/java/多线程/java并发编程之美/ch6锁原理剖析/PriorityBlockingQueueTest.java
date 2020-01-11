package 多线程.java并发编程之美.ch6锁原理剖析;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Random;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * @Author wangxi
 * @Time 2020/1/11 22:25
 *
 * 封装任务 利用PriorityBlockingQueue 进行按照优先级执行
 */
public class PriorityBlockingQueueTest {

    public static void main(String[] args) {
        PriorityBlockingQueue<Task> queue = new PriorityBlockingQueue<>();
        for (int i = 0; i < 10; i++) {
            queue.offer(new Task("taskName" + i, new Random().nextInt(10)));
        }

        while (!queue.isEmpty()) {
            System.out.println(queue.poll());
        }
    }



    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Task implements Comparable<Task> {

        private String taskName;
        private int priority;

        @Override
        public int compareTo(Task o) {
            // 小到大排序
            return Integer.compare(this.priority, o.getPriority());
        }
    }
}
