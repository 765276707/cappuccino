package com.github.xzb617.cappuccino.server.mapper;

import com.github.xzb617.cappuccino.server.base.TextAndTimeCondition;
import com.github.xzb617.cappuccino.server.domain.entity.Oplog;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface OplogMapper extends Mapper<Oplog> {

    /**
     * 查询操作日志列表
     * @param condition 查询条件
     * @return List
     */
    List<Oplog> selectList(@Param("condition") TextAndTimeCondition condition);

}