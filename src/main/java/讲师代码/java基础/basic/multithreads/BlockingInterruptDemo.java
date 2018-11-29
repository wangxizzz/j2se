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
public class BlockingInterruptDemo {

    private static Logger logger = LoggerFactory .getLogger(BlockingInterruptDemo.class);

    public BlockingInterruptDemo() {}

    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(10000L);
            } catch (InterruptedException e) {
                logger.warn("I was interrupted, current state: {}",
                        Thread.currentThread().isInterrupted());
            }
        }, "interupted-thread");
        thread.start();
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            logger.warn("I was interruped before I interrup others...");
        }
        thread.interrupt();
    }
}
