package java高级知识.对象拷贝.浅拷贝;

/**
 * <Description>
 *
 * @author wangxi
 */
public class FatherClass implements Cloneable{
    public String name;
    public int age;
    public ChildClass childClass;

    @Override
    protected Object clone() {
        try {
            // 浅拷贝
            return super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }
}

