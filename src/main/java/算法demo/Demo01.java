package 算法demo;

import org.junit.Test;

public class Demo01 {

    /**
     * 统计字符串中的单词个数,可以使用句子里一些额外的随机空格
     */
    @Test
    public void countWords() {
        String s = " Nel mezzo del cammin di nostra vita " +
                "mi ritrovai in una selva oscura" +
                " ché la dritta via era smarrita ";
        int count = 0;
        boolean lastSpace = true;
        char[] nums =  s.toCharArray();
        for (char c : nums) {
            // 判断是否为空字符
            if (Character.isWhitespace(c)) {
                lastSpace = true;
            } else {
                if (lastSpace) {
                    // 单词的个数加1
                    count++;
                }
                lastSpace = false;
            }
        }
        System.out.println(count);
    }
}
