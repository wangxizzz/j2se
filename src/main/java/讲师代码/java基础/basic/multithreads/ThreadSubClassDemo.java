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
public class ThreadSubClassDemo {

    private static Logger logger = LoggerFactory .getLogger(ThreadSubClassDemo.class);
    
    public ThreadSubClassDemo() {}

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            ThreadWithJob threadWithJob = new ThreadWithJob("thread-" + i);
            threadWithJob.start();
        }
    }

    static class ThreadWithJob extends Thread {
        ThreadWithJob(String name) {
            super(name);
        }
        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                logger.info("{}th number", i);
            }
        }
    }
    
}
