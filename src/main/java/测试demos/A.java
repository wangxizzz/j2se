package 测试demos;

/**
 * <Description>
 *
 * @author wangxi
 */
public class A {

    public void a() {
        System.out.println(this);
    }

    @Override
    public String toString() {
        return "A";
    }

    class SubA {
        public void a1() {
            System.out.println(this);
        }

        @Override
        public String toString() {
            return "SubA";
        }
    }

    public static void main(String[] args) {
        SubA subA = new A().new SubA();
        subA.a1();
    }
}

