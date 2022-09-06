package com.github.xzb617.cappuccino.server.domain.entity;

import java.util.Date;
import javax.persistence.*;

public class Grayscale {
    @Id
    private Long id;

    @Column(name = "client_id")
    private Long clientId;

    /**
     * 灰度配置的格式必须与主线配置的格式一样
     */
    @Column(name = "file_extension")
    private String fileExtension;

    private String description;

    private String sign;

    /**
     * 灰度规则,最多支持10条规则
     */
    private String rules;

    /**
     * 灰度版本
     */
    private Integer version;

    /**
     * 来源主线配置的版本
     */
    @Column(name = "config_version")
    private Integer configVersion;

    /**
     * 发布时间
     */
    @Column(name = "release_time")
    private Date releaseTime;

    /**
     * 发布人
     */
    @Column(name = "release_user")
    private String releaseUser;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "create_user")
    private String createUser;

    private String content;

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return client_id
     */
    public Long getClientId() {
        return clientId;
    }

    /**
     * @param clientId
     */
    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    /**
     * 获取灰度配置的格式必须与主线配置的格式一样
     *
     * @return file_extension - 灰度配置的格式必须与主线配置的格式一样
     */
    public String getFileExtension() {
        return fileExtension;
    }

    /**
     * 设置灰度配置的格式必须与主线配置的格式一样
     *
     * @param fileExtension 灰度配置的格式必须与主线配置的格式一样
     */
    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension == null ? null : fileExtension.trim();
    }

    /**
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description
     */
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    /**
     * @return sign
     */
    public String getSign() {
        return sign;
    }

    /**
     * @param sign
     */
    public void setSign(String sign) {
        this.sign = sign == null ? null : sign.trim();
    }

    /**
     * 获取灰度规则,最多支持10条规则
     *
     * @return rules - 灰度规则,最多支持10条规则
     */
    public String getRules() {
        return rules;
    }

    /**
     * 设置灰度规则,最多支持10条规则
     *
     * @param rules 灰度规则,最多支持10条规则
     */
    public void setRules(String rules) {
        this.rules = rules == null ? null : rules.trim();
    }

    /**
     * 获取灰度版本
     *
     * @return version - 灰度版本
     */
    public Integer getVersion() {
        return version;
    }

    /**
     * 设置灰度版本
     *
     * @param version 灰度版本
     */
    public void setVersion(Integer version) {
        this.version = version;
    }

    /**
     * 获取来源主线配置的版本
     *
     * @return config_version - 来源主线配置的版本
     */
    public Integer getConfigVersion() {
        return configVersion;
    }

    /**
     * 设置来源主线配置的版本
     *
     * @param configVersion 来源主线配置的版本
     */
    public void setConfigVersion(Integer configVersion) {
        this.configVersion = configVersion;
    }

    /**
     * 获取发布时间
     *
     * @return release_time - 发布时间
     */
    public Date getReleaseTime() {
        return releaseTime;
    }

    /**
     * 设置发布时间
     *
     * @param releaseTime 发布时间
     */
    public void setReleaseTime(Date releaseTime) {
        this.releaseTime = releaseTime;
    }

    /**
     * 获取发布人
     *
     * @return release_user - 发布人
     */
    public String getReleaseUser() {
        return releaseUser;
    }

    /**
     * 设置发布人
     *
     * @param releaseUser 发布人
     */
    public void setReleaseUser(String releaseUser) {
        this.releaseUser = releaseUser == null ? null : releaseUser.trim();
    }

    /**
     * @return create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return create_user
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * @param createUser
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    /**
     * @return content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}