/**
 * 
 */
package 讲师代码.java基础.basic.homework;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.Collectors;

/**
 * 题目2：从access.log中统计数据
 * 0. 使用 Java&Guava
 * 1. 输出请求总量
 * 2. 输出请求最频繁的10个 HTTP 接口，及其相应的请求数量
 * 3. 统计 POST 和 GET 请求量分别为多少
 * 4. URI 格式均为 /AAA/BBB 或者 /AAA/BBB/CCC 格式，按 AAA 分类，输出各个类别下 URI 都有哪些access log会发给大家
 *
 * @author Guanying Piao
 *
 * 2018-07-23
 */
public class AccessLogStats {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(AccessLogStats.class);
    private static final String GET = "GET";
    private static final String POST = "POST";
    private static final int METHOD_INDEX = 0;
    private static final int TYPE_INDEX = 1;
    private static final int URL_INDEX = 2;
    private static final int TOP_N = 10;
    
    public AccessLogStats() {}
    
    public static void stats(String path) {
        LongAdder count = new LongAdder();
        Map<String, LongAdder> methodCounts = new TreeMap<>();
        Map<String, LongAdder> urlCounts = new HashMap<>();
        Map<String, Set<String>> urls = new TreeMap<>();
        
        try (   InputStream is = AccessLogStats.class.getResourceAsStream(path);
                InputStreamReader isReader = new InputStreamReader(is);
                BufferedReader reader = new BufferedReader(isReader);) {
            reader.lines().forEach(line -> {
                try {
                    String[] infos = parse(line);
                    String method = infos[METHOD_INDEX];
                    String type = infos[TYPE_INDEX];
                    String url = infos[URL_INDEX];
                    count.increment();
                    methodCounts.computeIfAbsent(method, key -> new LongAdder()).increment();
                    urlCounts.computeIfAbsent(url, key -> new LongAdder()).increment();
                    urls.computeIfAbsent(type, key -> new HashSet<>()).add(url);
                } catch (Exception e) {
                    LOGGER.warn("Got exception for line:{} error:{}", line, e.getMessage());
                }
            });
        } catch (Exception e) {
            LOGGER.warn("Got exception while parsing accesslog", e);
        }
        List<Entry<String, LongAdder>> urlAndCounts = urlCounts.entrySet().stream().collect(Collectors.toList());
        urlAndCounts.sort(Comparator.comparingLong(entry -> - entry.getValue().longValue()));
        LOGGER.info("Total count:{}", count.longValue());
        LOGGER.info("Method count:{}", methodCounts.entrySet().stream()
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .collect(Collectors.joining(", ", "{", "}")));
        for (int i = 0; i < TOP_N; i++) {
            Entry<String, LongAdder> urlAndCount = urlAndCounts.get(i);
            LOGGER.info("Url counts for url: {} is {}", urlAndCount.getKey(), urlAndCount.getValue());
        }
        for (Entry<String, Set<String>> typeAndUrls : urls.entrySet()) {
            LOGGER.info("Type:{} contains urls:{}", typeAndUrls.getKey(), 
                    typeAndUrls.getValue().stream().collect(Collectors.joining(", ", "[", "]")));
        }
    }

    static String[] parse(String line) {
        String[] parts = line.split(" ");
        if (parts.length != 2 || Arrays.stream(parts).anyMatch(part -> part == null || part.trim().isEmpty())) {            
            throw new IllegalArgumentException("Illegal line: " + line);
        }
        String method = parts[0].toUpperCase();
        if (!GET.equals(method) && !POST.equals(method)) {
            throw new IllegalArgumentException("Illegal HTTP method for line: " + line);
        }
        String request = parts[1];
        parts = request.split("\\?");
        if (parts.length < 1 || parts[0].trim().isEmpty()) {
            throw new IllegalArgumentException("Cannot parse uri: " + line);
        }
        String url = parts[0];
        if (url == null || url.trim().isEmpty()) {
            throw new IllegalArgumentException("Cannot parse uri: " + line);
        }
        parts = url.split("/");
        String type = parts[1];
        if (type == null || type.trim().isEmpty()) {
            throw new IllegalArgumentException("Cannot parse type: " + line);
        }
        return new String[] {method, type, url};
    }

    public static final void main(String[] args) {
        AccessLogStats.stats("/access.txt");
    }
    
}
