package com.github.xzb617.cappuccino.server.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@ConfigurationProperties(prefix = ServerProperties.PREFIX)
public class ServerProperties {

    public final static String PREFIX = "spring.cloud.cappuccino.server";

    /**
     * 本机主机名，必须配置可以为ip或域名，但是必须与集群列表中的配置保持一致
     * 例如： clusterAddrList 中配置时ip，则本机名也须为ip
     */
    private String hostname = "http://127.0.0.1";

    /**
     * 是否开启集群模式， 默认：false
     */
    private Boolean clusterEnabled = false;

    /**
     * 集群的地址列表, 每个节点都必须部署一样，一般集群至少不少于三个节点
     */
    private List<String> clusterAddrList = new ArrayList<>();

    /**
     * 开启自我保护
     * <p>当服务集群中不可用节点比例小于阈值85%时，将给出提示<p/>
     */
    private Boolean clusterProtectionEnabled = false;

    /**
     * 集群间心跳检测间隔时间，单位: 毫秒，默认: 30秒
     */
    private Integer clusterHeartbeatInterval = 30000;

    /**
     * 是否开启加密
     */
    private Boolean encryptedEnabled = false;

    /**
     * 加密私钥（RSA256），开启加密时，该项目必须配置
     */
    private String encryptedRsaPrivateKey;

    /**
     * 客户端访问时是否需要进行认证
     */
    private Boolean clientAuthEnabled = false;

    /**
     * 客户端访问需要认证时的Key
     */
    private String clientAccessKey;

    /**
     * 客户端访问需要认证时的秘钥
     */
    private String clientAccessSecret;

    /**
     * Token 在请求头中 key
     */
    private String tokenHeader;

    /**
     * Token 秘钥（AES）
     */
    private String tokenSecret;

    /**
     * 初始密码
     */
    private String userInitPassword;

    /**
     * 对明文密码加密的秘钥, 默认是：空字符串，配置该项可增加用户密码的安全系数
     */
    private String passwordEncryptSecret = "";


    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public List<String> getClusterAddrList() {
        return clusterAddrList;
    }

    public void setClusterAddrList(List<String> clusterAddrList) {
        this.clusterAddrList = clusterAddrList;
    }

    public Boolean getClusterProtectionEnabled() {
        return clusterProtectionEnabled;
    }

    public void setClusterProtectionEnabled(Boolean clusterProtectionEnabled) {
        this.clusterProtectionEnabled = clusterProtectionEnabled;
    }

    public Integer getClusterHeartbeatInterval() {
        return clusterHeartbeatInterval;
    }

    public void setClusterHeartbeatInterval(Integer clusterHeartbeatInterval) {
        this.clusterHeartbeatInterval = clusterHeartbeatInterval;
    }

    public Boolean getEncryptedEnabled() {
        return encryptedEnabled;
    }

    public void setEncryptedEnabled(Boolean encryptedEnabled) {
        this.encryptedEnabled = encryptedEnabled;
    }

    public String getEncryptedRsaPrivateKey() {
        return encryptedRsaPrivateKey;
    }

    public void setEncryptedRsaPrivateKey(String encryptedRsaPrivateKey) {
        this.encryptedRsaPrivateKey = encryptedRsaPrivateKey;
    }

    public Boolean getClientAuthEnabled() {
        return clientAuthEnabled;
    }

    public void setClientAuthEnabled(Boolean clientAuthEnabled) {
        this.clientAuthEnabled = clientAuthEnabled;
    }

    public String getClientAccessKey() {
        return clientAccessKey;
    }

    public void setClientAccessKey(String clientAccessKey) {
        this.clientAccessKey = clientAccessKey;
    }

    public String getClientAccessSecret() {
        return clientAccessSecret;
    }

    public void setClientAccessSecret(String clientAccessSecret) {
        this.clientAccessSecret = clientAccessSecret;
    }

    public String getTokenHeader() {
        return tokenHeader;
    }

    public void setTokenHeader(String tokenHeader) {
        this.tokenHeader = tokenHeader;
    }

    public String getTokenSecret() {
        return tokenSecret;
    }

    public void setTokenSecret(String tokenSecret) {
        this.tokenSecret = tokenSecret;
    }

    public String getUserInitPassword() {
        return userInitPassword;
    }

    public void setUserInitPassword(String userInitPassword) {
        this.userInitPassword = userInitPassword;
    }

    public Boolean getClusterEnabled() {
        return clusterEnabled;
    }

    public void setClusterEnabled(Boolean clusterEnabled) {
        this.clusterEnabled = clusterEnabled;
    }

    public String getPasswordEncryptSecret() {
        return passwordEncryptSecret;
    }

    public void setPasswordEncryptSecret(String passwordEncryptSecret) {
        this.passwordEncryptSecret = passwordEncryptSecret;
    }
}
