package com.github.xzb617.cappuccino.server.domain.entity;

import javax.persistence.*;

public class Environment {
    @Id
    private Long id;

    @Column(name = "env_name")
    private String envName;

    @Column(name = "env_desc")
    private String envDesc;

    @Column(name = "order_num")
    private Integer orderNum;

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
     * @return env_name
     */
    public String getEnvName() {
        return envName;
    }

    /**
     * @param envName
     */
    public void setEnvName(String envName) {
        this.envName = envName == null ? null : envName.trim();
    }

    /**
     * @return env_desc
     */
    public String getEnvDesc() {
        return envDesc;
    }

    /**
     * @param envDesc
     */
    public void setEnvDesc(String envDesc) {
        this.envDesc = envDesc == null ? null : envDesc.trim();
    }

    /**
     * @return order_num
     */
    public Integer getOrderNum() {
        return orderNum;
    }

    /**
     * @param orderNum
     */
    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }
}