package com.github.xzb617.cappuccino.server.clients;

import com.github.xzb617.cappuccino.server.cluster.ClusterBroadcaster;
import com.github.xzb617.cappuccino.server.properties.ServerProperties;
import com.github.xzb617.cappuccino.server.utils.CollectionUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.*;

/**
 * 客户端下发器
 * <p>功能是下发消息给客户端</p>
 * @author xzb617
 */
@Component("clientsTransmitter")
public class ClientsTransmitter {

    private final ServerProperties serverProperties;
    private final ClientsContainer clientsContainer;
    private final ClusterBroadcaster clusterBroadcaster;
    private final static String RESP_BODY = "config changed";

    public ClientsTransmitter(ServerProperties serverProperties, ClientsContainer clientsContainer, ClusterBroadcaster clusterBroadcaster) {
        this.serverProperties = serverProperties;
        this.clientsContainer = clientsContainer;
        this.clusterBroadcaster = clusterBroadcaster;
    }

    /**
     * 通知某个实例
     * @param clientKey 客户端标识
     * @param instanceKey 实例标识
     */
    public void transmitInstance(String clientKey, String instanceKey) {
        DeferredResult instance = this.clientsContainer.getInstance(clientKey, instanceKey);
        if (instance != null) {
            instance.setResult(ResponseEntity.status(HttpStatus.OK).body(RESP_BODY));
        }
        // 判断是否为集群模式
        if (this.serverProperties.getClusterEnabled()) {
            // 进行广播
            Set<String> instanceKeys = new HashSet<>(1);
            instanceKeys.add(instanceKey);
            this.clusterBroadcaster.broadcastTransmitClient(clientKey, instanceKeys);
        }
    }

    /**
     * 通知指定的多个实例
     * @param clientKey 客户端标识
     * @param instanceKeys 实例标识数组
     */
    public void transmitInstances(String clientKey, Set<String> instanceKeys) {
        for (String instanceKey : instanceKeys) {
            this.transmitInstance(clientKey, instanceKey);
        }
        // 判断是否为集群模式
        if (this.serverProperties.getClusterEnabled()) {
            // 进行广播
            this.clusterBroadcaster.broadcastTransmitClient(clientKey, instanceKeys);
        }
    }

    /**
     * 通知客户端下的所有实例
     * @param clientKey 客户端标识
     */
    public void transmitClient(String clientKey) {
        Map<String, DeferredResult> instanceMap = this.clientsContainer.getClient(clientKey);
        if (instanceMap!=null && !instanceMap.isEmpty()) {
            Set<String> instanceKeys = instanceMap.keySet();
            this.transmitInstances(clientKey, instanceKeys);
        }
        // 判断是否为集群模式
        if (this.serverProperties.getClusterEnabled()) {
            // 进行广播
            this.clusterBroadcaster.broadcastTransmitClient(clientKey, null);
        }
    }
}
