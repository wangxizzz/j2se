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
 * 2018-06-24
 */
public class IsInterruptedDemo {

    private static Logger logger = LoggerFactory .getLogger(IsInterruptedDemo.class);

    public IsInterruptedDemo() {}

    public static void main(String[] args) throws InterruptedException {
        long begin = System.currentTimeMillis();
        Thread thread = new Thread(() -> {
            long start = System.currentTimeMillis();
            while (System.currentTimeMillis() - start < 1000L) {
                if (Thread.currentThread().isInterrupted()) {
                    logger.info("Somebody interrupt me, ignore it....");
                } else {
                    logger.info("I am busy.......");
                }                
            }
            logger.info("I am done with my job");
        }, "interupted-thread");  
        thread.start();
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            logger.warn("I was interruped before I interrup others...");
        }
        thread.interrupt();
        thread.join();
        logger.info("相差的时间是{}", System.currentTimeMillis() - begin);
    }
}
