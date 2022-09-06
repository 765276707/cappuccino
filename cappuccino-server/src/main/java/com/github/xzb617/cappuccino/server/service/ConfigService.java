package com.github.xzb617.cappuccino.server.service;

import com.github.xzb617.cappuccino.server.domain.dto.ConfigDTO;
import com.github.xzb617.cappuccino.server.domain.entity.Client;
import com.github.xzb617.cappuccino.server.domain.entity.Config;

import java.util.List;

public interface ConfigService {

    Config getById(Long id);

    Config getByClientId(Long clientId);

    void save(ConfigDTO dto);

    void update(ConfigDTO dto);

    void deleteById(Long id);

}
