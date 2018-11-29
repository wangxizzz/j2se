package 讲师代码.exam1.exception;

/**
 * Copyright (C) Qunar.com - All Rights Reserved.
 *
 * @author Mingxin Wang
 */
public final class IllFormedExpressionException extends Exception {
    public IllFormedExpressionException(String message) {
        super(message);
    }

    public IllFormedExpressionException(String message, Throwable cause) {
        super(message, cause);
    }
}
