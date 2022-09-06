package com.github.xzb617.cappuccino.loadbalance.factory;

/**
 * 负载均衡策略枚举
 * @author xzb617
 */
public enum LoadBalanceStrategy {

    /**
     * 随机
     */
    RANDOM("com.github.xzb617.cappuccino.loadbalance.strategy.RandomLoadBalancer"),
    /**
     * 轮询
     */
    ROUND_ROBIN("com.github.xzb617.cappuccino.loadbalance.strategy.RoundRobinLoadBalancer"),
    /**
     * 权重加轮询
     */
    WEIGHT_ROUND_ROBIN("com.github.xzb617.cappuccino.loadbalance.strategy.WeightRoundRobinLoadBalancer");

    private String value;

    LoadBalanceStrategy(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static LoadBalanceStrategy get(String key) {
        LoadBalanceStrategy[] strategies = values();
        LoadBalanceStrategy res = null;
        for (LoadBalanceStrategy strategy : strategies) {
            if (strategy.name().equalsIgnoreCase(key)) {
                res = strategy;
                break;
            }
        }
        return res;
    }

}
