package java高级知识.对象拷贝.深拷贝;

import lombok.extern.slf4j.Slf4j;

/**
 * <Description>
 *  浅拷贝测试
 * @author wangxi
 */
@Slf4j
public class Client {
    public static void main(String[] args) {
        FatherClass fatherA = new FatherClass();
        fatherA.name = "张三";
        fatherA.age = 30;
        fatherA.childClass = new ChildClass();
        fatherA.childClass.name = "小张三";
        fatherA.childClass.age = 3;

        /**
         * 可以看到，使用 clone() 方法，从 == 和 hashCode 的不同可以看出，
         *  clone() 方法实则是真的创建了一个新的对象fatherB
         */
        FatherClass fatherB = (FatherClass) fatherA.clone();
        log.info("fatherA == fatherB? {}", fatherA == fatherB);
        log.info("fatherA hash = {}", fatherA.hashCode());
        log.info("fatherB hash = {}", fatherB.hashCode());
        log.info("fatherA name = {}", fatherA.name);
        log.info("fatherB name = {}", fatherB.name);


        System.out.println("===========证明深拷贝===========");
        System.out.println("A.child == B.child : " + (fatherA.childClass == fatherB.childClass));
        System.out.println("A.child hash = " + fatherA.childClass.hashCode());
        System.out.println("B.child hash = " + fatherB.childClass.hashCode());

    }
}

