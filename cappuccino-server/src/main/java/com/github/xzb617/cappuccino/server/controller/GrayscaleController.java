package com.github.xzb617.cappuccino.server.controller;

import com.github.xzb617.cappuccino.server.base.AjaxResponse;
import com.github.xzb617.cappuccino.server.domain.dto.GrayscaleDTO;
import com.github.xzb617.cappuccino.server.service.GrayscaleService;
import com.github.xzb617.cappuccino.server.validation.group.Update;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/grayscale")
@RestController
public class GrayscaleController {

    private final GrayscaleService grayscaleService;

    public GrayscaleController(GrayscaleService grayscaleService) {
        this.grayscaleService = grayscaleService;
    }

    @PutMapping("/release")
    public AjaxResponse release(@Validated(Update.class) @RequestBody GrayscaleDTO dto) {
        this.grayscaleService.release(dto);
        return AjaxResponse.success().message("发布成功");
    }

    @GetMapping("/releaseFull")
    public AjaxResponse releaseFull(Long id) {
        this.grayscaleService.releaseFull(id);
        return AjaxResponse.success().message("发布成功");
    }

    @DeleteMapping("/deleteById")
    public AjaxResponse deleteById(Long id) {
        this.grayscaleService.deleteById(id);
        return AjaxResponse.success().message("取消灰度成功");
    }
}
