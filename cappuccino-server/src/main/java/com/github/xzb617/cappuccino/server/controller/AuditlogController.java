package com.github.xzb617.cappuccino.server.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.xzb617.cappuccino.server.base.AjaxResponse;
import com.github.xzb617.cappuccino.server.base.Page;
import com.github.xzb617.cappuccino.server.base.PageCondition;
import com.github.xzb617.cappuccino.server.base.PageData;
import com.github.xzb617.cappuccino.server.domain.condition.AuditlogCondition;
import com.github.xzb617.cappuccino.server.domain.vo.AuditlogVO;
import com.github.xzb617.cappuccino.server.security.perms.CheckRole;
import com.github.xzb617.cappuccino.server.security.perms.Role;
import com.github.xzb617.cappuccino.server.service.AuditlogService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/auditlog")
@RestController
public class AuditlogController {

    private final AuditlogService auditlogService;

    public AuditlogController(AuditlogService auditlogService) {
        this.auditlogService = auditlogService;
    }

    @GetMapping("/getPage")
    @CheckRole(Role.SUPER_ADMIN)
    public AjaxResponse getPage(AuditlogCondition condition, PageCondition page) {
        PageHelper.startPage(page);
        List<AuditlogVO> list = this.auditlogService.getList(condition);
        PageInfo<AuditlogVO> pageInfo = new PageInfo<>(list);
        PageData pageData = Page.toData(pageInfo);
        return AjaxResponse.success()
                .message("查询成功").data(pageData);
    }
}
