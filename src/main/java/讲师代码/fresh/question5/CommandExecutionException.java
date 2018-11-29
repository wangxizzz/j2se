package 讲师代码.fresh.question5;

/**
 * Copyright (C) Qunar.com - All Rights Reserved.
 *
 * @author Mingxin Wang
 *
 * 命令执行异常
 */
final class CommandExecutionException extends Exception {
    CommandExecutionException(String how, String what) {
        super("命令执行错误；原因：" + how + "；错误信息：" + what);
    }

    CommandExecutionException(Exception e) {
        super(e);
    }
}
