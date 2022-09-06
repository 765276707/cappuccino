package com.github.xzb617.cappuccino.server.mapper;

import com.github.xzb617.cappuccino.server.base.TextCondition;
import com.github.xzb617.cappuccino.server.domain.entity.Group;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface GroupMapper extends Mapper<Group> {

    long countByName(@Param("groupName") String groupName, @Param("excludeId") Long excludeId);

    List<Group> selectList(@Param("condition") TextCondition condition);

}