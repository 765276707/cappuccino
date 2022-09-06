package com.github.xzb617.cappuccino.server.service;

import com.github.xzb617.cappuccino.server.base.TextCondition;
import com.github.xzb617.cappuccino.server.domain.condition.UserCondition;
import com.github.xzb617.cappuccino.server.domain.dto.ModifyPasswordDTO;
import com.github.xzb617.cappuccino.server.domain.dto.UserDTO;
import com.github.xzb617.cappuccino.server.domain.entity.User;

import java.util.List;

public interface UserService {
    User getById(Long id);

    User getByName(String username);

    List<User> getList(UserCondition condition);

    void save(UserDTO dto);

    void update(UserDTO dto);

    void deleteById(List<Long> ids);

    void resetPassword(Long id);

    void updateSelf(UserDTO dto);

    void updatePassword(ModifyPasswordDTO dto);
}
