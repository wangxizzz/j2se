package 多线程.java并发编程之美.ch6锁原理剖析;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author wangxi
 * @Time 2020/1/5 16:56
 * 测试condition相关方法
 */
public class ConditionDemo {
    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        try {
            lock.lock();
            condition.await();
            // 演示作用
            condition.signal();
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            lock.unlock();
        }
    }
}
