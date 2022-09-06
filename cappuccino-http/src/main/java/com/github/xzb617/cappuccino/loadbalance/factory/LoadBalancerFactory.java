package com.github.xzb617.cappuccino.loadbalance.factory;

import com.github.xzb617.cappuccino.commons.exception.BeanCreatedException;
import com.github.xzb617.cappuccino.loadbalance.LoadBalancer;
import java.lang.reflect.Constructor;

/**
 * 负载均衡工厂
 * @author xzb617
 */
public class LoadBalancerFactory {

    /**
     * 根据策略获取负载均衡实例
     * @param loadBalanceStrategy 负载均衡策略
     * @return LoadBalancer
     */
    public static LoadBalancer getInstance(LoadBalanceStrategy loadBalanceStrategy) throws BeanCreatedException {
        if (loadBalanceStrategy == null) {
            throw new IllegalArgumentException("No LoadBalanceStrategy applied");
        }
        try
        {
            Class<?> algClazz = Class.forName(loadBalanceStrategy.getValue());
            Constructor<?> constructor = algClazz.getDeclaredConstructor();
            return (LoadBalancer) constructor.newInstance();
        }
        catch (ClassNotFoundException e)
        {
            throw new BeanCreatedException(String.format(" [%s] can not be found, check this class path or it exists.", loadBalanceStrategy.getValue()));
        }
        catch (Exception e)
        {
            throw new BeanCreatedException("LoadBalancer bean init failure.", e);
        }
    }

}
