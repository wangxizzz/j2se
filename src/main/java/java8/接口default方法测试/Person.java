package java8.接口default方法测试;

/**
 * 一个人
 */
public interface Person {
    int getAge();  // 获得年龄

    default String getName() {
        return "wangxi";
    }
}
