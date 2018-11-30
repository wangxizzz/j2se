package work.lambda;

import org.apache.commons.lang3.mutable.MutableInt;
import org.junit.Test;

/**
 *
 *
 * @author
 *
 * 2018-06-30
 */
public class Demo01 {
    /**
     * http://commons.apache.org/proper/commons-lang/apidocs/org/apache/commons/lang3/mutable/MutableInt.html
     * MutableInt的简单介绍
     */
    @Test
    public void test01() throws InterruptedException {
        // 可以把值封装到对象中，然后可以在lambda中使用。
        MutableInt i = new MutableInt(0);
        MyInt myInt = new MyInt(1);
        new Thread(() -> {
            i.increment();
            i.add(100);
            myInt.increment();
            myInt.add(100);
            System.out.println(i);
            System.out.println(i.getValue().intValue());
            System.out.println(myInt.get());
        }).start();
        Thread.currentThread().join();
    }
    class MyInt {
        private int value;
        private MyInt(int value){
            this.value = value;
        }
        public void add(int opt) {
            value += opt;
        }
        private void increment() {
            value++;
        }
        public int get() {
            return value;
        }
    }

    @Test
    public void test02() {

    }
}
