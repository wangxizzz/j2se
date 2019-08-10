package java高级知识.set存储对象问题;

import com.google.common.collect.Sets;
import org.junit.Test;

import java.util.Set;

/**
 * Created by wxi.wang
 * <p>
 * 2019/7/31 20:16
 * Decription:
 */
public class Main {
    @Test
    public void test01() {
        Set<DirectFlightParam> infoFromUrl = Sets.newHashSet();
        Set<DirectFlightParam> infoFromDb = Sets.newHashSet();
        DirectFlightParam param1 = new DirectFlightParam("北京", "三亚");
        DirectFlightParam param2 = new DirectFlightParam("上海", "厦门");
        DirectFlightParam param3 = new DirectFlightParam("杭州", "厦门");

        DirectFlightParam param4 = new DirectFlightParam("上海", "厦门");
        DirectFlightParam param5 = new DirectFlightParam("杭州", "厦门");

        infoFromUrl.add(param1);
        infoFromUrl.add(param2);
        infoFromUrl.add(param3);

        infoFromDb.add(param4);
        infoFromDb.add(param5);
        System.out.println(infoFromUrl);

        System.out.println(Sets.difference(infoFromUrl, infoFromDb));
    }

    @Test
    public void test02() {
        Set<DirectFlightParam> infoFromUrl = Sets.newHashSet();
        DirectFlightParam param = new DirectFlightParam();
        for (int i = 0; i < 3; i++) {
            param.setDepCity("北京" + i);
            param.setArrCity("上海" + i);
            infoFromUrl.add(param);
        }

        System.out.println(infoFromUrl);
    }
}
