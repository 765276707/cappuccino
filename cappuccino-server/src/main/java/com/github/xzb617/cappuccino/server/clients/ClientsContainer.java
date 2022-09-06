package com.github.xzb617.cappuccino.server.clients;

import org.springframework.web.context.request.async.DeferredResult;

import java.util.Map;
import java.util.Set;

/**
 * 存储客户端长轮询请求的容器接口
 * @author xzb617
 */
public interface ClientsContainer {

    /**
     * 保存长轮询请求结果
     * @param clientKey 客户端标识
     * @param instanceKey 实例标识
     * @param deferredResult 长轮询请求结果
     */
    public void store(String clientKey, String instanceKey, DeferredResult deferredResult);

    /**
     * 获取客户端下的所有实例长轮询请求
     * @param clientKey 客户端标识
     * @return Map
     */
    public Map<String, DeferredResult> getClient(String clientKey);

    /**
     * 获取实例的长轮询请求
     * @param clientKey 客户端标识
     * @param instanceKey 实例标识
     * @return DeferredResult
     */
    public DeferredResult getInstance(String clientKey, String instanceKey);

    /**
     * 获取客户端下的所有实例标识
     * @param clientKey 客户端标识
     * @return Set
     */
    public Set<String> getInstanceKeys(String clientKey);

    /**
     * 是否包含某个客户端
     * @param clientKey 客户端标识
     * @return boolean
     */
    public boolean existClient(String clientKey);

    /**
     * 是否包含某个客户的某个实例
     * @param clientKey 客户端标识
     * @param instanceKey 实例标识
     * @return boolean
     */
    public boolean existInstance(String clientKey, String instanceKey);

    /**
     * 移除客户端下所有实例长轮询请求结果
     * @param clientKey 客户端标识
     */
    public void removeClient(String clientKey);

    /**
     * 移除客户端下指定实例长轮询请求结果
     * @param clientKey 客户端标识
     * @param instanceKey 实例标识
     */
    public void removeInstance(String clientKey, String instanceKey);

}
