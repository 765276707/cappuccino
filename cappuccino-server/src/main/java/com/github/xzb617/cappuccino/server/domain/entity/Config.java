package com.github.xzb617.cappuccino.server.domain.entity;

import java.util.Date;
import javax.persistence.*;

public class Config {
    @Id
    private Long id;

    @Column(name = "client_id")
    private Long clientId;

    private String description;

    @Column(name = "file_extension")
    private String fileExtension;

    private String sign;

    private Integer version;

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