package com.github.xzb617.cappuccino.http.entity;

/**
 * Http请求的响应
 * @author xzb
 */
public class RpcResponse {

    /**
     * 返回的状态码
     */
    private int httpStatus;
    /**
     * 返回的数据
     */
    private String httpEntity;

    public RpcResponse() {
    }

    public RpcResponse(int httpStatus, String httpEntity) {
        this.httpStatus = httpStatus;
        this.httpEntity = httpEntity;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(int httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getHttpEntity() {
        return httpEntity;
    }

    public void setHttpEntity(String httpEntity) {
        this.httpEntity = httpEntity;
    }

}
