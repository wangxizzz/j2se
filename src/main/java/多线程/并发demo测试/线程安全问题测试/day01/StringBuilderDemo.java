package 多线程.并发demo测试.线程安全问题测试.day01;

/**
 * @Author wangxi
 * @Time 2019/11/21 23:47
 * 会出现count值小于1w，并且可能会出现ArrayIndexOutOfBoundsException
 */
public class StringBuilderDemo {
    public static void main(String[] args) throws InterruptedException {
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    buffer.append("a");
                }
            }).start();
        }
        System.out.println(buffer.length());
        Thread.sleep(1000);
    }
}
