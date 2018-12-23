package 常用的工具类.guava.集合;

import com.google.common.collect.ComparisonChain;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

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
}
