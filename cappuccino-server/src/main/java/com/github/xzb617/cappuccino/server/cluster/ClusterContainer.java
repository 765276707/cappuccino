package com.github.xzb617.cappuccino.server.cluster;

import com.github.xzb617.cappuccino.server.properties.ServerProperties;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 存储集群节点信息的容器
 * @author xzb617
 */
@Component("clusterContainer")
public class ClusterContainer {

    /**
     * 集群节点集合
     */
    private List<ClusterNode> clusterNodes;
    /**
     * 主机名
     */
    private String localHostAddr;

    public ClusterContainer(ServerProperties serverProperties, ConfigurableEnvironment configurableEnvironment) {
        this.clusterNodes = this.convertTo(serverProperties.getClusterAddrList());
        String serverPort = configurableEnvironment.getProperty("server.port");
        this.localHostAddr = serverProperties.getHostname() + ":" + serverPort;
    }

    /**
     * 获取可用节点
     * @param excludeSelfNode 是否排除本节点
     * @return List
     */
    public List<ClusterNode> getAliveNodes(boolean excludeSelfNode) {
        Stream<ClusterNode> stream = this.getAllNodes().stream().filter(ClusterNode::isAlive);
        if (excludeSelfNode) {
            stream = stream.filter(serverNode -> {
                return !this.localHostAddr.equals(serverNode.getAddress());
            });
        }
        return stream.collect(Collectors.toList());
    }

    /**
     * 获取所有节点
     * @return List
     */
    public List<ClusterNode> getAllNodes() {
        return this.clusterNodes;
    }

    /**
     * 将集群地址转成服务集合
     * @param serverAddrs 集群地址
     * @return
     */
    private List<ClusterNode> convertTo(List<String> serverAddrs) {
        List<ClusterNode> serverNodes = new ArrayList<>(serverAddrs.size());
        for (String serverAddr : serverAddrs) {
            serverNodes.add(new ClusterNode(serverAddr, true));
        }
        return serverNodes;
    }

    /**
     * 获取本节点地址
     * @return String
     */
    public String getLocalHostAddr() {
        return localHostAddr;
    }
}
