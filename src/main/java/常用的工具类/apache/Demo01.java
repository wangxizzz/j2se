package 常用的工具类.apache;

import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * apache的一些工具类方法的总结
 */
public class Demo01 {
    // 利用日志文件
    private static final Logger LOGGER = LoggerFactory.getLogger(Demo01.class);
    /**
     * apache common collections 下的集合工具类
     */
    @Test
    public void test01() {
        // 使用guava里面的方法创建list
        List<Integer> list = Lists.newArrayList();
        // 判断list是否为空(指的是list为null或者size=0)
        if (CollectionUtils.isEmpty(list)) {
            LOGGER.debug("list为空");
        }
    }
}
