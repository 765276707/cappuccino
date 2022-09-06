package com.github.xzb617.cappuccino.loadbalance.strategy;

import com.github.xzb617.cappuccino.commons.data.Server;
import com.github.xzb617.cappuccino.loadbalance.AbstractLoadBalancer;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 顺序轮询策略
 * @author xzb617
 */
public class RoundRobinLoadBalancer extends AbstractLoadBalancer {

    private final AtomicInteger serverPositionCounter = new AtomicInteger(0);

    @Override
    protected Server select(List<Server> servers) {
        Server server = null;
        synchronized (RoundRobinLoadBalancer.class) {
            int position = this.serverPositionCounter.getAndIncrement();
            if (position >= servers.size()) {
                this.serverPositionCounter.set(1);
                position = 0;
            }
            server = servers.get(position);
        }
        return server;
    }

}
