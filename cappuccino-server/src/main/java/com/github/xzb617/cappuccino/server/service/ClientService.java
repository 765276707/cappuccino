package com.github.xzb617.cappuccino.server.service;

import com.github.xzb617.cappuccino.commons.data.Meta;
import com.github.xzb617.cappuccino.server.domain.condition.ClientCondition;
import com.github.xzb617.cappuccino.server.domain.dto.ClientDTO;
import com.github.xzb617.cappuccino.server.domain.dto.CloneClientDTO;
import com.github.xzb617.cappuccino.server.domain.eimod.ClientWithConfigModel;
import com.github.xzb617.cappuccino.server.domain.entity.Client;
import com.github.xzb617.cappuccino.server.domain.vo.ClientImportAndExport;

import java.util.List;

public interface ClientService {

    Client getById(Long id);

    Client getByMeta(Meta meta);

    List<Client> getList(ClientCondition condition);

    List<ClientWithConfigModel> getExportList(List<Long> ids);

    void save(ClientDTO dto);

    void update(ClientDTO dto);

    void deleteById(Long id);

    void deleteByGroupId(Long groupId);

    void cloned(CloneClientDTO dto);

    void imports(List<ClientWithConfigModel> imports);
}
