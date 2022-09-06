package com.github.xzb617.cappuccino.server.service.impl;

import com.github.xzb617.cappuccino.server.constant.ResourceType;
import com.github.xzb617.cappuccino.server.domain.condition.UserCondition;
import com.github.xzb617.cappuccino.server.domain.dto.AssignDTO;
import com.github.xzb617.cappuccino.server.domain.entity.Relation;
import com.github.xzb617.cappuccino.server.domain.entity.User;
import com.github.xzb617.cappuccino.server.domain.vo.ElCheckboxOption;
import com.github.xzb617.cappuccino.server.domain.vo.RelationVO;
import com.github.xzb617.cappuccino.server.mapper.RelationMapper;
import com.github.xzb617.cappuccino.server.security.SecurityCapability;
import com.github.xzb617.cappuccino.server.security.perms.Role;
import com.github.xzb617.cappuccino.server.service.RelationService;
import com.github.xzb617.cappuccino.server.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class RelationServiceImpl extends SecurityCapability implements RelationService {

    private final RelationMapper relationMapper;
    private final UserService userService;

    public RelationServiceImpl(RelationMapper relationMapper, UserService userService) {
        this.relationMapper = relationMapper;
        this.userService = userService;
    }

    @Override
    public boolean allow(Long resourceId, ResourceType resourceType) {
        if (isSuperAdmin()) {
            // 超管拥有所有数据权限
            return true;
        }
        Long userId = getLoginUid();
        long count = this.relationMapper.countBy(userId, resourceId, resourceType.getType());
        return count > 0;
    }

    @Override
    @Transactional
    public void assign(Long userId, Long resourceId, ResourceType resourceType) {
        List<Long> userIds = new ArrayList<>(1);
        userIds.add(userId);
        AssignDTO assignDTO = new AssignDTO(userIds, resourceId, resourceType);
        this.assign(assignDTO);
    }

    @Override
    @Transactional
    public void assign(AssignDTO assignDTO) {
        // 删除原有权限
        this.deleteByResource(assignDTO.getResourceId(), assignDTO.getResourceType());
        // 插入新的权限
        List<Long> userIds = assignDTO.getUserIds();
        System.out.println("授权： " + assignDTO.toString());
        for (Long userId : userIds) {
            Relation relation = new Relation();
            relation.setUserId(userId);
            relation.setResourceId(assignDTO.getResourceId());
            relation.setResourceType(assignDTO.getResourceType());
            this.relationMapper.insertSelective(relation);
        }
    }

    @Override
    public RelationVO getRelations(Long clientId) {
        // 查询所有管理员
        UserCondition userCondition = new UserCondition();
        List<User> users = this.userService.getList(userCondition);
        // 查询已授权的普通管理员
        List<Long> assignUserIds = this.relationMapper.selectUserIdsByResource(clientId, ResourceType.CLIENT.getType());
        // 解析数据
        List<ElCheckboxOption> options = new ArrayList<>(users.size());
        for (User user : users) {
            ElCheckboxOption option = new ElCheckboxOption();
            option.setValue(user.getId());
            if (Role.SUPER_ADMIN.getValue().equals(user.getRole())) {
                // SA
                assignUserIds.add(user.getId());
                option.setLabel(user.getUsername() + " | " + user.getRealname() + " (SA)");
                option.setDisabled(true);
            } else {
                option.setLabel(user.getUsername() + " | " + user.getRealname());
            }
            options.add(option);
        }
        return new RelationVO(assignUserIds, options);
    }

    private void deleteByResource(Long resourceId, String resourceType) {
        this.relationMapper.deleteByResource(resourceId, resourceType);
    }

}
