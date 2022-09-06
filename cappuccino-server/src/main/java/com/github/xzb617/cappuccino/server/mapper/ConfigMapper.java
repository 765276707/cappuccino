package com.github.xzb617.cappuccino.server.mapper;

import com.github.xzb617.cappuccino.server.domain.entity.Config;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface ConfigMapper extends Mapper<Config> {

    Config selectByClientId(@Param("clientId") Long clientId);

}