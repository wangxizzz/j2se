package java高级知识.序列化和transient;

import lombok.Data;

import java.io.Serializable;

/**
 * @author wangxi created on 2020/6/1 22:35
 * @version v1.0
 */
@Data
public class SerializeUserEntity implements Serializable {
    private String name;
    private Integer age;
    private transient String sex;
    private static String signature = "dasdas你眼中的世界就是你自己的样子";

    /**
     * 注意：lombok对static变量不会生成 setter、getter、toString 。
     * @return
     */
    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", sex='" + sex +'\'' +
                ", signature='" + signature + '\'' +
                '}';
    }
}
