package java高级知识.javassist;

/**
 * 测试字节码类
 */
public class Person {

    public String hello(String name) {
        System.out.println("test " + name);
        return name;
    }

}
