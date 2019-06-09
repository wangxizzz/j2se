import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.*;

/**
 * Description
 * Author wxi.wang
 * Date 2019/3/11 19:01
 */
@Getter
@Setter
@AllArgsConstructor
@ToString
public class User {
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
}