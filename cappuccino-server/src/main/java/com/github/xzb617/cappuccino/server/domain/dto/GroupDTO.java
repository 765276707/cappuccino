package com.github.xzb617.cappuccino.server.domain.dto;

import com.github.xzb617.cappuccino.server.validation.group.Insert;
import com.github.xzb617.cappuccino.server.validation.group.Update;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class GroupDTO {

    @NotNull(message = "分组编号不能为空", groups = {Update.class})
    private Long id;

    @NotBlank(message = "分组名称不能为空", groups = {Insert.class, Update.class})
    @Length(max = 50, message = "分组名称长度不能超过50个字", groups = {Insert.class, Update.class})
    private String groupName;

    @Length(max = 150, message = "分组描述长度不能超过150个字", groups = {Insert.class, Update.class})
    private String groupDesc;

    @Min(value = 1, message = "排序号不小于1", groups = {Insert.class, Update.class})
    private Integer orderNum;

    private Boolean enableWrite;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupDesc() {
        return groupDesc;
    }

    public void setGroupDesc(String groupDesc) {
        this.groupDesc = groupDesc;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public Boolean getEnableWrite() {
        return enableWrite;
    }

    public void setEnableWrite(Boolean enableWrite) {
        this.enableWrite = enableWrite;
    }
}
