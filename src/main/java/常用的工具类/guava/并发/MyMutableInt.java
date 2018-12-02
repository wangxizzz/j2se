package 常用的工具类.guava.并发;

import org.apache.commons.lang3.mutable.MutableInt;

/**
 * @author wxi.wang
 * 18-12-1　扩展一个方法，线程不安全，作为演示每个future值的变化
 */
public class MyMutableInt extends MutableInt {
    MyMutableInt(final int value) {
        super(value);
    }
    public int get() {
        return super.getValue();
    }
    int myIncrement() {
        super.increment();
        return super.getValue();
    }
}
