package com.github.xzb617.cappuccino.server.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.xzb617.cappuccino.server.base.AjaxResponse;
import com.github.xzb617.cappuccino.server.base.Page;
import com.github.xzb617.cappuccino.server.base.PageCondition;
import com.github.xzb617.cappuccino.server.base.PageData;
import com.github.xzb617.cappuccino.server.domain.condition.ReleaseCondition;
import com.github.xzb617.cappuccino.server.domain.entity.Release;
import com.github.xzb617.cappuccino.server.domain.vo.ReleaseVO;
import com.github.xzb617.cappuccino.server.service.ReleaseService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/release")
@RestController
public class ReleaseController {

    private final ReleaseService releaseService;

    public ReleaseController(ReleaseService releaseService) {
        this.releaseService = releaseService;
    }

    @GetMapping("/getPage")
    public AjaxResponse getPageByClientId(ReleaseCondition condition, PageCondition page) {
        PageHelper.startPage(page);
        List<ReleaseVO> list = this.releaseService.getList(condition);
        PageInfo<ReleaseVO> pageInfo = new PageInfo<>(list);
        PageData pageData = Page.toData(pageInfo);
        return AjaxResponse.success()
                .message("查询成功").data(pageData);
    }

    @GetMapping("/rollback")
    public AjaxResponse rollback(Long id) {
        this.releaseService.rollback(id);
        return AjaxResponse.success().message("回滚成功");
    }

}
