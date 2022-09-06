package com.github.xzb617.cappuccino.server.service;

import com.github.xzb617.cappuccino.server.base.TextAndTimeCondition;
import com.github.xzb617.cappuccino.server.domain.entity.Oplog;

import java.util.List;

public interface OplogService {

    /**
     * 查询操作日志列表
     * @param condition 查询条件
     * @return List
     */
    List<Oplog> getList(TextAndTimeCondition condition);

}
