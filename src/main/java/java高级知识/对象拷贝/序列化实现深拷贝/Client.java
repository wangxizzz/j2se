package java高级知识.对象拷贝.序列化实现深拷贝;

import lombok.extern.slf4j.Slf4j;

import java.io.*;

/**
 * <Description>
 *  浅拷贝测试
 * @author wangxi
 */
@Slf4j
public class Client {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        FatherClass fatherA = new FatherClass();
        fatherA.name = "张三";
        fatherA.age = 30;
        fatherA.childClass = new ChildClass();
        fatherA.childClass.name = "小张三";
        fatherA.childClass.age = 3;

        FatherClass fatherB = (FatherClass)fatherA.deepClone();

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

