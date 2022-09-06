package com.github.xzb617.cappuccino.commons.exception;

/**
 * 编译时异常
 */
public class CappuccinoException extends Exception {

    public CappuccinoException() {
    }

    public CappuccinoException(String message) {
        super(message);
    }

    public CappuccinoException(String message, Throwable cause) {
        super(message, cause);
    }

    public CappuccinoException(Throwable cause) {
        super(cause);
    }

    public CappuccinoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
