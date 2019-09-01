package java高级知识.泛型.PECS.client;

import java高级知识.泛型.PECS.Animal;
import java高级知识.泛型.PECS.Dog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wxi.wang
 * <p>
 * 2019/9/1 18:35
 * Decription:
 *
 */
public class PETest {
    public static void producerExtends(List<? extends Animal> animals) {
        // 读取animals
        Animal a1 = animals.get(0);

        // 添加animal
        //animals.add(new Animal());     //编译错
        //animals.add(new Dog());        //编译错
        /**
         * 因为会违反类型安全(violate type safe)，animals在例子中是List<Dog>，
         * 但是也可以是 List<BigDog>, List<SmallDog>, List<Cat>。
         * 这个时候向容器写入对象的时候无法做类型安全检查（比如 List<Dog>是无法添加Animal类型），
         * 所以就禁止写入任何对象。
         *
         * 因为这个特性，所以说<? extnebds T>适合进行读取操作，不能写入，也就是PECS中的PE(Producer Extends)。
         */
    }


    public static void main(String[] args) {
        List<Dog> dogs = new ArrayList<Dog>();
        dogs.add(new Dog());
        producerExtends(dogs);
    }
}
