package java8.接口default方法测试;

public class MeaningOfThis {
    public final int value = 4;
    public void doIt() {
        int value = 6;
        Runnable r = new Runnable() {   // 采用匿名内部类,new的是接口
            final int value = 5;
            @Override
            public void run() {
                int value = 10;
                // 下面的this代表的是r
                System.out.println(this.value);
            }
        };
        r.run();
    }

    public static void main(String[] args) {
        MeaningOfThis m = new MeaningOfThis();
        m.doIt();
    }
}
