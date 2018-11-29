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
public class InterruptedDemo {

    private static Logger logger = LoggerFactory .getLogger(InterruptedDemo.class);

    public InterruptedDemo() {}

    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            long start = System.currentTimeMillis();
            boolean interrupted = false;
            while (System.currentTimeMillis() - start < 10000L) {
                if (Thread.interrupted()) {
                    logger.info("Somebody interrupt me, ignore it....");
                    interrupted = true;
                } else if (interrupted) {
                    logger.info("Somebody interrupted me once, but Thread.interrupted test false");
                    break;
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
    }

}
