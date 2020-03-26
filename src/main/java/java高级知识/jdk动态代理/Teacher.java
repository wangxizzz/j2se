package java高级知识.jdk动态代理;

/**
 * @Author wangxi
 * @Time 2020/3/26 17:32
 */
public class Teacher implements People {
    @Override
    public void work() {
        System.out.println("老师教书育人");
    }
}
