package 讲师代码.fresh.question5;

import java.util.Iterator;

/**
 * Copyright (C) Qunar.com - All Rights Reserved.
 *
 * @author Mingxin Wang
 */
interface AppletExecution {
    // 输入：
    //   1. 代表”标准输入“的字符串
    //   2. 选项信息
    // 输出：
    //   代表”标准输出“的字符串
    String execute(String input, Iterator<String> options) throws CommandExecutionException;
}
