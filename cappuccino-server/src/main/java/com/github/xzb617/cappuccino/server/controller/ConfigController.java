package com.github.xzb617.cappuccino.server.controller;

import com.github.xzb617.cappuccino.server.base.AjaxResponse;
import com.github.xzb617.cappuccino.server.domain.dto.ConfigDTO;
import com.github.xzb617.cappuccino.server.service.ConfigService;
import com.github.xzb617.cappuccino.server.validation.group.Insert;
import com.github.xzb617.cappuccino.server.validation.group.Update;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

@Validated
@RequestMapping("/config")
@RestController
public class ConfigController {

    private final ConfigService configService;

    public ConfigController(ConfigService configService) {
        this.configService = configService;
    }

    @GetMapping("/getByClientId")
    public AjaxResponse getByClientId(@NotNull(message = "客户端编号不能为空") Long clientId) {
        Map<String, Object> data = new HashMap<>(4);
        data.put("client", null);
        data.put("config", null);
        data.put("grayscale", null);
        data.put("auditlog", null);
        return AjaxResponse.success()
                .message("查询成功").data(data);
    }

    @PostMapping("/save")
    public AjaxResponse save(@Validated({Insert.class}) @RequestBody ConfigDTO dto) {
        this.configService.save(dto);
        return AjaxResponse.success().message("发布成功");
    }

    @PutMapping("/update")
    public AjaxResponse update(@Validated({Update.class}) @RequestBody ConfigDTO dto) {
        this.configService.update(dto);
        return AjaxResponse.success().message("发布成功");
    }

    @DeleteMapping("/deleteById")
    public AjaxResponse deleteById(@NotNull(message = "配置编号不能为空") Long id) {
        this.configService.deleteById(id);
        return AjaxResponse.success().message("删除成功");
    }

}
