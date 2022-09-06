package com.github.xzb617.cappuccino.loadbalance;

import com.github.xzb617.cappuccino.commons.data.Server;

import java.util.List;

public abstract class AbstractLoadBalancer implements LoadBalancer{

    @Override
    public Server choose(List<Server> servers) {
        if (servers.isEmpty()) {
            throw new IllegalArgumentException("Server node list cannot be empty.");
        }
        if (servers.size() == 1) {
            return servers.get(0);
        }
        return this.select(servers);
    }

    protected abstract Server select(List<Server> servers);

}
