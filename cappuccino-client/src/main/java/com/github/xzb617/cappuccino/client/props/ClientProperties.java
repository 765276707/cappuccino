package com.github.xzb617.cappuccino.client.props;

import com.github.xzb617.cappuccino.commons.enums.FileExtension;
import com.github.xzb617.cappuccino.loadbalance.factory.LoadBalanceStrategy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 客户端的配置项目
 * @author xzb617
 */
@ConfigurationProperties(prefix = ClientProperties.PREFIX)
public class ClientProperties {

    public final static String PREFIX = "spring.cloud.cappuccino.client";

    /**
     * 服务端地址，默认地址为： http://127.0.0.1:8617
     */
    private String serverAddr = "http://127.0.0.1:8617";

    /**
     * 环境，默认： PUBLIC
     */
    private String env = "PUBLIC";

    /**
     * 分组，默认： DEFAULT
     */
    private String group = "DEFAULT";

    /**
     * 配置文件在服务端的名称，默认使用 spring.application.name 的值
     */
    @Value("${spring.application.name}")
    private String name = "";

    /**
     * 配置文件在服务端的拓展名，默认：Properties
     */
    private FileExtension fileExtension = FileExtension.PROPERTIES;

    /**
     * 灰度标识，默认使用server.port的值
     */
    @Value("${server.port}")
    private String grayscale = "";

    /**
     * 是否开启自动刷新，不开启的情况下只会在项目启动时加载一次配置中心的配置
     */
    private Boolean refreshEnabled = true;

    /**
     * 负载均衡策略（集群）
     */
    private LoadBalanceStrategy loadBalanceStrategy = LoadBalanceStrategy.ROUND_ROBIN;

    /**
     * 编码
     */
    private String encode = "UTF-8";

    /**
     * 请求超时，单位：毫秒
     */
    private Integer timeout = 100000;

    /**
     * 是否开启加密
     */
//    private Boolean encryptedEnabled = false;

    /**
     * 加密公钥，开启加密时，该项目必须配置
     */
//    private String encryptedRsaPublicKey;

    /**
     * 访问服务端的Key
     */
    private String accessKey;

    /**
     * 访问服务端的秘钥
     */
    private String accessSecret;

    public String getServerAddr() {
        return serverAddr;
    }

    public void setServerAddr(String serverAddr) {
        this.serverAddr = serverAddr;
    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FileExtension getFileExtension() {
        return fileExtension;
    }

    public void setFileExtension(FileExtension fileExtension) {
        this.fileExtension = fileExtension;
    }

    public String getGrayscale() {
        return grayscale;
    }

    public void setGrayscale(String grayscale) {
        this.grayscale = grayscale;
    }

    public Boolean getRefreshEnabled() {
        return refreshEnabled;
    }

    public void setRefreshEnabled(Boolean refreshEnabled) {
        this.refreshEnabled = refreshEnabled;
    }

    public LoadBalanceStrategy getLoadBalanceStrategy() {
        return loadBalanceStrategy;
    }

    public void setLoadBalanceStrategy(LoadBalanceStrategy loadBalanceStrategy) {
        this.loadBalanceStrategy = loadBalanceStrategy;
    }

    public String getEncode() {
        return encode;
    }

    public void setEncode(String encode) {
        this.encode = encode;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

//    public Boolean getEncryptedEnabled() {
//        return encryptedEnabled;
//    }
//
//    public void setEncryptedEnabled(Boolean encryptedEnabled) {
//        this.encryptedEnabled = encryptedEnabled;
//    }
//
//    public String getEncryptedRsaPublicKey() {
//        return encryptedRsaPublicKey;
//    }
//
//    public void setEncryptedRsaPublicKey(String encryptedRsaPublicKey) {
//        this.encryptedRsaPublicKey = encryptedRsaPublicKey;
//    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getAccessSecret() {
        return accessSecret;
    }

    public void setAccessSecret(String accessSecret) {
        this.accessSecret = accessSecret;
    }
}
