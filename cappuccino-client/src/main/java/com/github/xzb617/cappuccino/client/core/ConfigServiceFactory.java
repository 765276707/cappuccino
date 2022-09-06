package com.github.xzb617.cappuccino.client.core;

import com.github.xzb617.cappuccino.client.props.ClientProperties;
import com.github.xzb617.cappuccino.client.utils.ServerUtil;
import com.github.xzb617.cappuccino.commons.data.Meta;
import com.github.xzb617.cappuccino.commons.data.Server;
import com.github.xzb617.cappuccino.commons.exception.BeanCreatedException;
import com.github.xzb617.cappuccino.http.HttpClientRpcSender;
import com.github.xzb617.cappuccino.http.RpcSender;
import com.github.xzb617.cappuccino.loadbalance.LoadBalancer;
import com.github.xzb617.cappuccino.loadbalance.factory.LoadBalanceStrategy;
import com.github.xzb617.cappuccino.loadbalance.factory.LoadBalancerFactory;
import com.github.xzb617.cappuccino.rpc.LoadBalanceRpcBroker;
import org.springframework.beans.factory.BeanCreationException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ConfigService 工厂
 */
public class ConfigServiceFactory {

    private static ConfigService configService;

    public static ConfigService getInstance(ClientProperties clientProperties) {
        if (configService == null) {
            synchronized (ConfigServiceFactory.class) {
                if (configService == null) {
                    LoadBalanceRpcBroker loadBalanceRpcBroker = null;
                    try {
                        loadBalanceRpcBroker = createLoadBalanceRpcBroker(clientProperties);
                    } catch (BeanCreatedException e) {
                        throw new BeanCreationException(e.getMessage());
                    }
                    configService = new ConfigService(clientProperties, loadBalanceRpcBroker);
                }
            }
        }
        return configService;
    }

    private static LoadBalanceRpcBroker createLoadBalanceRpcBroker(ClientProperties clientProperties) throws BeanCreatedException {
        Meta meta = generateMeta(clientProperties);
        List<Server> servers = generateServers(clientProperties);
        RpcSender rpcSender = createRpcSender(clientProperties);
        LoadBalancer loadBalancer = createLoadBalancer(clientProperties);
        return new LoadBalanceRpcBroker(meta, servers, rpcSender, loadBalancer);
    }

    private static RpcSender createRpcSender(ClientProperties clientProperties) {
        // 请求头（客户端认证）
        Map<String, String> headers = new HashMap<>(2);
        headers.put("Access-Key", clientProperties.getAccessKey());
        headers.put("Access-Secret", clientProperties.getAccessSecret());
        // 初始化 RpcSender
        RpcSender rpcSender = new HttpClientRpcSender();
        rpcSender.initConfig(headers, clientProperties.getTimeout(), clientProperties.getEncode());
        return rpcSender;
    }

    private static LoadBalancer createLoadBalancer(ClientProperties clientProperties) throws BeanCreatedException {
        LoadBalanceStrategy loadBalanceStrategy = clientProperties.getLoadBalanceStrategy();
        return LoadBalancerFactory.getInstance(loadBalanceStrategy);
    }

    private static Meta generateMeta(ClientProperties clientProperties) {
        Meta meta = new Meta();
        meta.setEnv(clientProperties.getEnv());
        meta.setGroup(clientProperties.getGroup());
        meta.setName(clientProperties.getName());
        meta.setGrayscale(clientProperties.getGrayscale());
        return meta;
    }

    private static List<Server> generateServers(ClientProperties clientProperties) {
        String serverAddr = clientProperties.getServerAddr();
        return ServerUtil.parseServers(serverAddr);
    }
}
