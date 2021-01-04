package 多线程.Java并发编程.producer_consumer.阻塞队列实现;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author wangxi03 created on 2021/1/4 6:39 下午
 * @version v1.0
 */
public class ConsumerProducer01 {
    // 利用lock + condition

    private Lock lock = new ReentrantLock();
    private Condition notEmpty = lock.newCondition();
    private Condition notFull = lock.newCondition();
    private List<Integer> list = new LinkedList<>();

    public static void main(String[] args) {

    }
}
