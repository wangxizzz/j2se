/**
 * 
 */
package 讲师代码.java基础.basic.concurrent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 
 *
 * @author Guanying Piao
 *
 * 2018-06-23
 */
public class AtomicLongDemo {

    private static Logger logger = LoggerFactory .getLogger(AtomicLongDemo.class);

    private static AtomicLong count = new AtomicLong(0);    
    
    public AtomicLongDemo() {}

    public static void main(String[] args) {
        int threadCount = 5;
        Thread[] threads = new Thread[threadCount];
        for (int i = 0; i < threadCount; i++) {
            int threadIndex = i;
            Thread thread = new Thread(() -> {                
                for (int j = 0; j < 1000; j++) {                    
//                    logger.trace("{}th thread {}th number count:{}", threadIndex, j, count.incrementAndGet());
                }               
            });
            threads[i] = thread;
            thread.start();
        }
        try {
            for (Thread thread : threads) {
                thread.join();
            }
            logger.info("sub thread's jobs are done, count:{}", count.get());
        } catch (InterruptedException e) {
            logger.error("Got interrupted while waiting for other threads to stop", e);
        }
    }
}
