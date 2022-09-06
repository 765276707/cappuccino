package com.github.xzb617.cappuccino.loadbalance.strategy;

import com.github.xzb617.cappuccino.commons.data.Server;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 基于权重的顺序轮询策略
 * @author xzb617
 */
public class WeightRoundRobinLoadBalancer extends RoundRobinLoadBalancer {

    @Override
    protected Server select(List<Server> servers) {
        List<Server> weightServers = this.calcWeightServers(servers);
        return super.select(weightServers);
    }

    private List<Server> calcWeightServers(List<Server> servers) {
        List<Server> weightServers = new ArrayList<>();
        Iterator<Server> it = servers.iterator();
        while (it.hasNext()) {
            Server server = it.next();
            Integer weight = server.getWeight();
            for (Integer i = 0; i < weight; i++) {
                weightServers.add(server);
            }
        }
        return weightServers;
    }
}
