package 多线程.业务实例.死锁的例子;

/**
 * <Description>
 *  synchronized死锁的例子
 *  lock的死锁：没有执行lock.unlock()方法。
 * @author wangxi
 */
public class DeadThread {
    private Object object1 = new Object();
    private Object object2 = new Object();

    public void fun1() {
        synchronized (object1) {
            System.out.println("fun1 获得object1");
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (object2) {
                System.out.println("fun1 获得object2");
            }
        }
    }

    public void fun2() {
        synchronized (object2) {
            System.out.println("fun2 获得object2");
            synchronized (object1) {
                System.out.println("fun2 获得object1");
            }
        }
    }

    public static void main(String[] args) {
        DeadThread deadThread = new DeadThread();
        new Thread(() -> {
            deadThread.fun1();
        }).start();

        new Thread(() -> {
            deadThread.fun2();
        }).start();
    }
}

