package 讲师代码.fresh.question5;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

/**
 * Copyright (C) Qunar.com - All Rights Reserved.
 *
 * @author Mingxin Wang
 */
public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String command;
        while (!(command = input.nextLine()).equals("exit")) {
            try {
                LOGGER.info(CommandExecutor.execute(command));
            } catch (CommandExecutionException e) {
                LOGGER.error("命令执行失败", e);
            }
        }
        input.close();
    }
}
