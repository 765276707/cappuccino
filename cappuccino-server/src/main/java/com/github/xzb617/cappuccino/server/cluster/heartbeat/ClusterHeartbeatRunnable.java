package com.github.xzb617.cappuccino.server.cluster.heartbeat;

import com.github.xzb617.cappuccino.server.cluster.ClusterContainer;
import com.github.xzb617.cappuccino.server.cluster.ClusterNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * 集群节点心跳检测业务
 */
public class ClusterHeartbeatRunnable implements Runnable {

    private final Logger LOGGER = LoggerFactory.getLogger(ClusterHeartbeatRunnable.class);

    /**
     * 失败重试次数，达到此次数将服务状态设为不可用（DOWN）
     */
    private final static int RETRY_FAIL_COUNT = 3;

    private final ClusterContainer clusterContainer;
    private final RestTemplate restTemplate;

    public ClusterHeartbeatRunnable(ClusterContainer clusterContainer, RestTemplate restTemplate) {
        this.clusterContainer = clusterContainer;
        this.restTemplate = restTemplate;
    }

    @Override
    public void run() {
        List<ClusterNode> allNodes = this.clusterContainer.getAliveNodes(false);
        for (ClusterNode clusterNode : allNodes) {
            this.ping(clusterNode);
        }
    }

    private void ping(ClusterNode clusterNode) {
        try {
            boolean result = this.doGet(clusterNode.getAddress());
            if (result) {
                if (!clusterNode.isAlive()) {
                    if (LOGGER.isWarnEnabled()) {
                        LOGGER.info("Unavailable service node [{}] ping successfully again and has been set to available status", clusterNode.getAddress());
                    }
                    clusterNode.setAlive(true);
                    // 重置失败计数器
                    clusterNode.resetConnectFailedCounter();
                }
            } else {
                // 计算失败
                this.handleFailedPing(clusterNode);
            }
        } catch (Throwable e) {
            if (LOGGER.isErrorEnabled()) {
                LOGGER.error("Unable to connect to the server normally, may be due to " + e.getMessage(), e);
            }
            // 计算失败
            this.handleFailedPing(clusterNode);
        }
    }

    private boolean doGet(String url) {
        ResponseEntity<String> resp = this.restTemplate.getForEntity(url + "/cluster/ping", String.class);
        return resp.getStatusCode()== HttpStatus.OK && "pong".equals(resp.getBody());
    }

    /**
     * ping或连接失败时更新服务节点数据
     * @param clusterNode 服务节点
     */
    private void handleFailedPing(ClusterNode clusterNode) {
        // 只有当服务节点状态为可用时（UP），才需要进行失败计数操作
        if (clusterNode.isAlive()) {
            // 次数先取出后加1
            int failConnectCount = clusterNode.getConnectFailedCounter().getAndIncrement();
            if (failConnectCount >= RETRY_FAIL_COUNT) {
                if (LOGGER.isWarnEnabled()) {
                    LOGGER.warn("Cluster node [{}] ping failed for more than 3 times and has been set to unavailable status", clusterNode.getAddress());
                }
                clusterNode.setAlive(false);
                clusterNode.resetConnectFailedCounter();
            }
        }
    }
}
