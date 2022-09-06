package com.github.xzb617.cappuccino.rpc;

import com.github.xzb617.cappuccino.commons.data.Meta;
import com.github.xzb617.cappuccino.commons.data.Server;
import com.github.xzb617.cappuccino.http.RpcSender;
import com.github.xzb617.cappuccino.http.entity.RpcResponse;
import com.github.xzb617.cappuccino.loadbalance.LoadBalancer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 基于负载均衡的RPC代理者
 * @author xzb617
 */
public class LoadBalanceRpcBroker {

    private final Map<String, String> metaMap;
    private final List<Server> servers;
    private final RpcSender rpcSender;
    private final LoadBalancer loadBalancer;

    public LoadBalanceRpcBroker(Meta meta, List<Server> servers, RpcSender rpcSender, LoadBalancer loadBalancer) {
        this.metaMap = meta.toParameterMap();
        this.servers = servers;
        this.rpcSender = rpcSender;
        this.loadBalancer = loadBalancer;
    }

    public RpcResponse lbGet(String methodPath, Map<String, String> parameters) {
        Server server = this.loadBalancer.choose(this.servers);
        this.addMetaMapToParameters(parameters);
        return this.rpcSender.get(server, methodPath, parameters);
    }

    public RpcResponse lbPost(String methodPath, Map<String, String> parameters) {
        Server server = this.loadBalancer.choose(this.servers);
        this.addMetaMapToParameters(parameters);
        return this.rpcSender.post(server, methodPath, parameters);
    }

    public RpcResponse lbDelete(String methodPath, Map<String, String> parameters) {
        Server server = this.loadBalancer.choose(this.servers);
        this.addMetaMapToParameters(parameters);
        return this.rpcSender.delete(server, methodPath, parameters);
    }

    public void pingAll() {
        for (Server server : this.servers) {
            this.rpcSender.ping(server, this.metaMap);
        }
    }

    private void addMetaMapToParameters(Map<String, String> parameters) {
        if (parameters == null) {
            parameters = new HashMap<>();
        }
        parameters.putAll(metaMap);
    }
}
