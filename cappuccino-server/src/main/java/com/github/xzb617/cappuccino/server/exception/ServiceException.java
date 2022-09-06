package com.github.xzb617.cappuccino.server.exception;

import com.github.xzb617.cappuccino.commons.exception.CappuccinoRuntimeException;

/**
 * 业务异常
 * @author xzb617
 */
public class ServiceException extends CappuccinoRuntimeException {

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

}
