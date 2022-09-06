package com.github.xzb617.cappuccino.server.service;

import com.github.xzb617.cappuccino.server.base.TextCondition;
import com.github.xzb617.cappuccino.server.domain.dto.GroupDTO;
import com.github.xzb617.cappuccino.server.domain.entity.Group;

import java.util.List;

public interface GroupService {

    Group getById(Long id);

    List<Group> getList(TextCondition condition);

    void save(GroupDTO dto);

    void update(GroupDTO dto);

    void deleteById(Long id);

}
