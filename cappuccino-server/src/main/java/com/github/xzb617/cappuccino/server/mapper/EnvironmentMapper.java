package com.github.xzb617.cappuccino.server.mapper;

import com.github.xzb617.cappuccino.server.domain.entity.Environment;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface EnvironmentMapper extends Mapper<Environment> {

    List<Environment> selectList(@Param("excludeId") Long excludeId);

}