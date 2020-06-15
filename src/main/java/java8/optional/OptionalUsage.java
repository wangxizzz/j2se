package java8.optional;

import org.junit.Test;

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

        // 通过of构造出构造Optional，参数必须为非null
        Optional<Insurance> insuranceOptional1 = Optional.of(new Insurance());
        // 返回Insurance实例
        insuranceOptional1.get();

        // ofNullable的应用场景是无法确定参数是否为null,但又不想判断是否为null。
        Optional<Insurance> objectOptional = Optional.ofNullable(null);

        // 如果不为空就返回实例，如果为null就会执行括号里的代码Insurance::new,里面需要传入一个Supplier函数式接口
        objectOptional.orElseGet(Insurance::new);

        // 参数是一个reference，可以传一个对象或String.不是Supplier
        objectOptional.orElse(new Insurance());

        objectOptional.orElseThrow(RuntimeException::new);

        objectOptional.orElseThrow(() -> new RuntimeException("Not have reference"));

        // 把getName != null的过滤出来送入下一个流中。注意：此时getName是为空的，因为没有赋值。
        Insurance insurance = insuranceOptional1.filter(i -> i.getName() != null).get();
        System.out.println(insurance);

        Optional<String> nameOptional = insuranceOptional1.map(i -> i.getName());

        // 如果nameOptional为null,不会抛出异常，会输出括号里的内容。
        System.out.println(nameOptional.orElse("empty Value"));

        // 测试容器里面的值存不存在。不建议使用此方法
        System.out.println(nameOptional.isPresent());

        // 如果nameOptional存在就会执行Consumer接口
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

    @Test
	public void test01() {
		System.out.println(getInsuranceName(null));
	}
}