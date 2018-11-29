/**
 * 
 */
package 讲师代码.java基础.basic.multithreads;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 *
 * @author Guanying Piao
 *
 * 2018-06-23
 */
public class ThreadJoinDemo {

    private static Logger logger = LoggerFactory .getLogger(ThreadSubClassDemo.class);

    public ThreadJoinDemo() {}

    public static void main(String[] args) {
        int threadCount = 5;
        Thread[] threads = new Thread[threadCount];
        for (int i = 0; i < threadCount; i++) {
            int threadIndex = i;
            Thread thread = new Thread(() -> {                
                for (int j = 0; j < 10; j++) {
                    logger.info("{}th thread {}th number", threadIndex, j);
                }               
            });
            threads[i] = thread;
            thread.start();
        }
        try {
            for (Thread thread : threads) {
                thread.join();
            }
            logger.info("sub thread's jobs are done");
        } catch (InterruptedException e) {
            logger.error("Got interrupted while waiting for other threads to stop", e);
        }
    }

}