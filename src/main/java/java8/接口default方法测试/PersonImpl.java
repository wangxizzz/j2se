package java8.接口default方法测试;

import org.junit.Test;

public class PersonImpl implements Person{
    @Override
    public int getAge() {
        return 100;
    }

    // 实现类和子类接口默认会拥有接口中的default方法。
    @Test
    public void testDefault() {
        System.out.println(getName());
    }
}
