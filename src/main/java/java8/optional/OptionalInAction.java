package java8.optional;

import java.util.Optional;

/**
 * 一个小例子demo
 *
 * 利用Optional和stream来避免嵌套关系比较多时的NullPointerException问题。
 * 可以对比参照类NullPointerException
 */
public class OptionalInAction {

    public static void main(String[] args) {
        Optional.ofNullable(getInsuranceNameByOptional(null)).ifPresent(System.out::println);
    }

    /**
     * 通过Person得到Car,通过Car 得到Insurance,通过Insurance得到Insurance的名字。
     * 中间的步骤很容易出现NullPointerException，因此通过Optional避免
     * @param person
     * @return
     */
    private static String getInsuranceNameByOptional(Person person) {
        /**
         * map()返回一个新的Optional,而flatMap是把第二个参数的Optional重用后返回。
         * 此时getCar()本身就返回一个Optional
         */
        return Optional.ofNullable(person)
                .flatMap(p -> p.getCar()).flatMap(car -> car.getInsurance())
                .map(Insurance::getName).orElse("Unknown");
    }
}
