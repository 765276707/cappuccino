package com.github.xzb617.cappuccino.server.controller;

import com.github.xzb617.cappuccino.server.base.AjaxResponse;
import com.github.xzb617.cappuccino.server.domain.entity.Environment;
import com.github.xzb617.cappuccino.server.domain.vo.ElSelectOption;
import com.github.xzb617.cappuccino.server.service.EnvironmentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/environment")
@RestController
public class EnvironmentController {

    private final EnvironmentService environmentService;

    public EnvironmentController(EnvironmentService environmentService) {
        this.environmentService = environmentService;
    }

    @GetMapping("/getList")
    public AjaxResponse getList(Long excludeId) {
        List<Environment> list = this.environmentService.getList(excludeId);
        List<ElSelectOption> options = list.stream()
                .map(env -> new ElSelectOption(env.getId(), env.getEnvName()))
                .collect(Collectors.toList());
        return AjaxResponse.success()
                .message("查询成功").data(options);
    }
}
