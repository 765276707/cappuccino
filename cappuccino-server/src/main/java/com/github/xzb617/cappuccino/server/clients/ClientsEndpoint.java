package com.github.xzb617.cappuccino.server.clients;

import com.github.xzb617.cappuccino.commons.api.ClientApi;
import com.github.xzb617.cappuccino.commons.data.Config;
import com.github.xzb617.cappuccino.commons.data.Meta;
import com.github.xzb617.cappuccino.commons.enums.FileExtension;
import com.github.xzb617.cappuccino.commons.utils.MetaUtil;
import com.github.xzb617.cappuccino.server.cluster.ClusterBroadcaster;
import com.github.xzb617.cappuccino.server.domain.entity.Client;
import com.github.xzb617.cappuccino.server.domain.entity.Grayscale;
import com.github.xzb617.cappuccino.server.service.ClientService;
import com.github.xzb617.cappuccino.server.service.ConfigService;
import com.github.xzb617.cappuccino.server.service.GrayscaleService;
import com.github.xzb617.cappuccino.server.utils.IpUtil;
import com.github.xzb617.cappuccino.server.utils.RuleMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import javax.servlet.http.HttpServletRequest;

/**
 * 客户端请求的端点
 * @author xzb617
 */
@RestController
public class ClientsEndpoint {

    /**
     * 日志
     */
    private final static Logger LOGGER = LoggerFactory.getLogger(ClientsEndpoint.class);
    /**
     * 超时时间
     */
    private final static long TIMEOUT = 60000L;

    private final ClientsContainer clientsContainer;
    private final ClientsTransmitter clientsTransmitter;
    private final ClusterBroadcaster clusterBroadcaster;
    private final ClientService clientService;
    private final ConfigService configService;
    private final GrayscaleService grayscaleService;


    public ClientsEndpoint(ClientsContainer clientsContainer, ClusterBroadcaster clusterBroadcaster, ClientsTransmitter clientsTransmitter, ClientService clientService, ConfigService configService, GrayscaleService grayscaleService) {
        this.clientsContainer = clientsContainer;
        this.clusterBroadcaster = clusterBroadcaster;
        this.clientsTransmitter = clientsTransmitter;
        this.clientService = clientService;
        this.configService = configService;
        this.grayscaleService = grayscaleService;
    }

    @GetMapping(ClientApi.MONITOR_CONFIG)
    public DeferredResult<ResponseEntity<Boolean>> monitorConfig(HttpServletRequest request, Meta meta) {
        // 解析基础客户端实例信息
        String ip = IpUtil.getClientIpAddr(request);
        String clientKey = MetaUtil.getClientKey(meta);
        String instanceKey = MetaUtil.getInstanceKey(ip, meta.getGrayscale());
//        LOGGER.info("配置中心收到了{}【{}】客户端实例的监听请求!", clientKey, instanceKey);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("配置中心收到了{}【{}】客户端实例的监听请求!", clientKey, instanceKey);
        }
        // 构建异步结果
        DeferredResult<ResponseEntity<Boolean>> deferredResult = new DeferredResult<>(TIMEOUT);
        deferredResult.onCompletion(() -> {
            this.clientsContainer.removeInstance(clientKey, instanceKey);
        });
        deferredResult.onTimeout(() -> {
            // 超时返回304状态，表示未有新的变化内容
            ResponseEntity<Boolean> errorResult = ResponseEntity.status(HttpStatus.NOT_MODIFIED).body(Boolean.FALSE);
            deferredResult.setErrorResult(errorResult);
            this.clientsContainer.removeInstance(clientKey, instanceKey);
        });
        deferredResult.onError((Throwable err) -> {
            this.clientsContainer.removeInstance(clientKey, instanceKey);
        });

        // 添加到长轮询结果容器中
        this.clientsContainer.store(clientKey, instanceKey, deferredResult);
        return deferredResult;
    }

    @GetMapping(ClientApi.GET_CONFIG)
    public ResponseEntity<Config> getConfig(HttpServletRequest request, Meta meta) {
        // 解析基础客户端实例信息
        String ip = IpUtil.getClientIpAddr(request);
        String clientKey = MetaUtil.getClientKey(meta);
        String instanceKey = MetaUtil.getInstanceKey(ip, meta.getGrayscale());
        // 查询客户端
        Client client = this.clientService.getByMeta(meta);
        if (client == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        // 查询灰度配置
        Grayscale grayscale = this.grayscaleService.getByClientId(client.getId());
        if (grayscale != null) {
            String rules = grayscale.getRules();
            if (RuleMatcher.match(rules, instanceKey)) {
                // 返回灰度配置
                Config result = new Config();
                result.setSign(grayscale.getSign());
                result.setContent(grayscale.getContent());
                result.setFileExtension(FileExtension.of(grayscale.getFileExtension()));
                return ResponseEntity.status(HttpStatus.OK).body(result);
            }
        }
        // 查询主配置
        com.github.xzb617.cappuccino.server.domain.entity.Config config = this.configService.getByClientId(client.getId());
        if (config == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        // 返回主配置
        Config result = new Config();
        result.setSign(config.getSign());
        result.setContent(config.getContent());
        result.setFileExtension(FileExtension.of(config.getFileExtension()));
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping(ClientApi.PING)
    public ResponseEntity ping() {
        return ResponseEntity.status(HttpStatus.OK).body("pong");
    }

    @DeleteMapping(ClientApi.SHUTDOWN)
    public ResponseEntity clientShutdown(HttpServletRequest request, Meta meta) {
        // 解析基础客户端实例信息
        String ip = IpUtil.getClientIpAddr(request);
        String clientKey = MetaUtil.getClientKey(meta);
        String instanceKey = MetaUtil.getInstanceKey(ip, meta.getGrayscale());
        // 直接响应给客户端
        this.clientsTransmitter.transmitInstance(clientKey, instanceKey);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
