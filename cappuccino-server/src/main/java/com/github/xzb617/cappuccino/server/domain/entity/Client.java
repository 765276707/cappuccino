package com.github.xzb617.cappuccino.server.domain.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "clients")
public class Client {

    @Id
    @GeneratedValue(generator = "JDBC",strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "client_name")
    private String clientName;

    @Column(name = "client_desc")
    private String clientDesc;

    @Column(name = "env_id")
    private Long envId;

    @Column(name = "env_name")
    private String envName;

    @Column(name = "group_id")
    private Long groupId;

    @Column(name = "group_name")
    private String groupName;

    private String charger;

    @Column(name = "charger_phone_number")
    private String chargerPhoneNumber;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "create_user")
    private String createUser;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "update_user")
    private String updateUser;

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
     * @return client_name
     */
    public String getClientName() {
        return clientName;
    }

    /**
     * @param clientName
     */
    public void setClientName(String clientName) {
        this.clientName = clientName == null ? null : clientName.trim();
    }

    /**
     * @return client_desc
     */
    public String getClientDesc() {
        return clientDesc;
    }

    /**
     * @param clientDesc
     */
    public void setClientDesc(String clientDesc) {
        this.clientDesc = clientDesc == null ? null : clientDesc.trim();
    }

    /**
     * @return env_id
     */
    public Long getEnvId() {
        return envId;
    }

    /**
     * @param envId
     */
    public void setEnvId(Long envId) {
        this.envId = envId;
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
     * @return group_id
     */
    public Long getGroupId() {
        return groupId;
    }

    /**
     * @param groupId
     */
    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    /**
     * @return group_name
     */
    public String getGroupName() {
        return groupName;
    }

    /**
     * @param groupName
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName == null ? null : groupName.trim();
    }

    /**
     * @return charger
     */
    public String getCharger() {
        return charger;
    }

    /**
     * @param charger
     */
    public void setCharger(String charger) {
        this.charger = charger == null ? null : charger.trim();
    }

    /**
     * @return charger_phone_number
     */
    public String getChargerPhoneNumber() {
        return chargerPhoneNumber;
    }

    /**
     * @param chargerPhoneNumber
     */
    public void setChargerPhoneNumber(String chargerPhoneNumber) {
        this.chargerPhoneNumber = chargerPhoneNumber == null ? null : chargerPhoneNumber.trim();
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
     * @return update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * @return update_user
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * @param updateUser
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", clientName='" + clientName + '\'' +
                ", clientDesc='" + clientDesc + '\'' +
                ", envId=" + envId +
                ", envName='" + envName + '\'' +
                ", groupId=" + groupId +
                ", groupName='" + groupName + '\'' +
                ", charger='" + charger + '\'' +
                ", chargerPhoneNumber='" + chargerPhoneNumber + '\'' +
                ", createTime=" + createTime +
                ", createUser='" + createUser + '\'' +
                ", updateTime=" + updateTime +
                ", updateUser='" + updateUser + '\'' +
                '}';
    }
}