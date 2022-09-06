package com.github.xzb617.cappuccino.server.domain.entity;

import java.util.Date;
import javax.persistence.*;

public class Auditlog {
    @Id
    private Long id;

    /**
     * 记录的客户端ID
     */
    @Column(name = "op_client_id")
    private Long opClientId;

    /**
     * 配置类型，1：主配置，2：灰度配置
     */
    @Column(name = "config_type")
    private Integer configType;

    /**
     * 操作类型
     */
    @Column(name = "op_type")
    private String opType;

    /**
     * 具体操作内容
     */
    @Column(name = "op_desc")
    private String opDesc;

    /**
     * 操作时间
     */
    @Column(name = "op_time")
    private Date opTime;

    /**
     * 操作者
     */
    @Column(name = "op_user")
    private String opUser;

    /**
     * 节点颜色，用来UI展示
     */
    private String color;

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
     * 获取记录的客户端ID
     *
     * @return op_client_id - 记录的客户端ID
     */
    public Long getOpClientId() {
        return opClientId;
    }

    /**
     * 设置记录的客户端ID
     *
     * @param opClientId 记录的客户端ID
     */
    public void setOpClientId(Long opClientId) {
        this.opClientId = opClientId;
    }

    /**
     * 获取配置类型，1：主配置，2：灰度配置
     *
     * @return config_type - 配置类型，1：主配置，2：灰度配置
     */
    public Integer getConfigType() {
        return configType;
    }

    /**
     * 设置配置类型，1：主配置，2：灰度配置
     *
     * @param configType 配置类型，1：主配置，2：灰度配置
     */
    public void setConfigType(Integer configType) {
        this.configType = configType;
    }

    /**
     * 获取操作类型
     *
     * @return op_type - 操作类型
     */
    public String getOpType() {
        return opType;
    }

    /**
     * 设置操作类型
     *
     * @param opType 操作类型
     */
    public void setOpType(String opType) {
        this.opType = opType == null ? null : opType.trim();
    }

    /**
     * 获取具体操作内容
     *
     * @return op_desc - 具体操作内容
     */
    public String getOpDesc() {
        return opDesc;
    }

    /**
     * 设置具体操作内容
     *
     * @param opDesc 具体操作内容
     */
    public void setOpDesc(String opDesc) {
        this.opDesc = opDesc == null ? null : opDesc.trim();
    }

    /**
     * 获取操作时间
     *
     * @return op_time - 操作时间
     */
    public Date getOpTime() {
        return opTime;
    }

    /**
     * 设置操作时间
     *
     * @param opTime 操作时间
     */
    public void setOpTime(Date opTime) {
        this.opTime = opTime;
    }

    /**
     * 获取操作者
     *
     * @return op_user - 操作者
     */
    public String getOpUser() {
        return opUser;
    }

    /**
     * 设置操作者
     *
     * @param opUser 操作者
     */
    public void setOpUser(String opUser) {
        this.opUser = opUser == null ? null : opUser.trim();
    }

    /**
     * 获取节点颜色，用来UI展示
     *
     * @return color - 节点颜色，用来UI展示
     */
    public String getColor() {
        return color;
    }

    /**
     * 设置节点颜色，用来UI展示
     *
     * @param color 节点颜色，用来UI展示
     */
    public void setColor(String color) {
        this.color = color == null ? null : color.trim();
    }
}