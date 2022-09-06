package com.github.xzb617.cappuccino.server.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.xzb617.cappuccino.server.aoplog.Logger;
import com.github.xzb617.cappuccino.server.base.*;
import com.github.xzb617.cappuccino.server.domain.condition.UserCondition;
import com.github.xzb617.cappuccino.server.domain.dto.ModifyPasswordDTO;
import com.github.xzb617.cappuccino.server.domain.dto.UserDTO;
import com.github.xzb617.cappuccino.server.domain.entity.User;
import com.github.xzb617.cappuccino.server.exception.ServiceException;
import com.github.xzb617.cappuccino.server.security.perms.CheckRole;
import com.github.xzb617.cappuccino.server.security.perms.Role;
import com.github.xzb617.cappuccino.server.service.UserService;
import com.github.xzb617.cappuccino.server.validation.group.Insert;
import com.github.xzb617.cappuccino.server.validation.group.Update;
import com.github.xzb617.cappuccino.server.validation.passay.PasswordChecker;
import com.github.xzb617.cappuccino.server.validation.passay.PasswordComplexity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.ValidationException;
import javax.validation.constraints.NotNull;
import java.util.List;

@RequestMapping("/user")
@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/getById")
    public AjaxResponse getById(@NotNull(message = "用户编号不能为空") Long id) {
        User user = this.userService.getById(id);
        return AjaxResponse.success()
                .message("查询成功").data(user);
    }

    @GetMapping("/getPage")
    public AjaxResponse getPage(UserCondition condition, PageCondition page) {
        PageHelper.startPage(page);
        List<User> list = this.userService.getList(condition);
        PageInfo<User> pageInfo = new PageInfo<>(list);
        PageData pageData = Page.toData(pageInfo);
        return AjaxResponse.success()
                .message("查询成功").data(pageData);
    }

    @Logger(desc = "创建用户")
    @PostMapping("/save")
    @CheckRole(Role.SUPER_ADMIN)
    public AjaxResponse save(@Validated({Insert.class}) @RequestBody UserDTO dto) {
        this.userService.save(dto);
        return AjaxResponse.success().message("添加成功");
    }

    @Logger(desc = "编辑用户")
    @PutMapping("/update")
    @CheckRole(Role.SUPER_ADMIN)
    public AjaxResponse update(@Validated({Update.class}) @RequestBody UserDTO dto) {
        this.userService.update(dto);
        return AjaxResponse.success().message("更新成功");
    }

    @Logger(desc = "删除用户")
    @PutMapping("/deleteById")
    @CheckRole(Role.SUPER_ADMIN)
    public AjaxResponse deleteById(@RequestBody List<Long> ids) {
        this.userService.deleteById(ids);
        return AjaxResponse.success().message("删除成功");
    }

    @Logger(desc = "重置用户密码")
    @GetMapping("/resetPassword")
    @CheckRole(Role.SUPER_ADMIN)
    public AjaxResponse resetPassword(@NotNull(message = "用户编号不能为空") Long id) {
        this.userService.resetPassword(id);
        return AjaxResponse.success().message("重置成功");
    }

    @Logger(desc = "修改登录用户资料")
    @PutMapping("/updateSelf")
    public AjaxResponse updateSelf(@Validated({Update.class}) @RequestBody UserDTO dto) {
        this.userService.updateSelf(dto);
        return AjaxResponse.success().message("修改成功");
    }

    @Logger(desc = "修改登录用户密码", shieldArgs = true)
    @PutMapping("/updatePassword")
    public AjaxResponse updatePassword(@Validated @RequestBody ModifyPasswordDTO dto) {
        // 校验重复
        if (!dto.getNewPassword().equals(dto.getRepPassword())) {
            throw new ValidationException("新密码与确认密码不一致");
        }
        if (dto.getOldPassword().equals(dto.getNewPassword())) {
            throw new ValidationException("新密码与旧密码重复");
        }
        // 校验强度
        PasswordComplexity medium = PasswordComplexity.MEDIUM;
        PasswordChecker.valid(dto.getNewPassword(), medium, medium.getErrorMessage());
        // 修改密码
        this.userService.updatePassword(dto);
        return AjaxResponse.success().message("修改成功");
    }
}
