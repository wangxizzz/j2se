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
public class ThreadSleepDemo {

    private static Logger logger = LoggerFactory .getLogger(ThreadSubClassDemo.class);

    public ThreadSleepDemo() {}

    public static void main(String[] args) {
        
        for (int i = 0; i < 5; i++) {
            int threadIndex = i;
            Thread thread = new Thread(() -> {
                for (int j = 0; j < 10; j++) {
                    logger.info("{}th job {}th number", threadIndex, j);
                }             
            });
            thread.start();
        }
        
        try {
            Thread.sleep(2000L);
            logger.info("I THINK other threads has finished...");
        } catch (InterruptedException e) {
            logger.error("Got interrupted while waiting for other threads to stop", e);
        }
        
    }

}
