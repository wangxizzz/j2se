package 多线程.Java并发编程.producer_consumer.阻塞队列实现;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author wangxi03 created on 2021/1/5 10:49 上午
 * @version v1.0
 */
public class MultiplePrint {

    private Lock lock = new ReentrantLock();
    private Condition conditionA = lock.newCondition();
    private Condition conditionB = lock.newCondition();
    private Condition conditionC = lock.newCondition();
    private int maxSize = 10;

    private BlockingQueue<Character> queue1 = new ArrayBlockingQueue<>(10);
    private BlockingQueue<Character> queue2 = new ArrayBlockingQueue<>(10);
    private BlockingQueue<Character> queue3 = new ArrayBlockingQueue<>(10);


    public static void main(String[] args) throws InterruptedException {
        MultiplePrint multiplePrint = new MultiplePrint();
        multiplePrint.print2();
    }

    public void print2() throws InterruptedException {
        new Thread(new Task('a', conditionA, conditionB)).start();

        Thread.sleep(100);
        new Thread(new Task('b', conditionB, conditionC)).start();

        Thread.sleep(100);
        new Thread(new Task('c', conditionC, conditionA)).start();
    }

    private void print1() {
        new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    queue1.put('a');
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    queue2.put('b');
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    queue3.put('c');
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    System.out.println(new StringBuilder().append(queue1.take())
                            .append(queue2.take()).append(queue3.take()));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }


    public class Task extends Thread {

        private char c;
        private Condition condition1;
        private Condition condition2;

        public Task(char c, Condition condition1, Condition condition2) {
            this.c = c;
            this.condition1 = condition1;
            this.condition2 = condition2;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                try {
                    lock.lock();
                    System.out.println(c);
                    try {
                        condition2.signal();
                        condition1.await();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } finally {
                    lock.unlock();
                }
            }
        }
    }
}
