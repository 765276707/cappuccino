package com.github.xzb617.cappuccino.server.mapper;

import com.github.xzb617.cappuccino.commons.data.Meta;
import com.github.xzb617.cappuccino.server.domain.condition.ClientCondition;
import com.github.xzb617.cappuccino.server.domain.entity.Client;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface ClientMapper extends Mapper<Client> {

    Client selectById(@Param("id") Long id);

    Client selectByMeta(@Param("meta") Meta meta);

    List<Client> selectList(@Param("condition") ClientCondition condition, @Param("userId") Long userId);

    long countBy(@Param("clientName") String clientName, @Param("envId") Long envId,
                 @Param("groupId") Long groupId, @Param("excludeId") Long excludeId);

    List<Client> selectByGroupId(@Param("groupId") Long groupId);

}