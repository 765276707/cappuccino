package com.github.xzb617.cappuccino.server.service;

import com.github.xzb617.cappuccino.server.constant.ResourceType;
import com.github.xzb617.cappuccino.server.domain.dto.AssignDTO;
import com.github.xzb617.cappuccino.server.domain.vo.RelationVO;

import java.util.List;

public interface RelationService {

    boolean allow(Long resourceId, ResourceType resourceType);

    void assign(Long userId, Long resourceId, ResourceType resourceType);

    void assign(AssignDTO assignDTO);

    RelationVO getRelations(Long clientId);

}
