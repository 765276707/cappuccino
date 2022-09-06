package com.github.xzb617.cappuccino.server.cluster;

import com.github.xzb617.cappuccino.rpc.LoadBalanceRpcBroker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 集群节点广播器
 * <p>向集群中的其他节点进行广播消息</p>
 * @author xzb617
 */
@Component("clusterBroadcaster")
public class ClusterBroadcaster {

    private final static Logger LOGGER = LoggerFactory.getLogger(ClusterBroadcaster.class);

    private final ClusterContainer clusterContainer;
    private final RestTemplate restTemplate;

    public ClusterBroadcaster(ClusterContainer clusterContainer, RestTemplate restTemplate) {
        this.clusterContainer = clusterContainer;
        this.restTemplate = restTemplate;
    }

    /**
     * 广播下发客户端节点
     */
    public void broadcastTransmitClient(String clientKey, Set<String> instancesKeys) {
        Map<String, Object> paramMap = new HashMap<>(2);
        paramMap.put("clientKey", clientKey);
        paramMap.put("instancesKeys", instancesKeys);
        for (ClusterNode serverNode : this.clusterContainer.getAliveNodes(true)) {
            String address = serverNode.getAddress();
            String url = address + "/cluster/transmitClient";
            this.restTemplate.postForEntity(url, paramMap, String.class);
        }
    }

    /**
     * 广播关闭客户端节点下线
     */
    public void broadcastShutdownClient(String clientKey, String instancesKeys) {
        Map<String, String> paramMap = new HashMap<>(2);
        paramMap.put("clientKey", clientKey);
        paramMap.put("instanceKey", instancesKeys);
        for (ClusterNode serverNode : this.clusterContainer.getAliveNodes(true)) {
            String address = serverNode.getAddress();
            String url = address + "/cluster/shutdownClient";
            this.restTemplate.postForEntity(url, paramMap, String.class);
        }
    }

    /**
     * 广播服务端节点下线
     */
    void broadcastShutdownServer() {
        String clusterNode = this.clusterContainer.getLocalHostAddr();
        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>(1);
        paramMap.add("clusterNode", clusterNode);
        for (ClusterNode serverNode : this.clusterContainer.getAliveNodes(true)) {
            String address = serverNode.getAddress();
            String url = address + "/cluster/shutdownServer";
            this.restTemplate.postForEntity(url, paramMap, String.class);
        }
    }

    /**
     * 广播服务端节点上线
     */
    void broadcastStartupServer() {
        String clusterNode = this.clusterContainer.getLocalHostAddr();
        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>(1);
        paramMap.add("clusterNode", clusterNode);
        for (ClusterNode serverNode : this.clusterContainer.getAliveNodes(true)) {
            String address = serverNode.getAddress();
            String url = address + "/cluster/startupServer";
            try {
                this.restTemplate.postForEntity(url, paramMap, String.class);
            } catch (Throwable e) {
                LOGGER.warn("Send startup command to cluster node [{}] failed.", url);
            }
        }
    }

}
