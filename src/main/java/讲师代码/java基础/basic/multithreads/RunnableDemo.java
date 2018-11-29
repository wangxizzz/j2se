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
public class RunnableDemo {

    private static Logger logger = LoggerFactory .getLogger(ThreadSubClassDemo.class);

    public RunnableDemo() {}

    public static void main(String[] args) {
        
        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 10; i++) {
                        logger.info("{}th number", i);
                    }
                }                
            });
            thread.start();
        }        
        
    }

}
