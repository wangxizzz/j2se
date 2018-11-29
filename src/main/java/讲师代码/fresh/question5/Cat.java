package 讲师代码.fresh.question5;

/**
 * Copyright (C) Qunar.com - All Rights Reserved.
 *
 * @author Mingxin Wang
 *
 * cat命令实现
 * Concatenate FILE(s) to standard output.
 * 语法：cat [FILES...]
 * 如果没有指定文件，或者文件为"-"，则从标准输入读取。
 */
final class Cat extends TextAppletExecution {
    private StringBuilder result = new StringBuilder();

    @Override
    protected void addData(String input) {
        result.append(input);
    }

    @Override
    protected String getResult() {
        return result.toString();
    }
}
