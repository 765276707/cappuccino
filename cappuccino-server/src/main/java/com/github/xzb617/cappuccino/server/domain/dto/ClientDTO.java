package com.github.xzb617.cappuccino.server.domain.dto;

import com.github.xzb617.cappuccino.server.validation.annotation.IsChinesePhoneNumber;
import com.github.xzb617.cappuccino.server.validation.group.Insert;
import com.github.xzb617.cappuccino.server.validation.regex.PatternPool;
import com.github.xzb617.cappuccino.server.validation.group.Update;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ClientDTO {

    @NotNull(message = "客户端编号不能为空", groups = {Update.class})
    private Long id;

    @NotBlank(message = "客户端名称不能为空", groups = {Insert.class, Update.class})
    @Length(max = 50, message = "客户端名称长度不能超过50个字", groups = {Insert.class, Update.class})
    private String clientName;

    @Length(max = 50, message = "客户端描述长度不能超过50个字", groups = {Insert.class, Update.class})
    private String clientDesc;

    @NotNull(message = "环境不能为空", groups = {Insert.class, Update.class})
    private Long envId;

    @NotNull(message = "分组不能为空", groups = {Insert.class, Update.class})
    private Long groupId;

    @Length(max = 50, message = "负责人名字长度不能超过50个字", groups = {Insert.class, Update.class})
    private String charger;

    @IsChinesePhoneNumber(message = "不合法的手机号码", groups = {Insert.class, Update.class})
    private String chargerPhoneNumber;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientDesc() {
        return clientDesc;
    }

    public void setClientDesc(String clientDesc) {
        this.clientDesc = clientDesc;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getEnvId() {
        return envId;
    }

    public void setEnvId(Long envId) {
        this.envId = envId;
    }

    public String getCharger() {
        return charger;
    }

    public void setCharger(String charger) {
        this.charger = charger;
    }

    public String getChargerPhoneNumber() {
        return chargerPhoneNumber;
    }

    public void setChargerPhoneNumber(String chargerPhoneNumber) {
        this.chargerPhoneNumber = chargerPhoneNumber;
    }
}
