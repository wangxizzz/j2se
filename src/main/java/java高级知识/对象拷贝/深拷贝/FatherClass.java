package java高级知识.对象拷贝.深拷贝;

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
            // 深拷贝  fatherClass为克隆后的对象
            FatherClass fatherClass = (FatherClass) super.clone();
            // 同时把FatherClass的对象属性，也进行clone一份。相对于ChildClass是浅拷贝，相对于FatherClass是深拷贝
            fatherClass.childClass = (ChildClass) this.childClass.clone();
            return fatherClass;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }
}

