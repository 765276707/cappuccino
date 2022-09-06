package com.github.xzb617.cappuccino.server.controller;

import com.github.xzb617.cappuccino.server.base.AjaxResponse;
import com.github.xzb617.cappuccino.server.cluster.ClusterContainer;
import com.github.xzb617.cappuccino.server.cluster.ClusterNode;
import com.github.xzb617.cappuccino.server.domain.vo.ClusterInfo;
import com.github.xzb617.cappuccino.server.properties.ServerProperties;
import org.apache.catalina.Cluster;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/clusters")
@RestController
public class ClustersController {

    private final ClusterContainer clusterContainer;
    private final ServerProperties serverProperties;

    public ClustersController(ClusterContainer clusterContainer, ServerProperties serverProperties) {
        this.clusterContainer = clusterContainer;
        this.serverProperties = serverProperties;
    }

    @GetMapping("/getInfo")
    public AjaxResponse getInfo() {
        if (!serverProperties.getClusterEnabled()) {
            return AjaxResponse.success().message("查询成功")
                            .data(new ClusterInfo(false));
        }
        List<ClusterNode> clusters = this.clusterContainer.getAllNodes();
        return AjaxResponse.success().message("查询成功")
                            .data(new ClusterInfo(true, clusters));
    }

}
