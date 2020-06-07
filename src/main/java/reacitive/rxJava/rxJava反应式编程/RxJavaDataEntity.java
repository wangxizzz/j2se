package reacitive.rxJava.rxJava反应式编程;

/**
 * @author wangxi created on 2020/6/7 00:03
 * @version v1.0
 */
public class RxJavaDataEntity {
    public static RxJavaDataEntity load(int id) {
        System.out.println("当期线程: " + Thread.currentThread().getName());
        int i = 1/0;
        return new RxJavaDataEntity();
    }
}
