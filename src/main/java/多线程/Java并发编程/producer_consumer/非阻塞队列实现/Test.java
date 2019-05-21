package 多线程.Java并发编程.producer_consumer.非阻塞队列实现;

import java.util.PriorityQueue;

/**
 * @Author:王喜
 * @Description :使用Object.wait()和Object.notify()、非阻塞队列实现生产者-消费者模式：
 * @Date: 2018/4/20 0020 13:20
 */
public class Test {

    private int queueSize = 10;
    private PriorityQueue<Integer> queue = new PriorityQueue<>(queueSize);

    public static void main(String[] args) {
        Test test = new Test();
        Producer producer = test.new Producer();
        Consumer consumer = test.new Consumer();
        producer.start();
        consumer.start();
    }

    class Producer extends Thread{
        @Override
        public void run() {
            produce();
        }

        private void produce() {
            while (true) {
                synchronized (queue) {
                    while (queue.size() == queueSize) {
                        try {
                            System.out.println("队列已满");
                            queue.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            queue.notify();
                        }
                    }
                    queue.offer(1);
                    //插入元素成功，唤醒取元素线程
                    queue.notify();
                    System.out.println("向队列取中插入一个元素，队列剩余空间："+
                            (queueSize-queue.size()));
                }
            }
        }
    }
    class Consumer extends Thread{
        @Override
        public void run() {
            consume();
        }
        private void consume() {
            while (true) {
                synchronized (queue) {
                    // while循环防止虚假唤醒，不能用if
                    while (queue.size() <= 0) {
                        try {
                            System.out.println("队列空，等待数据");
                            // 在调用notify(),阻塞线程会在这里苏醒，然后继续while循环，
                            queue.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            //出现中断异常，会唤醒等待的线程
                            queue.notify();
                        }
                    }
                    queue.poll();
                    //如果去元素成功，唤醒插入元素线程
                    queue.notify();
                    System.out.println("从队列取走一个元素，队列剩余"+
                            queue.size()+"个元素");
                }
            }
        }
    }
}
