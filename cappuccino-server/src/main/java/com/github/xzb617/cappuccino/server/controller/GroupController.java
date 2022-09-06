package com.github.xzb617.cappuccino.server.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.xzb617.cappuccino.server.aoplog.Logger;
import com.github.xzb617.cappuccino.server.base.*;
import com.github.xzb617.cappuccino.server.domain.dto.GroupDTO;
import com.github.xzb617.cappuccino.server.domain.entity.Group;
import com.github.xzb617.cappuccino.server.domain.vo.ElSelectOption;
import com.github.xzb617.cappuccino.server.security.perms.CheckRole;
import com.github.xzb617.cappuccino.server.security.perms.Role;
import com.github.xzb617.cappuccino.server.service.GroupService;
import com.github.xzb617.cappuccino.server.validation.group.Insert;
import com.github.xzb617.cappuccino.server.validation.group.Update;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Validated
@RequestMapping("/group")
@RestController
public class GroupController {

    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping("/getById")
    public AjaxResponse getById(@NotNull(message = "分组编号不能为空") Long id) {
        Group group = this.groupService.getById(id);
        return AjaxResponse.success()
                .message("查询成功").data(group);
    }

    @GetMapping("/getList")
    public AjaxResponse getList() {
        List<Group> list = this.groupService.getList(null);
        List<ElSelectOption> options = list.stream()
                .map(group -> new ElSelectOption(group.getId(), group.getGroupName()))
                .collect(Collectors.toList());
        return AjaxResponse.success()
                .message("查询成功").data(options);
    }

    @GetMapping("/getPage")
    public AjaxResponse getPage(TextCondition condition, PageCondition page) {
        PageHelper.startPage(page);
        List<Group> list = this.groupService.getList(condition);
        PageInfo<Group> pageInfo = new PageInfo<>(list);
        PageData pageData = Page.toData(pageInfo);
        return AjaxResponse.success()
                .message("查询成功").data(pageData);
    }

    @PostMapping("/save")
    @CheckRole(Role.SUPER_ADMIN)
    @Logger(desc = "创建分组")
    public AjaxResponse save(@Validated({Insert.class}) @RequestBody GroupDTO dto) {
        this.groupService.save(dto);
        return AjaxResponse.success().message("添加成功");
    }

    @PutMapping("/update")
    @CheckRole(Role.SUPER_ADMIN)
    @Logger(desc = "编辑分组")
    public AjaxResponse update(@Validated({Update.class}) @RequestBody GroupDTO dto) {
        this.groupService.update(dto);
        return AjaxResponse.success().message("更新成功");
    }

    @DeleteMapping("/deleteById")
    @CheckRole(Role.SUPER_ADMIN)
    @Logger(desc = "删除分组")
    public AjaxResponse deleteById(@NotNull(message = "分组编号不能为空") Long id) {
        this.groupService.deleteById(id);
        return AjaxResponse.success().message("删除成功");
    }

}
