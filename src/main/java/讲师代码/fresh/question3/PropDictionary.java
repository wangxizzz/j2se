package 讲师代码.fresh.question3;

import com.google.common.base.Preconditions;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Copyright (C) Qunar.com - All Rights Reserved.
 *
 * @author Mingxin Wang
 *
 * 资源字典
 */
final class PropDictionary {
    // 四种查找方式的标签
    private static final int NATURAL_ORDER = 0, INDEX_ORDER = 1, CHAR_ORDER = 2, CHAR_ORDER_DESC = 3;
    private static final String[] KEY = {"natureOrder", "indexOrder", "charOrder", "charOrderDESC"};
    // 解析prop文件的正则表达式
    private static final Pattern PROP_LINE_PATTERN = Pattern.compile("^(\\d+?)\t(.+?)$");
    // 提取占位符信息的正则表达式
    private static final Pattern REPLACE_PATTERN = Pattern.compile("\\$((" + KEY[0] + ")|(" + KEY[1] + ")|(" + KEY[2] + ")|(" + KEY[3] + "))\\((\\d+?)\\)");
    // 数据集
    private String[][] data;
    // 私有构造方法
    private PropDictionary(String[][] data) {
        this.data = data;
    }

    // 通过prop列表创建资源字典
    static PropDictionary build(List<String> propList) throws ParsingException {
        // 一个四维的字典
        String[][] data = new String[KEY.length][propList.size()];

        // 当前构造的位置
        int position = 0;

        // 逐行读取prop信息
        for (String line : propList) {
            // 解析每一行的两个数据
            Matcher matcher = PROP_LINE_PATTERN.matcher(line);
            if (!matcher.find()) {
                throw new ParsingException(line);
            }
            int tag = Integer.parseInt(matcher.group(1));

            // 断言tag未越界
            Preconditions.checkElementIndex(tag, propList.size());
            String str = matcher.group(2);
            data[NATURAL_ORDER][position]
                    = data[INDEX_ORDER][tag]
                    = data[CHAR_ORDER][position]
                    = data[CHAR_ORDER_DESC][position]
                    = str;
            ++position;
        }

        // 对字典的后两列按题目要求排序
        Arrays.sort(data[CHAR_ORDER]);
        Arrays.sort(data[CHAR_ORDER_DESC], Comparator.reverseOrder());
        return new PropDictionary(data);
    }

    // 一行中的占位符替换为实际内容
    String convertLine(String line) {
        // 由StringBuilder构造
        StringBuilder result = new StringBuilder();

        // 每一个替换占位符将截短输入字符串
        while (line.length() > 0) {
            // 正则匹配占位符
            Matcher matcher = REPLACE_PATTERN.matcher(line);
            if (matcher.find()) {
                // 查找被匹配的占位符
                for (int i = 0; i < KEY.length; ++i) {
                    if (matcher.group(i + 2) != null) {
                        // 首先记录占位符之前的信息
                        result.append(line, 0, matcher.start());

                        // 然后替代占位符
                        result.append(data[i][Integer.parseInt(matcher.group(KEY.length + 2))]);

                        // 最后从截短字符串
                        line = line.substring(matcher.end());
                        break;
                    }
                }
            } else {
                // 未找到占位符的情况（之前的循环中可能已经替换掉若干占位符）直接将后续字符串追加至结果
                result.append(line);
                break;
            }
        }
        return result.toString();
    }
}
