package com.github.xzb617.cappuccino.server.cluster;

import com.github.xzb617.cappuccino.server.clients.ClientsContainer;
import com.github.xzb617.cappuccino.server.clients.ClientsTransmitter;
import com.github.xzb617.cappuccino.server.utils.CollectionUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RequestMapping("/cluster")
@RestController
public class ClusterEndpoint {

    private final ClientsTransmitter clientsTransmitter;
    private final ClientsContainer clientsContainer;
    private final ClusterContainer clusterContainer;

    public ClusterEndpoint(ClientsTransmitter clientsTransmitter, ClientsContainer clientsContainer, ClusterContainer clusterContainer) {
        this.clientsTransmitter = clientsTransmitter;
        this.clientsContainer = clientsContainer;
        this.clusterContainer = clusterContainer;
    }

    /**
     * 节点心跳检测
     * @return
     */
    @GetMapping("/ping")
    public ResponseEntity ping() {
        return ResponseEntity.status(HttpStatus.OK).body("pong");
    }

    /**
     * 获取客户端的监控实例
     * @param clientKey 客户端Key
     * @return
     */
    @GetMapping("/getLocalInstances")
    public Set<String> getLocalInstances(String clientKey) {
        return this.clientsContainer.getInstanceKeys(clientKey);
    }

    /**
     * 下发到客户端
     * @param clientKey 客户端Key
     * @param instanceKeys 实例Key数组拼接成的字符串
     * @return ResponseEntity
     */
    @PostMapping("/transmitClient")
    public ResponseEntity transmitClient(String clientKey, Set<String> instanceKeys) {
        if (instanceKeys != null) {
            this.clientsTransmitter.transmitInstances(clientKey, instanceKeys);
        } else {
            this.clientsTransmitter.transmitClient(clientKey);
        }
        return ResponseEntity.status(HttpStatus.OK).body("ok");
    }

    /**
     * 客户端关闭事件
     * @param clientKey 客户端Key
     * @param instanceKeys 实例Key数组拼接成的字符串
     * @return ResponseEntity
     */
    @PostMapping("/shutdownClient")
    public ResponseEntity shutdownClient(String clientKey, String instanceKeys) {
        if (instanceKeys != null) {
            Set<String> insKeySet = CollectionUtil.splitToSet(instanceKeys, ",");
            this.clientsTransmitter.transmitInstances(clientKey, insKeySet);
        } else {
            this.clientsTransmitter.transmitClient(clientKey);
        }
        return ResponseEntity.status(HttpStatus.OK).body("ok");
    }

    /**
     * 服务端关闭事件
     * @param clusterNode 关闭的服务端节点地址
     * @return ResponseEntity
     */
    @PostMapping("/shutdownServer")
    public ResponseEntity shutdownServer(String clusterNode) {
        List<ClusterNode> nodes = this.clusterContainer.getAllNodes();
        for (ClusterNode node : nodes) {
            if (node.getAddress().equals(clusterNode)) {
                node.setAlive(false);
                node.resetConnectFailedCounter();
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body("ok");
    }

    /**
     * 服务端节点启动通知事件
     * @param clusterNode 启动的服务端节点地址
     * @return ResponseEntity
     */
    @PostMapping("/startupServer")
    public ResponseEntity startupServer(String clusterNode) {
        List<ClusterNode> nodes = this.clusterContainer.getAllNodes();
        for (ClusterNode node : nodes) {
            if (node.getAddress().equals(clusterNode) && !node.isAlive()) {
                node.setAlive(true);
                node.resetConnectFailedCounter();
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body("ok");
    }
}
