package com.github.xzb617.cappuccino.server.domain.dto;

import com.github.xzb617.cappuccino.server.validation.annotation.IsChinesePhoneNumber;
import com.github.xzb617.cappuccino.server.validation.annotation.IsEmailAddress;
import com.github.xzb617.cappuccino.server.validation.group.Insert;
import com.github.xzb617.cappuccino.server.validation.group.Update;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class UserDTO {

    @NotNull(message = "用户编号不能为空", groups = {Update.class})
    private Long id;

    @NotBlank(message = "用户名", groups = {Insert.class})
    @Length(max = 50, message = "用户名长度不允许超过50个字", groups = {Insert.class})
    private String username;

    @NotBlank(message = "真实姓名", groups = {Insert.class, Update.class})
    @Length(max = 50, message = "真实姓名长度不允许超过50个字", groups = {Insert.class, Update.class})
    private String realname;

    @NotBlank(message = "手机号码不能为空", groups = {Insert.class, Update.class})
    @IsChinesePhoneNumber(message = "不合法的手机号", groups = {Insert.class, Update.class})
    private String phoneNumber;

    @IsEmailAddress(message = "不合法的邮箱地址", groups = {Insert.class, Update.class})
    private String email;

    @Length(max = 50, message = "部门名称长度不允许超过50个字", groups = {Insert.class, Update.class})
    private String deptName;

    @Length(max = 50, message = "职位名称长度不允许超过50个字", groups = {Insert.class, Update.class})
    private String jobName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }
}
