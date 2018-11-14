package java8.optional;

import org.junit.Test;

import java.util.Optional;

/**
 * 小测试
 */
public class Demo {
    @Test
    public void test01() {
        Optional<Insurance> objectOptional = Optional.ofNullable(null);
        // 会抛异常
//        System.out.println(objectOptional.get());
        objectOptional.ifPresent(insurance -> System.out.println(objectOptional.get()));
    }
}
