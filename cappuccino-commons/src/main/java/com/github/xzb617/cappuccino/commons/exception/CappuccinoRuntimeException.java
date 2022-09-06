package com.github.xzb617.cappuccino.commons.exception;

/**
 * 运行时异常
 */
public class CappuccinoRuntimeException extends RuntimeException {

    public CappuccinoRuntimeException() {
    }

    public CappuccinoRuntimeException(String message) {
        super(message);
    }

    public CappuccinoRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public CappuccinoRuntimeException(Throwable cause) {
        super(cause);
    }

    public CappuccinoRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
