package 多线程.join用法测试;

/**
 * 在很多情况下，主线程创建并启动子线程，如果子线程中要进行大量的耗时运算，
 * 主线程往往将早于子线程结束之前结束。如果主线程想等待子线程完成之后再结束，就要用到join()方法。
 */
public class Run {
    public static void main(String[] args) {
        try {
            Thread t1 = new Thread(() -> {
                System.out.println(Thread.currentThread().getName());
                try {
                    System.out.println("t1线程在进行大规模运算中....");
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            t1.start();
            //线程t1正常执行，而当前的main线程进行无限期的阻塞
            //等待线程t1销毁后再继续执行main线程后面的代码
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("t1线程执行结束....");
    }
    /**
     * 方法join的作用是使所属的线程对象t1正常执行run()方法中的任务，
     * 而使当前线程main进行无限期的阻塞，等待线程t1销毁后（执行完成）再继续执行线程main后面的代码。
     */
}
