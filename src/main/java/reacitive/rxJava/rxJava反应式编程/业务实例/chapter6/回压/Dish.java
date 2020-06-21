package reacitive.rxJava.rxJava反应式编程.业务实例.chapter6.回压;

/**
 * @author wangxi created on 2020/6/20 21:58
 * @version v1.0
 */
public class Dish {

    private byte[] oneKb = new byte[1024];

    private int id;

    Dish(int id) {
        this.id = id;
        System.out.println("create: " + id);
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }
}
