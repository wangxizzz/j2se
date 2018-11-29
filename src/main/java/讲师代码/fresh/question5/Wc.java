package 讲师代码.fresh.question5;

/**
 * Copyright (C) Qunar.com - All Rights Reserved.
 *
 * @author Mingxin Wang
 *
 * wc命令实现
 * 统计所有输入文件的总行数
 * 语法：wc -l [FILES...]
 * 如果没有指定文件，或者文件为"-"，则从标准输入读取。
 */
final class Wc extends TextAppletExecution {
    private boolean calcLineCount = false;
    private int lineCount = 0;

    // 计算一个特定的字符串中行数（以换行符作为标识）
    private static int calcLine(String input) {
        if (input.isEmpty()) {
            return 0;
        }
        int pos = input.indexOf("\n"), result = 1;
        while (pos != -1 && pos < input.length() - 1) {
            ++result;
            pos = input.indexOf("\n", pos + 1);
        }
        return result;
    }

    @Override
    protected void addData(String input) throws CommandExecutionException {
        if (!calcLineCount) {
            throw new CommandExecutionException("未启用选项，无法继续命令解析", "第一个参数应为-l");
        }
        lineCount += calcLine(input);
    }

    @Override
    protected void setFlag(char c) throws CommandExecutionException {
        if (c != 'l') {
            throw new CommandExecutionException("无法识别的选项", "" + c);
        }
        calcLineCount = true;
    }

    // 获取结果
    // 异常：当-l参数未指定时，
    @Override
    protected String getResult() throws CommandExecutionException {
        if (!calcLineCount) {
            throw new CommandExecutionException("期望的选项", "-l参数未指定");
        }
        return "" + lineCount;
    }
}
