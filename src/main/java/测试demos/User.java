package 测试demos;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * Description
 * Author wxi.wang
 * Date 2019/3/11 19:01
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    private int id;
    private String username;

    @Override
    public int hashCode() {
        int h = 0;
        if (username.length() > 0) {
            char val[] = username.toCharArray();

            for (int i = 0; i < username.length(); i++) {
                h = 31 * h + val[i];
            }
        }
        return h;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof User)) {
            return false;
        }
        User user = (User)obj;
        return this.username.equals(user.username);
    }

    public static void main(String[] args) {
        System.out.println(new User().toString());
        // fast json不支持类路径下的中文包名
//        System.out.println(JSON.toJSONString(new User(1, "aaa")));

    }
}