package 常用的工具类.guava.一致性hash;

import com.google.common.collect.Lists;
import com.google.common.hash.Hashing;
import org.junit.Test;

import java.nio.charset.Charset;
import java.util.List;

/**
 * Created by wxi.wang
 * <p>
 * 2019/10/28 17:08
 * Decription:
 * guava的一致性哈希  有待考证
 */
public class HashTest01 {
    @Test
    public void test01() {
        List<String> servers = Lists.newArrayList("server1", "server2", "server3", "server4", "server5");

        int someIdVal = Hashing.consistentHash(Hashing.sha256().hashString("someId", Charset.defaultCharset()), servers.size());
        System.out.println("someId routed to: " + servers.get(someIdVal));
        int blahVal = Hashing.consistentHash(Hashing.sha256().hashString("blah", Charset.defaultCharset()), servers.size());
        System.out.println(servers.get(blahVal));
        // one of the back end servers is removed from the (middle of the) pool
        servers.remove(1);

        System.out.println("移除一个后==========");

        int bucket = Hashing.consistentHash(Hashing.sha256().hashString("blah", Charset.defaultCharset()), servers.size());
        System.out.println("Second time blah routed to: " + servers.get(bucket));
    }
}
