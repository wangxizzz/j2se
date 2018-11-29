/**
 * 
 */
package 讲师代码.java基础.basic.multithreads;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ThreadLocalRandom;

/**
 * This example is wrong!!!
 * <p>
 * <b>Why?
 *
 * @author Guanying Piao
 *
 * 2018-06-23
 */
public class ThreadInterruptDemo {

    private static Logger logger = LoggerFactory .getLogger(ThreadSubClassDemo.class);

    public ThreadInterruptDemo() {}

    public static void main(String[] args) {

        Thread mainThread = Thread.currentThread();
        int threadCount = 5;
        Thread[] threads = new Thread[threadCount];
        for (int i = 0; i < threadCount; i++) {
            int threadIndex = i;
            Thread thread = new Thread(() -> { 
                try {
                    Thread.sleep(ThreadLocalRandom.current().nextLong(1000L));
                } catch (InterruptedException e) {
                    logger.warn("Unexpected interruption");
                }
                for (int j = 0; j < 3; j++) {
                    logger.info("{}th thread {}th number", threadIndex, j);
                }   
                mainThread.interrupt();
            });
            threads[i] = thread;
            thread.start();
        }
        while (threadCount-- > 0) {
            try {
                Thread.sleep(Long.MAX_VALUE);                
            } catch (InterruptedException e) {
                logger.info("One thread finished");
            }
        }
        
    }

}