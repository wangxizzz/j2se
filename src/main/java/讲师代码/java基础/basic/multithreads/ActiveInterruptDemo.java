/**
 * 
 */
package 讲师代码.java基础.basic.multithreads;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 *
 * @author
 *
 * 2018-06-24
 */
public class ActiveInterruptDemo {

    private static Logger logger = LoggerFactory .getLogger(ActiveInterruptDemo.class);

    public ActiveInterruptDemo() {}

    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            long start = System.currentTimeMillis();
            while (System.currentTimeMillis() - start < 2000L) {
                logger.info("I am busy.......");
            }
            logger.info("I am done with my job, nobody interruped me");
            logger.info("My state: {}", Thread.currentThread().isInterrupted());
        }, "interupted-thread");  
        thread.start();
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            logger.warn("I was interruped before I interrup others...");
        }
        // 在主线程中中断thread线程。
        thread.interrupt();
    }

}
