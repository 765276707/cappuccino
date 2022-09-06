package com.github.xzb617.cappuccino.server.cluster;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component("clusterReceiver")
public class ClusterReceiver {

    private final ClusterContainer clusterContainer;
    private final RestTemplate restTemplate;

    public ClusterReceiver(ClusterContainer clusterContainer, RestTemplate restTemplate) {
        this.clusterContainer = clusterContainer;
        this.restTemplate = restTemplate;
    }

    /**
     * 从其他节点上获取该客户端监听实例
     * @param clientKey 客户端Key
     * @return Set
     */
    public Set<String> getMonitorInstances(String clientKey) {
        Map<String, String> paramMap = new HashMap<>(0);
        paramMap.put("clientKey", clientKey);
        Set<String> resSet = new HashSet<>();
        for (ClusterNode clusterNode : this.clusterContainer.getAliveNodes(true)) {
            String address = clusterNode.getAddress();
            String url = address + "/getClientMonitorInstances";
            ResponseEntity<Set> resp = this.restTemplate.getForEntity(url, Set.class, paramMap);
            if (resp.getStatusCode() == HttpStatus.OK) {
                resSet.addAll(resp.getBody());
            }
        }
        return resSet;
    }
}
