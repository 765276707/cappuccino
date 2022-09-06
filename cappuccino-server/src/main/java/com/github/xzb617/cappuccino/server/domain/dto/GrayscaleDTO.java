package com.github.xzb617.cappuccino.server.domain.dto;

import com.github.xzb617.cappuccino.server.validation.group.Insert;
import com.github.xzb617.cappuccino.server.validation.group.Update;

import javax.validation.constraints.NotNull;

public class GrayscaleDTO {

    private Long id;

    @NotNull(message = "客户端编号不能为空", groups = {Insert.class, Update.class})
    private Long clientId;

    private String description;

    private String sign;

    private String rules;

    @NotNull(message = "客户端编号不能为空", groups = {Insert.class, Update.class})
    private String fileExtension;

    private String content;

    private Integer configVersion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getRules() {
        return rules;
    }

    public void setRules(String rules) {
        this.rules = rules;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getConfigVersion() {
        return configVersion;
    }

    public void setConfigVersion(Integer configVersion) {
        this.configVersion = configVersion;
    }
}
