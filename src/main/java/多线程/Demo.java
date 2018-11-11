package 多线程;

import java.util.concurrent.Callable;

public class Demo {
    public static void main(String[] args) {
        new Thread(() -> {
            System.out.println();
        });

        new Thread(() -> System.out.println());
    }
    public Callable<String> feetch() {
        return () -> "ss";  // 需要返回Callable
    }
}
