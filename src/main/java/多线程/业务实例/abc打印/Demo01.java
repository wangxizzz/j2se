package 多线程.业务实例.abc打印;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <Description>
 *   参考网址：https://www.cnblogs.com/xiaoxi/p/8035725.html
 *   分析：
 *
 *     仔细想想本问题，既然同一时刻只能有一个线程打印字符，那我们为什么不使用一个同步锁ReentrantLock？
 *     线程之间的唤醒操作可以通过Condition实现，且Condition可以有多个，
 *     每个condition.await阻塞只能通过该condition的signal/signalall来唤醒！
 *     这是synchronized关键字所达不到的，
 *     那我们就可以给每个打印线程一个自身的condition和下一个线程的condition，每次打印字符后，
 *     调用下一个线程的condition.signal来唤醒下一个线程，
 *     然后自身再通过自己的condition.await来释放锁并等待唤醒。
 * @author wangxi
 */
public class Demo01 implements Runnable{
    private final int count = 3;
    private Lock lock;
    private Condition thisCondition;
    private Condition nextCondition;
    private char c;
    public Demo01(Lock lock, Condition thisCondition, Condition nextCondition, char c) {
        this.lock = lock;
        this.thisCondition = thisCondition;
        this.nextCondition = nextCondition;
        this.c = c;
    }

    @Override
    public void run() {
        // 获取打印锁 进入临界区
        lock.lock();
        try {
            for (int i = 0; i < count; i++) {
                System.out.print(c);
                // 使用nextCondition唤醒下一个线程
                // 因为只有一个线程在等待，所以signal或者signalAll都可以
                nextCondition.signal();
                // 如果没有判断，那么三个线程都会阻塞。程序不会结束
                if (i < count - 1) {
                    try {
                        // 本线程让出锁并等待唤醒
                        thisCondition.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Lock lock = new ReentrantLock();
        Condition conditionA = lock.newCondition();
        Condition conditionB = lock.newCondition();
        Condition conditionC = lock.newCondition();
        Thread threadA = new Thread(new Demo01(lock, conditionA, conditionB, 'a'));
        Thread threadB = new Thread(new Demo01(lock, conditionB, conditionC, 'b'));
        Thread threadC = new Thread(new Demo01(lock, conditionC, conditionA, 'c'));

        threadA.start();
        Thread.sleep(100);
        threadB.start();
        Thread.sleep(100);
        threadC.start();

    }
}

