package com.github.xzb617.cappuccino.server.service.impl;

import com.github.xzb617.cappuccino.server.domain.entity.Environment;
import com.github.xzb617.cappuccino.server.mapper.EnvironmentMapper;
import com.github.xzb617.cappuccino.server.service.EnvironmentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnvironmentServiceImpl implements EnvironmentService {

    private final EnvironmentMapper environmentMapper;

    public EnvironmentServiceImpl(EnvironmentMapper environmentMapper) {
        this.environmentMapper = environmentMapper;
    }

    @Override
    public List<Environment> getList(Long excludeId) {
        return this.environmentMapper.selectList(excludeId);
    }
}
