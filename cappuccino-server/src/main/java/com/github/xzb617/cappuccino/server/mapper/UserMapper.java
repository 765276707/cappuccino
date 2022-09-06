package com.github.xzb617.cappuccino.server.mapper;

import com.github.xzb617.cappuccino.server.domain.condition.UserCondition;
import com.github.xzb617.cappuccino.server.domain.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface UserMapper extends Mapper<User> {

    long countByUsername(@Param("username") String username);

    long countByPhoneNumber(@Param("phoneNumber") String phoneNumber);

    User selectByUsername(@Param("username") String username);

    List<User> selectList(@Param("condition") UserCondition condition);

    void deleteByIds(@Param("ids") List<Long> ids);

}