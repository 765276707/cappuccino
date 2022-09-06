package com.github.xzb617.cappuccino.server.mapper;

import com.github.xzb617.cappuccino.server.domain.entity.Grayscale;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface GrayscaleMapper extends Mapper<Grayscale> {

    Grayscale selectByClientId(@Param("clientId") Long clientId);

}