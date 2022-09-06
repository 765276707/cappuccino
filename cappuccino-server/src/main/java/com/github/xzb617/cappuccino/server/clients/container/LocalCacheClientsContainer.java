package com.github.xzb617.cappuccino.server.clients.container;

import com.github.xzb617.cappuccino.server.clients.ClientsContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.*;

/**
 * 基于本地内存的长连接请求存储容器
 * @author xzb617
 */
@Component
public class LocalCacheClientsContainer implements ClientsContainer {

    /**
     * 存储容器，加锁
     */
    private final static Map<String, Map<String, DeferredResult>> CONTAINER = Collections.synchronizedMap(new HashMap<>());
    private final static Logger LOGGER = LoggerFactory.getLogger(LocalCacheClientsContainer.class);

    @Override
    public void store(String clientKey, String instanceKey, DeferredResult deferredResult) {
        Map<String, DeferredResult> instanceMap = CONTAINER.get(clientKey);
        if (instanceMap == null) {
            instanceMap = new HashMap<>();
        }
        instanceMap.put(instanceKey, deferredResult);
        CONTAINER.put(clientKey, instanceMap);
    }

    @Override
    public Map<String, DeferredResult> getClient(String clientKey) {
        return CONTAINER.get(clientKey);
    }

    @Override
    public DeferredResult getInstance(String clientKey, String instanceKey) {
        Map<String, DeferredResult> instanceMap = CONTAINER.get(clientKey);
        if (instanceMap == null) {
            return null;
        }
        return instanceMap.get(instanceKey);
    }

    @Override
    public Set<String> getInstanceKeys(String clientKey) {
        Map<String, DeferredResult> instanceMap = CONTAINER.get(clientKey);
        return instanceMap==null?new HashSet<>(0):instanceMap.keySet();
    }

    @Override
    public boolean existClient(String clientKey) {
        return CONTAINER.containsKey(clientKey);
    }

    @Override
    public boolean existInstance(String clientKey, String instanceKey) {
        Map<String, DeferredResult> instanceMap = CONTAINER.get(clientKey);
        if (instanceMap == null) {
            return false;
        }
        return instanceMap.containsKey(instanceKey);
    }

    @Override
    public void removeClient(String clientKey) {
        CONTAINER.remove(clientKey);
    }

    @Override
    public void removeInstance(String clientKey, String instanceKey) {
        Map<String, DeferredResult> instanceMap = CONTAINER.get(clientKey);
        if (instanceMap != null) {
            instanceMap.remove(instanceKey);
        }
    }
}
