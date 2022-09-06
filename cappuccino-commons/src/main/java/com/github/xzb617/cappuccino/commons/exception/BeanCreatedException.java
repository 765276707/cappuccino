package com.github.xzb617.cappuccino.commons.exception;

/**
 * Bean创建异常
 * @author xzb617
 */
public class BeanCreatedException extends CappuccinoException {

    public BeanCreatedException(String message) {
        super(message);
    }

    public BeanCreatedException(String message, Throwable cause) {
        super(message, cause);
    }
}
