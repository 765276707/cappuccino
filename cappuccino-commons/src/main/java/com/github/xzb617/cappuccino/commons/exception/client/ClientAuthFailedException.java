package com.github.xzb617.cappuccino.commons.exception.client;

import com.github.xzb617.cappuccino.commons.exception.CappuccinoRuntimeException;

/**
 * 客户端认证失败异常
 * @author xzb617
 */
public class ClientAuthFailedException extends CappuccinoRuntimeException {

    public ClientAuthFailedException() {
        super("Client auth failed. Please check your access configuration.");
    }
}
