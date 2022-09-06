package com.github.xzb617.cappuccino.loadbalance.strategy;

import com.github.xzb617.cappuccino.commons.data.Server;
import com.github.xzb617.cappuccino.loadbalance.AbstractLoadBalancer;

import java.util.List;
import java.util.Random;

/**
 * 随机选择
 * @author xzb617
 */
public class RandomLoadBalancer extends AbstractLoadBalancer {

    private final Random random = new Random();

    @Override
    protected Server select(List<Server> servers) {
        int size = servers.size();
        int position = this.random.nextInt(size);
        return servers.get(position);
    }

}
