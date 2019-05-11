package java高级知识.对象拷贝.深拷贝;

/**
 * <Description>
 *
 * @author wangxi
 */
public class ChildClass implements Cloneable{
    public String name;
    public int age;

    @Override
    protected Object clone() {
        try {
            // ChildClass是浅拷贝
            return super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }
}

