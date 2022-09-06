package com.github.xzb617.cappuccino.commons.api;

/**
 * 客户端访问服务端的接口地址
 * @author xzb617
 */
public class ClientApi {

    /**
     * 长轮询监听服务端的本客户端的配置是否发生变化的接口
     * <p>1.如果有配置发生变化，返回 HttpStatus:200</p>
     * <p>2.如果没有新的配置内容，则返回 HttpStatus:204</p>
     */
    public final static String MONITOR_CONFIG = "/clients/monitorConfig";

    /**
     * 从服务端拉取客户端的配置接口
     */
    public final static String GET_CONFIG = "/clients/getConfig";

    /**
     * 心跳检测接口
     * <p>1.如果服务存活返回状态码为： 200, 返回内容为字符串 pong</p>
     * <p>2.其他情况均视为未存活状态</p>
     */
    public final static String PING = "/clients/ping";

    /**
     * 在程序正常退出时通知配置中心接口
     */
    public final static String SHUTDOWN = "/clients/shutdown";

}
