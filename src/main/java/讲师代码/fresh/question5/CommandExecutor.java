package 讲师代码.fresh.question5;

import com.google.common.base.Splitter;

import java.util.Iterator;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * Copyright (C) Qunar.com - All Rights Reserved.
 *
 * @author Mingxin Wang
 *
 * 命令执行器
 */
final class CommandExecutor {
    private static final Splitter PIPE_SPLITTER = Splitter.on('|');
    private static final Splitter TOKEN_SPLITTER = Splitter.on(Pattern.compile("\\s+"));

    // 无法实例化
    private CommandExecutor() {
        throw new UnsupportedOperationException();
    }

    static String execute(String commandLine) throws CommandExecutionException {
        String result = "";
        // 空串合法；需特判
        if (commandLine.isEmpty()) {
            return result;
        }

        // 依次解析每个管道的命令
        for (String singleCommandLine : PIPE_SPLITTER.split(commandLine)) {
            singleCommandLine = singleCommandLine.trim();
            if (singleCommandLine.isEmpty()) {
                throw new CommandExecutionException("管道语法错误", commandLine);
            }
            Iterator<String> tokens = TOKEN_SPLITTER.split(singleCommandLine).iterator();
            Optional<AppletExecution> execution = AppletFactory.newExecution(tokens.next());
            if (!execution.isPresent()) {
                throw new CommandExecutionException("命令未找到", singleCommandLine);
            }
            result = execution.get().execute(result, tokens);
        }
        return result;
    }
}
