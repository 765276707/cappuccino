package com.github.xzb617.cappuccino.server.service;

import com.github.xzb617.cappuccino.server.domain.entity.Environment;

import java.util.List;

public interface EnvironmentService {

    List<Environment> getList(Long excludeId);

}
