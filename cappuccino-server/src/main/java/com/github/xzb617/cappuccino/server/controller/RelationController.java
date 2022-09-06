package com.github.xzb617.cappuccino.server.controller;

import com.github.xzb617.cappuccino.server.aoplog.Logger;
import com.github.xzb617.cappuccino.server.base.AjaxResponse;
import com.github.xzb617.cappuccino.server.domain.dto.AssignDTO;
import com.github.xzb617.cappuccino.server.domain.vo.RelationVO;
import com.github.xzb617.cappuccino.server.security.perms.CheckRole;
import com.github.xzb617.cappuccino.server.security.perms.Role;
import com.github.xzb617.cappuccino.server.service.RelationService;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/relation")
@RestController
public class RelationController {

    private final RelationService relationService;

    public RelationController(RelationService relationService) {
        this.relationService = relationService;
    }

    @PutMapping("/assign")
    @CheckRole(Role.SUPER_ADMIN)
    @Logger(desc = "为管理员分配客户端权限")
    public AjaxResponse assign(@RequestBody AssignDTO assignDTO) {
        this.relationService.assign(assignDTO);
        return AjaxResponse.success().message("授权成功");
    }

    @GetMapping("/getRelations")
    public AjaxResponse getRelations(Long clientId) {
        RelationVO relationVO = this.relationService.getRelations(clientId);
        return AjaxResponse.success().message("查询成功").data(relationVO);
    }
}
