package 多线程.并发demo测试.线程安全问题测试.day01;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * <Description>
 *
 * @author wangxi
 */
@Slf4j
public class Demo02 {

    private Map<Integer, Integer> map = new HashMap<>();

    public void fun1() {
        synchronized (map) {
            try {
                log.info("fun1() get lock");
                Thread.sleep(7000);
                log.info("fun1() end");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 其他地方引用被synchronized修饰的变量与并发控制没有冲突
     */
    public void fun2() {
        map.put(2, 2);
        log.info("fun2() end");
    }

    public static void main(String[] args) {
        Demo02 d = new Demo02();
        new Thread(() -> {
            d.fun1();
        }).start();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(() -> {
            d.fun2();
        }).start();
        log.info("main Thread here");
    }
}

