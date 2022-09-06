package com.github.xzb617.cappuccino.server.domain.entity;

import java.util.Date;
import javax.persistence.*;

public class Oplog {
    @Id
    private Long id;

    @Column(name = "req_uri")
    private String reqUri;

    /**
     * 操作的方法名
     */
    @Column(name = "op_method")
    private String opMethod;

    /**
     * 参数
     */
    @Column(name = "op_args")
    private String opArgs;

    /**
     * 操作描述
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
     * @return req_uri
     */
    public String getReqUri() {
        return reqUri;
    }

    /**
     * @param reqUri
     */
    public void setReqUri(String reqUri) {
        this.reqUri = reqUri == null ? null : reqUri.trim();
    }

    /**
     * 获取操作的方法名
     *
     * @return op_method - 操作的方法名
     */
    public String getOpMethod() {
        return opMethod;
    }

    /**
     * 设置操作的方法名
     *
     * @param opMethod 操作的方法名
     */
    public void setOpMethod(String opMethod) {
        this.opMethod = opMethod == null ? null : opMethod.trim();
    }

    /**
     * 获取参数
     *
     * @return op_args - 参数
     */
    public String getOpArgs() {
        return opArgs;
    }

    /**
     * 设置参数
     *
     * @param opArgs 参数
     */
    public void setOpArgs(String opArgs) {
        this.opArgs = opArgs == null ? null : opArgs.trim();
    }

    /**
     * 获取操作描述
     *
     * @return op_desc - 操作描述
     */
    public String getOpDesc() {
        return opDesc;
    }

    /**
     * 设置操作描述
     *
     * @param opDesc 操作描述
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
}