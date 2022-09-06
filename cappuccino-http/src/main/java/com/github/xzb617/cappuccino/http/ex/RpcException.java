package com.github.xzb617.cappuccino.http.ex;

import com.github.xzb617.cappuccino.commons.exception.CappuccinoRuntimeException;

/**
 * RPC调用异常
 */
public class RpcException extends CappuccinoRuntimeException {

    public RpcException(String message) {
        super(message);
    }

    public RpcException(String message, Throwable cause) {
        super(message, cause);
    }

}
