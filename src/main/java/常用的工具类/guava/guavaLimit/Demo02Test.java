package 常用的工具类.guava.guavaLimit;

/**
 * @Author wangxi
 * @Time 2019/10/7 22:05
 */
public class Demo02Test {

    public static void fun01() throws InterruptedException {
        int i = 0;
        while (i < 100) {
            i++;
            boolean result = Demo02.enter("fun01", "wangxi");
            if (!result) {
                System.out.println(i);
                Thread.sleep(1000);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        fun01();
    }
}
