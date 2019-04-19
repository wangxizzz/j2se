package 多线程.Java并发编程.day2可重入锁和synchronized;

/**
 * @Author:王喜
 * @Description :普通加锁的单例模式
 * @Date: 2018/4/26 0026 19:59
 */
public class Singleton {

    private static Singleton instance = null;   //懒汉模式

//    private static Singleton instance = new Singleton();  //饿汉模式

    private Singleton(){}

    public static synchronized Singleton getInstance(){
        if (null == instance) {
            instance = new Singleton();
        }
        return instance;
    }
}
