package com.github.xzb617.cappuccino.client.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.xzb617.cappuccino.client.props.ClientProperties;
import com.github.xzb617.cappuccino.client.utils.JsonUtil;
import com.github.xzb617.cappuccino.commons.api.ClientApi;
import com.github.xzb617.cappuccino.commons.data.Config;
import com.github.xzb617.cappuccino.commons.data.Meta;
import com.github.xzb617.cappuccino.commons.utils.MetaUtil;
import com.github.xzb617.cappuccino.http.entity.RpcResponse;
import com.github.xzb617.cappuccino.rpc.LoadBalanceRpcBroker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 与服务端业务交互的核心类
 * @author xzb617
 */
public class ConfigService {

    private final static Logger LOGGER = LoggerFactory.getLogger(ConfigService.class);
    private Meta meta;
    private final ClientProperties clientProperties;
    private final LoadBalanceRpcBroker loadBalanceRpcBroker;

    public ConfigService(ClientProperties clientProperties, LoadBalanceRpcBroker loadBalanceRpcBroker) {
        this.clientProperties = clientProperties;
        this.loadBalanceRpcBroker = loadBalanceRpcBroker;
        this.meta = new Meta(clientProperties.getEnv(), clientProperties.getGroup(), clientProperties.getName(), clientProperties.getGrayscale());
    }

    public Config getConfig() {
        RpcResponse rpcResponse = this.loadBalanceRpcBroker.lbGet(ClientApi.GET_CONFIG, this.meta.toParameterMap());
        if (rpcResponse.getHttpStatus() == 204) {
            // 没有对应的配置（客户端不存在 / 配置不存在）
            return null;
        }
        String result = rpcResponse.getHttpEntity();
        return JsonUtil.toObject(result, Config.class);
    }

    public boolean monitorConfig() {
        RpcResponse rpcResponse = this.loadBalanceRpcBroker.lbGet(ClientApi.MONITOR_CONFIG, this.meta.toParameterMap());
        int httpStatus = rpcResponse.getHttpStatus();
        return httpStatus == 200;
    }

    public void pingServers() {
        this.loadBalanceRpcBroker.pingAll();
    }

    public void shutdownClient() {
        this.loadBalanceRpcBroker.lbDelete(ClientApi.SHUTDOWN, this.meta.toParameterMap());
    }

}
