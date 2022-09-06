package com.github.xzb617.cappuccino.server.domain.dto;

import com.github.xzb617.cappuccino.server.validation.group.Insert;
import com.github.xzb617.cappuccino.server.validation.group.Update;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ConfigDTO {

    @NotNull(message = "配置编号不能为空", groups = {Update.class})
    private Long id;

    @NotNull(message = "客户端编号不能为空", groups = {Insert.class, Update.class})
    private Long clientId;

    private String content;

    @Length(max = 150, message = "配置描述长度不能超过150字", groups = {Insert.class, Update.class})
    private String description;

    @NotBlank(message = "文件类型不能为空", groups = {Insert.class, Update.class})
    private String fileExtension;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }
}
