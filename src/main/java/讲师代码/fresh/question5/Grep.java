package 讲师代码.fresh.question5;

import com.google.common.base.Splitter;

import java.util.regex.Pattern;

/**
 * Copyright (C) Qunar.com - All Rights Reserved.
 *
 * @author Mingxin Wang
 *
 * grep命令实现
 * 在每个 FILE 或是标准输入中查找 PATTERN。
 * 语法：grep PATTERN [FILES...]
 * 如果没有指定文件，或者文件为"-"，则从标准输入读取。
 */
final class Grep extends TextAppletExecution {
    private static Splitter LINE_SPLITTER = Splitter.on("\n");
    private Pattern pattern = null;
    private StringBuilder result = new StringBuilder();

    @Override
    protected void addData(String input) {
        for (String line : LINE_SPLITTER.split(input)) {
            if (pattern.matcher(line).find()) {
                result.append(line);
                result.append(System.lineSeparator());
            }
        }
    }

    @Override
    protected void addParam(String param) {
        pattern = Pattern.compile(param);
    }

    @Override
    protected boolean acceptsData() {
        return pattern != null;
    }

    @Override
    protected String getResult() throws CommandExecutionException {
        if (pattern == null) {
            throw new CommandExecutionException("参数数量错误", "第一个参数应指定为模式");
        }
        return result.toString();
    }
}
