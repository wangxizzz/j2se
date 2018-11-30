package 讲师代码.exam1;


import 讲师代码.exam1.context.CurrencyCalculationContext;

import java.util.Scanner;

/**
 *
 * @author Mingxin Wang
 */
public final class Main {
    public static void main(String[] args) {
        CurrencyCalculationContext context = new CurrencyCalculationContext();
        try (Scanner scanner = new Scanner(System.in)) {
            while (scanner.hasNext()) {
                String statement = scanner.nextLine().trim();
                if (statement.equals("exit")) {
                    break;
                }
                context.process(statement);
            }
        }
    }
}
