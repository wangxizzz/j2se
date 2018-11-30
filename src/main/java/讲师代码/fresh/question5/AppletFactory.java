package 讲师代码.fresh.question5;

import java.util.Optional;

/**
 *
 * @author Mingxin Wang
 */
final class AppletFactory {
    // 无法实例化
    private AppletFactory() {
    }

    static Optional<AppletExecution> newExecution(String executionName) {
        switch (executionName) {
            case "cat":
                return Optional.of(new Cat());
            case "grep":
                return Optional.of(new Grep());
            case "wc":
                return Optional.of(new Wc());
        }
        return Optional.empty();
    }
}
