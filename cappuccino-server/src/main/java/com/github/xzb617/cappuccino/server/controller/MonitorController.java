package com.github.xzb617.cappuccino.server.controller;

import com.github.xzb617.cappuccino.commons.utils.MetaUtil;
import com.github.xzb617.cappuccino.server.base.AjaxResponse;
import com.github.xzb617.cappuccino.server.clients.ClientsContainer;
import com.github.xzb617.cappuccino.server.cluster.ClusterReceiver;
import com.github.xzb617.cappuccino.server.domain.entity.Client;
import com.github.xzb617.cappuccino.server.domain.entity.Grayscale;
import com.github.xzb617.cappuccino.server.properties.ServerProperties;
import com.github.xzb617.cappuccino.server.service.ClientService;
import com.github.xzb617.cappuccino.server.service.GrayscaleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@RequestMapping("/monitor")
@RestController
public class MonitorController {

    private final static Logger LOGGER = LoggerFactory.getLogger(MonitorController.class);

    private final ServerProperties serverProperties;
    private final ClientService clientService;
    private final ClientsContainer clientsContainer;
    private final ClusterReceiver clusterReceiver;
    private final GrayscaleService grayscaleService;


    public MonitorController(ServerProperties serverProperties, ClientService clientService, ClientsContainer clientsContainer, ClusterReceiver clusterReceiver, GrayscaleService grayscaleService) {
        this.serverProperties = serverProperties;
        this.clientService = clientService;
        this.clientsContainer = clientsContainer;
        this.clusterReceiver = clusterReceiver;
        this.grayscaleService = grayscaleService;
    }

    @GetMapping("getClientInstances")
    public AjaxResponse getClientInstances(Long clientId, Boolean isGrayscale) {
        // 查询客户端
        Client client = this.clientService.getById(clientId);
        if (client == null) {
            return AjaxResponse.failure("该客户端不存在或已被删除");
        }
        String clientKey = MetaUtil.getClientKey(client.getEnvName(), client.getGroupName(), client.getClientName());
        // 查询本节点下的监听实例
        Set<String> instanceKeys = this.clientsContainer.getInstanceKeys(clientKey);
        if (instanceKeys == null) {
            instanceKeys = new HashSet<>();
        }

        // 集群模式下从其他节点查询监听实例
        if (serverProperties.getClusterEnabled()) {
            Set<String> monitorInstances = this.clusterReceiver.getMonitorInstances(clientKey);
            if (!monitorInstances.isEmpty()) {
                instanceKeys.addAll(monitorInstances);
            }
        }
        // 是否包含灰度的监听实例
        Grayscale grayscale = this.grayscaleService.getByClientId(clientId);
        if (grayscale!=null && !StringUtils.isEmpty(grayscale.getRules())) {
            String rules = grayscale.getRules();
            if (Boolean.TRUE.equals(isGrayscale)) {
                Set<String> collect = instanceKeys.stream().filter(rules::contains).collect(Collectors.toSet());
                return AjaxResponse.success().message("灰度配置监听实例").data(collect);
            } else {
                Set<String> collect = instanceKeys.stream().filter(ik -> !rules.contains(ik)).collect(Collectors.toSet());
                return AjaxResponse.success().message("主配置监听实例").data(collect);
            }
        }
        return AjaxResponse.success().message("主配置监听实例").data(instanceKeys);
    }
}
