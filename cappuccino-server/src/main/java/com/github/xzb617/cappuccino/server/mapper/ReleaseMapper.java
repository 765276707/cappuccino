package com.github.xzb617.cappuccino.server.mapper;

import com.github.xzb617.cappuccino.server.domain.condition.ReleaseCondition;
import com.github.xzb617.cappuccino.server.domain.entity.Release;
import com.github.xzb617.cappuccino.server.domain.vo.ReleaseVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface ReleaseMapper extends Mapper<Release> {

    List<ReleaseVO> selectList(@Param("condition") ReleaseCondition condition, @Param("userId") Long userId);

    Integer selectLastVersion(@Param("clientId") Long clientId, @Param("releaseType") int releaseType);
}