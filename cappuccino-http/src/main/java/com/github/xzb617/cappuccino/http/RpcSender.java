package com.github.xzb617.cappuccino.http;

import com.github.xzb617.cappuccino.commons.data.Meta;
import com.github.xzb617.cappuccino.commons.data.Server;
import com.github.xzb617.cappuccino.commons.exception.BeanCreatedException;
import com.github.xzb617.cappuccino.http.entity.RpcResponse;
import com.github.xzb617.cappuccino.http.ex.RpcException;
import com.github.xzb617.cappuccino.loadbalance.factory.LoadBalanceStrategy;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * RPC代理者
 * @author xzb
 */
public interface RpcSender {

    String getName();

    void initConfig(Map<String, String> headers, Integer timeout, String encode);

    RpcResponse get(Server server, String methodPath, Map<String, String> parameters) throws RpcException;

    RpcResponse post(Server server, String methodPath, Map<String, String> parameters) throws RpcException;

    RpcResponse delete(Server server, String methodPath, Map<String, String> parameters) throws RpcException;

    void ping(Server server, Map<String, String> parameters);
}
