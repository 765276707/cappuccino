package com.github.xzb617.cappuccino.server.mapper;

import com.github.xzb617.cappuccino.server.constant.ResourceType;
import com.github.xzb617.cappuccino.server.domain.dto.AssignDTO;
import com.github.xzb617.cappuccino.server.domain.entity.Relation;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface RelationMapper extends Mapper<Relation> {

    void deleteByResource(@Param("resourceId") Long resourceId, @Param("resourceType") String resourceType);

    long countBy(@Param("userId") Long userId, @Param("resourceId") Long resourceId, @Param("resourceType") String resourceType);

    List<Long> selectUserIdsByResource(@Param("resourceId") Long resourceId, @Param("resourceType") String resourceType);
}