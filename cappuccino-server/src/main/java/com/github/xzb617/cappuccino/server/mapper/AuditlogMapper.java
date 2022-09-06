package com.github.xzb617.cappuccino.server.mapper;

import com.github.xzb617.cappuccino.server.domain.condition.AuditlogCondition;
import com.github.xzb617.cappuccino.server.domain.entity.Auditlog;
import com.github.xzb617.cappuccino.server.domain.vo.AuditlogVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface AuditlogMapper extends Mapper<Auditlog> {

    List<AuditlogVO> selectList(@Param("condition") AuditlogCondition condition);

    List<Auditlog> selectRecentlyList(@Param("clientId") Long clientId, @Param("size") int size);

}