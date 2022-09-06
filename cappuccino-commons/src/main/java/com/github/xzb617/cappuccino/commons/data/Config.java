package com.github.xzb617.cappuccino.commons.data;

import com.github.xzb617.cappuccino.commons.enums.FileExtension;

/**
 * 客户端配置
 * @author xzb617
 */
public class Config {

    /**
     * 配置内容是否加密
     */
    private boolean encrypted = false;
    /**
     * 对此加密密钥，由RSA私钥加密后的AES密钥，客户端会获取一个服务端分发的RSA公钥用来解密该项
     */
    private String secret;
    /**
     * 配置内容的签名，统一暂时默认使用 MD5
     */
    private String sign;
    /**
     * 配置内容
     */
    private String content;
    /**
     * 文件格式
     */
    private FileExtension fileExtension;

    public boolean isEncrypted() {
        return encrypted;
    }

    public void setEncrypted(boolean encrypted) {
        this.encrypted = encrypted;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public FileExtension getFileExtension() {
        return fileExtension;
    }

    public void setFileExtension(FileExtension fileExtension) {
        this.fileExtension = fileExtension;
    }
}
