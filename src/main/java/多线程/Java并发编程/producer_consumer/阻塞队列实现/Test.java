package 多线程.Java并发编程.producer_consumer.阻塞队列实现;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * @Author:王喜
 * @Description :使用阻塞队列实现的生产者-消费者模式
 * @Date: 2018/4/20 0020 14:12
 */
public class Test {

    private int queueSize = 10;
    private ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(queueSize);

    public static void main(String[] args) {
        Test test = new Test();
        Producer producer = test.new Producer();
        Consumer consumer = test.new Consumer();
        producer.start();
        consumer.start();
    }
    class Consumer extends Thread {

        @Override
        public void run() {
            consume();
        }

        public void consume() {
            while(true) {
                try {
                    queue.take();
                    System.out.println("从队列取走一个元素，队列剩余"+
                            queue.size()+"个元素");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    class Producer extends Thread {

        @Override
        public void run() {
            produce();
        }

        private void produce() {
            while(true){
                try {
                    queue.put(1);
                    System.out.println("向队列取中插入一个元素，队列剩余空间："+
                            (queueSize-queue.size()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
