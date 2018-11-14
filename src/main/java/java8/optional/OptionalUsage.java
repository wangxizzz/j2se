package java8.optional;

import java.util.Optional;

/**
 * Optional常见的API测试
 */
public class OptionalUsage {

    public static void main(String[] args) {
        // 通过empty()构造Optional
        Optional<Insurance> insuranceOptional = Optional.empty();
        // 会出现NoSuchElementException
        insuranceOptional.get();

        // 通过of构造出构造Optional
        Optional<Insurance> insuranceOptional1 = Optional.of(new Insurance());
        // 返回Insurance实例
        insuranceOptional1.get();

        // 如果ofNullable参数为null,返回empty(),否则返回of()方法、
        Optional<Insurance> objectOptional = Optional.ofNullable(null);

        // 如果不为空就返回实例，如果为null就会执行括号里的代码Insurance::new,里面需要传入一个Supplier接口
        objectOptional.orElseGet(Insurance::new);

        // 参数是一个reference，不是Supplier
        objectOptional.orElse(new Insurance());

        objectOptional.orElseThrow(RuntimeException::new);

        objectOptional.orElseThrow(() -> new RuntimeException("Not have reference"));

        // 把getName != null的过滤出来送入下一个流中
        Insurance insurance = insuranceOptional1.filter(i -> i.getName() != null).get();
        System.out.println(insurance);

        Optional<String> nameOptional = insuranceOptional1.map(i -> i.getName());

        System.out.println(nameOptional.orElse("empty Value"));

        System.out.println(nameOptional.isPresent());

        nameOptional.ifPresent(System.out::println);


        System.out.println(getInsuranceName(null));
        System.out.println(getInsuranceNameByOptional(null));
    }


    private static String getInsuranceName(Insurance insurance) {
        if (null == insurance)
            return "unknown";
        return insurance.getName();
    }

    private static String getInsuranceNameByOptional(Insurance insurance) {
        return Optional.ofNullable(insurance).map(Insurance::getName).orElse("unknown");
    }
}