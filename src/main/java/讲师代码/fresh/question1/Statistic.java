package 讲师代码.fresh.question1;

import com.google.common.collect.*;

import java.text.ParseException;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


final class Statistic {
    private static final int GET = 0, POST = 1, HEAD = 2;
    private static final String[] tags = {"GET", "POST", "HEAD"};

    // 日志数据集
    private List<Info> data = Lists.newArrayList();

    // 增加一行条目；若解析失败则抛出异常
    void add(String line) throws ParseException {
        // 以第一个空格分割类别标签和URL
        int separatorPos = line.indexOf(' ');
        if (separatorPos == -1) {
            throw new ParseException(line, 0);
        }
        String tag = line.substring(0, separatorPos);
        String url = line.substring(separatorPos + 1);
        for (int i = 0; i < tags.length; ++i) {
            if (tag.equals(tags[i])) {
                data.add(new Info(i, url));
                return;
            }
        }
        // 若未找到此标签则解析失败
        throw new ParseException(line, 0);
    }

    // 获得前k个频率最高的HTTP请求
    private List<Multiset.Entry<String>> getTopFrequencyRequests(int k) {
        // 提取data的url信息至requestSet
        Multiset<String> requestSet = HashMultiset.create();
        requestSet.addAll(data.stream()
                .map(info -> info.url)
                .collect(Collectors.toList()));

        // 抽取requestSet中的url频率统计的前k条信息
        return requestSet.entrySet().stream()
                .collect(Comparators.greatest(k, Comparator.comparingInt(Multiset.Entry::getCount)));
    }

    // 按照域名分组
    private Map<String, Collection<String>> groupByDomain() {
        Multimap<String, String> domainMap = HashMultimap.create();
        data.forEach(info -> domainMap.put(info.getDomain(), info.toString()));
        return domainMap.asMap();
    }

    // 按类别统计请求数量
    private int getRequestCount(int category) {
        return (int) data.stream().filter(info -> (info.category == category)).count();
    }

    // 获取统计结果
    String getResult() {
        List<Multiset.Entry<String>> topFrequencyRequests = getTopFrequencyRequests(10);
        Map<String, Collection<String>> domainMap = groupByDomain();

        // 构造结果
        StringBuilder result = new StringBuilder();
        result.append("请求总量：").append(data.size()).append(System.lineSeparator());
        result.append("请求最频繁的前 ").append(topFrequencyRequests.size()).append(" 个 HTTP 接口如下：").append(System.lineSeparator());
        for (Multiset.Entry<String> entry : topFrequencyRequests) {
            result.append("  ").append(entry.getCount()).append("  ").append(entry.getElement()).append(System.lineSeparator());
        }
        result.append("POST请求数量：").append(getRequestCount(POST)).append(System.lineSeparator());
        result.append("GET请求总量：").append(getRequestCount(GET)).append(System.lineSeparator());
        result.append("根据域名，共有").append(domainMap.size()).append("组请求：").append(System.lineSeparator());
        for (Map.Entry<String, Collection<String>> entry : domainMap.entrySet()) {
            result.append("  ").append(entry.getKey()).append(System.lineSeparator());
            for (String request : entry.getValue()) {
                result.append("    ").append(request).append(System.lineSeparator());
            }
        }
        return result.toString();
    }

    // 日志条目
    private static class Info {
        int category;
        String url;

        private Info(int category, String url) {
            this.category = category;
            this.url = url;
        }

        @Override
        public String toString() {
            return tags[category] + " " + url;
        }

        // 通过url解析域名
        private String getDomain() {
            int beginIndex = url.startsWith("/") ? 1 : 0;
            int endIndex = url.indexOf("/", beginIndex);
            if (endIndex == -1) {
                endIndex = url.length();
            }
            return url.substring(beginIndex, endIndex);
        }
    }
}
