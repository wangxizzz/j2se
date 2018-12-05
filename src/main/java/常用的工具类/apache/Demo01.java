package 常用的工具类.apache;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Lists;
import com.google.common.collect.Multiset;
import com.google.common.io.Resources;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;
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

    @Test
    public void test02() {
        // 统计两个字符串不同字符的个数
        /**
         * apache lang3参考https://www.cnblogs.com/shihaiming/p/7814804.html
         */
        int levenshteinDistance = StringUtils.getLevenshteinDistance("aaa", "aaaaa");
        System.out.println(levenshteinDistance);
    }
}
