package com.github.xzb617.cappuccino.loadbalance;

import com.github.xzb617.cappuccino.commons.data.Server;

import java.util.List;

/**
 * 负载均衡，轮询接口，不同策略来实现
 * @author xzb617
 */
public interface LoadBalancer {

    /**
     * 选择服务地址
     * @param servers 服务节点集合
     * @return Server 服务节点
     */
    public Server choose(List<Server> servers);

}
