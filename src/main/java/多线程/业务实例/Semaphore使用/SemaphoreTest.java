package 多线程.业务实例.Semaphore使用;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * <Description>
 *  参考网址：https://juejin.im/post/5b44116ce51d45191a0d2353
 *Semaphore（信号量）是用来控制同时访问特定资源的线程数量，
 * 通过协调各个线程以保证合理地使用公共资源。Semaphore可以用作流量控制，
 * 特别是公共资源有限的应用场景，比如数据库的连接。
 * @author wangxi
 */
public class SemaphoreTest {
    /* 读取文件数据的线程数量,并创建此容量的线程池 */
    private static final int THREAD_COUNT = 10;
    private static ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT, (r) -> {
        Thread t = new Thread(r);
        // 随着Main的消亡而结束，默认是非后台线程为false
        t.setDaemon(false);
        return t;
    });

    /* 创建Semaphore对象实例，构造函数的参数指定信号量的数目，为了方便说明问题，设为3 */
    private static Semaphore semaphore = new Semaphore(3);

    public static void main(String[] args){

        /* 创建线程读取数据，并尝试获取数据库连接，将数据存储到数据库中 */
        for(int i = 0;i < THREAD_COUNT;i++){
            final int index = i;

            Runnable task = new Runnable() {
                public void run() {
                    try {
                        /*从远程读数据*/
                        System.out.println("thread-"+ index + " is reading data from remote host");

                        /* 通过acquire 函数获取数据库连接，如果成功将数据存储到数据库 */
                        semaphore.acquire();
                        /*模拟存储数据耗时 这里是逻辑的处理代码*/
                        Thread.sleep(1000);
                        System.out.println("thread-"+ index + " is saving data....");
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }finally {

                        /* 最终使用release 函数释放信号量 */
                        semaphore.release();
                    }
                }
            };
            executorService.execute(task);
        }

        System.out.println("主线程执行完毕");
        executorService.shutdown();
        System.out.println("======");
    }
}

