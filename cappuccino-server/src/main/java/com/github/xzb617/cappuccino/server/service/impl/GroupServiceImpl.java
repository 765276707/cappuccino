package com.github.xzb617.cappuccino.server.service.impl;

import com.github.xzb617.cappuccino.server.base.TextCondition;
import com.github.xzb617.cappuccino.server.domain.dto.GroupDTO;
import com.github.xzb617.cappuccino.server.domain.entity.Group;
import com.github.xzb617.cappuccino.server.exception.ServiceException;
import com.github.xzb617.cappuccino.server.mapper.GroupMapper;
import com.github.xzb617.cappuccino.server.security.SecurityCapability;
import com.github.xzb617.cappuccino.server.service.ClientService;
import com.github.xzb617.cappuccino.server.service.GroupService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class GroupServiceImpl extends SecurityCapability implements GroupService {

    private final GroupMapper groupMapper;
    private final ClientService clientService;

    public GroupServiceImpl(GroupMapper groupMapper, ClientService clientService) {
        this.groupMapper = groupMapper;
        this.clientService = clientService;
    }

    @Override
    public Group getById(Long id) {
        return this.groupMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Group> getList(TextCondition condition) {
        return this.groupMapper.selectList(condition);
    }

    @Override
    @Transactional
    public void save(GroupDTO dto) {
        // 判断分组名称是否已被使用
        long count = this.groupMapper.countByName(dto.getGroupName(), null);
        if (count > 0) {
            throw new ServiceException("分组名称已被使用");
        }
        // 新增分组
        Group group = new Group();
        BeanUtils.copyProperties(dto, group);
        group.setCreateTime(new Date());
        group.setCreateUser(getLoginName());
        this.groupMapper.insertSelective(group);
    }

    @Override
    @Transactional
    public void update(GroupDTO dto) {
        // 查询编辑的记录
        Group group = this.getById(dto.getId());
        if (group == null) {
            throw new ServiceException("要编辑的分组不存在");
        }
        if (!Boolean.TRUE.equals(group.getEnableWrite())) {
            throw new ServiceException("该分组不允许编辑");
        }
        // 判断名称是否已被使用
        long count = this.groupMapper.countByName(dto.getGroupName(), group.getId());
        if (count > 0) {
            throw new ServiceException("分组名称已被使用");
        }
        // 编辑分组
        BeanUtils.copyProperties(dto, group);
        group.setUpdateTime(new Date());
        group.setUpdateUser(getLoginName());
        this.groupMapper.updateByPrimaryKeySelective(group);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        // 查询删除的记录
        Group group = this.getById(id);
        if (group == null) {
            throw new ServiceException("要编辑的分组不存在");
        }
        // 删除分组
        this.groupMapper.deleteByPrimaryKey(id);
        // 删除分组下的所有客户端
        this.clientService.deleteByGroupId(id);
    }
}
