package com.github.xzb617.cappuccino.server.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.xzb617.cappuccino.server.base.*;
import com.github.xzb617.cappuccino.server.domain.entity.Oplog;
import com.github.xzb617.cappuccino.server.security.perms.CheckRole;
import com.github.xzb617.cappuccino.server.security.perms.Role;
import com.github.xzb617.cappuccino.server.service.OplogService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/oplog")
@RestController
public class OplogController {

    private final OplogService oplogService;

    public OplogController(OplogService oplogService) {
        this.oplogService = oplogService;
    }

    @GetMapping("/getPage")
    @CheckRole(Role.SUPER_ADMIN)
    public AjaxResponse getPage(TextAndTimeCondition condition, PageCondition page) {
        PageHelper.startPage(page);
        List<Oplog> list = this.oplogService.getList(condition);
        PageInfo<Oplog> pageInfo = new PageInfo<>(list);
        PageData pageData = Page.toData(pageInfo);
        return AjaxResponse.success()
                    .message("查询成功").data(pageData);
    }
}
