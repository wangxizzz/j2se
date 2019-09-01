package java高级知识.泛型.PECS.client;

import java高级知识.泛型.PECS.Animal;
import java高级知识.泛型.PECS.BigDog;
import java高级知识.泛型.PECS.Dog;
import java高级知识.泛型.PECS.SmallDog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wxi.wang
 * <p>
 * 2019/9/1 18:35
 * Decription:
 */
public class CSTest {
    public static void consumerSuper(List<? super SmallDog> dogs) {
        dogs.add(new SmallDog());

//        dogs.add(new Dog());         //编译错
        //SmallDog dog1 = dogs.get(0);       //编译错
        /**
         * 为什么add(new Dog())编译错？
         * 因为这里的<? super SmallDog>限制了传入的list中的对象必须是SmallDog或其父类（具体类型未知，可能是Animal，
         * 也可能是Dog），所以添加的对象必须是SmallDog或其子类，这样才能保证类型安全。
         *
         * 为什么SmallDog dog1 = dogs.get(0)编译错？因为这个list中的对象可能是SmallDog的任意祖先，
         * 是无法保证一定可以转化成SmallDog类型 （父类转化为子类必会失败 ClassCastException）。
         *
         * 所以说<? super T>适合进行写操作，不能读取，也就是PECS中的CS(Consumer Super)。
         */

    }

    public static void main(String[] args) {

        List<Animal> animals = new ArrayList<>();
        animals.add(new Animal());
        animals.add(new BigDog());
        consumerSuper(animals);
    }
}
