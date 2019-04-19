package 多线程.Java并发编程.day2可重入锁和synchronized;

/**
 * @Author:王喜
 * @Description :双重加锁机制，并且防止指令重排序优化
 * @Date: 2018/4/26 0026 19:59
 */
public class DubbleSingleton {

    private static volatile DubbleSingleton instance = null;

    private DubbleSingleton(){}

    private static DubbleSingleton getInstance() {
        if(instance == null){  //判断实例是否已经被其他线程创建了，如果没有则创建
            try {
                //模拟初始化对象的准备时间...
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //类上加锁，表示当前对象不可以在其他线程的时候创建
            synchronized (DubbleSingleton.class) {
                //如果不加这一层判断的话，这样的话每一个线程会得到一个实例
                //而不是所有的线程的到的是一个实例
                if(instance == null){ //从第一次判断是否为null到加锁之间的时间内判断实例是否已经被创建
                    instance = new DubbleSingleton();
                }
            }
        }
        return instance;
    }
}
