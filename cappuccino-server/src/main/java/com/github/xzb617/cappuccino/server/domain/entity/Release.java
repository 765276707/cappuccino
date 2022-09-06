package com.github.xzb617.cappuccino.server.domain.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "releases")
public class Release {
    @Id
    private Long id;

    /**
     * 历史发布版本只关联客户端，只支持主线配置进行回滚
     */
    @Column(name = "client_id")
    private Long clientId;

    private String description;

    @Column(name = "file_extension")
    private String fileExtension;

    private String sign;

    private Integer version;

    @Column(name = "config_version")
    private Integer configVersion;

    private String rules;

    /**
     * 1：主配置，2：灰度配置
     */
    private Integer type;

    @Column(name = "release_time")
    private Date releaseTime;

    @Column(name = "release_user")
    private String releaseUser;

    /**
     * 该创建时间是快照的创建时间非配置的创建时间，回滚时配置的创建的时间应为回滚的时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 该创建用户是快照的创建用户非配置的创建时间，回滚时配置的创建者应为回滚的用户
     */
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
     * 获取历史发布版本只关联客户端，只支持主线配置进行回滚
     *
     * @return client_id - 历史发布版本只关联客户端，只支持主线配置进行回滚
     */
    public Long getClientId() {
        return clientId;
    }

    /**
     * 设置历史发布版本只关联客户端，只支持主线配置进行回滚
     *
     * @param clientId 历史发布版本只关联客户端，只支持主线配置进行回滚
     */
    public void setClientId(Long clientId) {
        this.clientId = clientId;
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
     * @return file_extension
     */
    public String getFileExtension() {
        return fileExtension;
    }

    /**
     * @param fileExtension
     */
    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension == null ? null : fileExtension.trim();
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
     * @return version
     */
    public Integer getVersion() {
        return version;
    }

    /**
     * @param version
     */
    public void setVersion(Integer version) {
        this.version = version;
    }

    /**
     * @return config_version
     */
    public Integer getConfigVersion() {
        return configVersion;
    }

    /**
     * @param configVersion
     */
    public void setConfigVersion(Integer configVersion) {
        this.configVersion = configVersion;
    }

    /**
     * @return rules
     */
    public String getRules() {
        return rules;
    }

    /**
     * @param rules
     */
    public void setRules(String rules) {
        this.rules = rules == null ? null : rules.trim();
    }

    /**
     * 获取1：主配置，2：灰度配置
     *
     * @return type - 1：主配置，2：灰度配置
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置1：主配置，2：灰度配置
     *
     * @param type 1：主配置，2：灰度配置
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * @return release_time
     */
    public Date getReleaseTime() {
        return releaseTime;
    }

    /**
     * @param releaseTime
     */
    public void setReleaseTime(Date releaseTime) {
        this.releaseTime = releaseTime;
    }

    /**
     * @return release_user
     */
    public String getReleaseUser() {
        return releaseUser;
    }

    /**
     * @param releaseUser
     */
    public void setReleaseUser(String releaseUser) {
        this.releaseUser = releaseUser == null ? null : releaseUser.trim();
    }

    /**
     * 获取该创建时间是快照的创建时间非配置的创建时间，回滚时配置的创建的时间应为回滚的时间
     *
     * @return create_time - 该创建时间是快照的创建时间非配置的创建时间，回滚时配置的创建的时间应为回滚的时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置该创建时间是快照的创建时间非配置的创建时间，回滚时配置的创建的时间应为回滚的时间
     *
     * @param createTime 该创建时间是快照的创建时间非配置的创建时间，回滚时配置的创建的时间应为回滚的时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取该创建用户是快照的创建用户非配置的创建时间，回滚时配置的创建者应为回滚的用户
     *
     * @return create_user - 该创建用户是快照的创建用户非配置的创建时间，回滚时配置的创建者应为回滚的用户
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * 设置该创建用户是快照的创建用户非配置的创建时间，回滚时配置的创建者应为回滚的用户
     *
     * @param createUser 该创建用户是快照的创建用户非配置的创建时间，回滚时配置的创建者应为回滚的用户
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