package 常用的工具类.guava;

import com.google.common.collect.ComparisonChain;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author wxi.wang
 * 18-12-23
 * 测试ComparisonChain,,链式编程,以及Builder模式
 */
@Slf4j
public class Girl implements Comparable<Girl> {
    @Setter @Getter
    private String name;
    @Setter @Getter
    private Double height;
    @Setter @Getter
    private String face;

    @Override
    public int compareTo(Girl girl) {
        return ComparisonChain.start()
                .compare(name, girl.getName())
                .compare(height, girl.getHeight())
                .compare(face, girl.getFace())
                .result();
    }

    public void test() {
        // 两个对象的地址输出的相同
        System.out.println(this);
        System.out.println(Girl.this);
    }

    public static void main(String[] args) {
        new Girl().test();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
