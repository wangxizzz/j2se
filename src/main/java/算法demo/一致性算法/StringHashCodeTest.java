package 算法demo.一致性算法;

/**
 * @author wxi.wang
 * 18-12-10
 */
public class StringHashCodeTest {
    public static void main(String[] args) {
        System.out.println("192.168.0.0:111的哈希值：" + "192.168.0.0:1111".hashCode());
        System.out.println("192.168.0.1:111的哈希值：" + "192.168.0.1:1111".hashCode());
        System.out.println("192.168.0.2:111的哈希值：" + "192.168.0.2:1111".hashCode());
        System.out.println("192.168.0.3:111的哈希值：" + "192.168.0.3:1111".hashCode());
        System.out.println("192.168.0.4:111的哈希值：" + "192.168.0.4:1111".hashCode());
    }
}
