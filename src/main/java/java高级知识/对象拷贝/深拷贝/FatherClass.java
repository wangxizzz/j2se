package java高级知识.对象拷贝.深拷贝;

import java.util.ArrayList;
import java.util.List;

/**
 * <Description>
 *
 * @author wangxi
 */
public class FatherClass implements Cloneable{
    public String name;   // 如果是两个不同的Bean，那么Bean属性的String,int类型就不重要了。
    public int age;
    public ChildClass childClass;
    // 注意list对象类型的深度拷贝的写法
    List<ChildClass> children;

    @Override
    protected Object clone() {
        try {
            // 深拷贝  fatherClass为克隆后的对象
            FatherClass clone = (FatherClass) super.clone();
            // 同时把FatherClass的对象属性，也进行clone一份。相对于ChildClass是浅拷贝，相对于FatherClass是深拷贝
            if (this.childClass != null) {
                clone.childClass = (ChildClass) this.childClass.clone();
            }
            if (children != null) {
                List<ChildClass> newChildren = new ArrayList<>();
                for (ChildClass child : this.children) {
                    newChildren.add((ChildClass)child.clone());
                }
                clone.children = newChildren;
            }
            return clone;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }
}

