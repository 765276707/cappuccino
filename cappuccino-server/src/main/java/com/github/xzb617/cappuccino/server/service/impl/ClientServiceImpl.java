package com.github.xzb617.cappuccino.server.service.impl;

import com.github.xzb617.cappuccino.commons.data.Meta;
import com.github.xzb617.cappuccino.server.constant.ResourceType;
import com.github.xzb617.cappuccino.server.domain.condition.ClientCondition;
import com.github.xzb617.cappuccino.server.domain.dto.ClientDTO;
import com.github.xzb617.cappuccino.server.domain.dto.CloneClientDTO;
import com.github.xzb617.cappuccino.server.domain.dto.ConfigDTO;
import com.github.xzb617.cappuccino.server.domain.dto.GrayscaleDTO;
import com.github.xzb617.cappuccino.server.domain.eimod.ClientWithConfigModel;
import com.github.xzb617.cappuccino.server.domain.entity.Client;
import com.github.xzb617.cappuccino.server.domain.entity.Config;
import com.github.xzb617.cappuccino.server.domain.entity.Grayscale;
import com.github.xzb617.cappuccino.server.domain.vo.ClientImportAndExport;
import com.github.xzb617.cappuccino.server.exception.ServiceException;
import com.github.xzb617.cappuccino.server.mapper.ClientMapper;
import com.github.xzb617.cappuccino.server.security.SecurityCapability;
import com.github.xzb617.cappuccino.server.service.ClientService;
import com.github.xzb617.cappuccino.server.service.ConfigService;
import com.github.xzb617.cappuccino.server.service.GrayscaleService;
import com.github.xzb617.cappuccino.server.service.RelationService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ClientServiceImpl extends SecurityCapability implements ClientService {

    private final ClientMapper clientMapper;
    private final ConfigService configService;
    private final GrayscaleService grayscaleService;
    private final RelationService relationService;

    public ClientServiceImpl(ClientMapper clientMapper, ConfigService configService, GrayscaleService grayscaleService, RelationService relationService) {
        this.clientMapper = clientMapper;
        this.configService = configService;
        this.grayscaleService = grayscaleService;
        this.relationService = relationService;
    }

    @Override
    public Client getById(Long id) {
        // 校验数据权限
        boolean allow = this.relationService.allow(id, ResourceType.CLIENT);
        if (!allow) {
            throw new ServiceException("无数据权限");
        }
        return this.clientMapper.selectById(id);
    }

    @Override
    public Client getByMeta(Meta meta) {
        return this.clientMapper.selectByMeta(meta);
    }

    @Override
    public List<Client> getList(ClientCondition condition) {
        if (isSuperAdmin()) {
            return this.clientMapper.selectList(condition, null);
        }
        return this.clientMapper.selectList(condition, getLoginUid());
    }

    @Override
    public List<ClientWithConfigModel> getExportList(List<Long> ids) {
        Example example = new Example(Client.class);
        example.createCriteria().andIn("id", ids);
        List<Client> clients = this.clientMapper.selectByExample(example);
        List<ClientWithConfigModel> exports = new ArrayList<>(clients.size());
        for (Client client : clients) {
            Config config = this.configService.getByClientId(client.getId());
            Grayscale grayscale = this.grayscaleService.getByClientId(client.getId());
            ClientWithConfigModel export = new ClientWithConfigModel();
            export.setClient(client);
            export.setConfig(config);
            export.setGrayscale(grayscale);
            exports.add(export);
        }
        return exports;
    }

    @Override
    @Transactional
    public void save(ClientDTO dto) {
        // 判断同个环境和分组下的客户端名称是否已被使用
        long count = this.countBy(dto.getClientName(), dto.getEnvId(), dto.getGroupId(), null);
        if (count > 0) {
            throw new ServiceException("当前环境和分组下，客户端名称已存在");
        }
        // 创建客户端
        Client client = new Client();
        BeanUtils.copyProperties(dto, client, "id");
        client.setCreateTime(new Date());
        client.setCreateUser(getLoginName());
        this.clientMapper.insertSelective(client);
        // 创建者直接拥有对该客户端的权限
        this.relationService.assign(getLoginUid(), client.getId(), ResourceType.CLIENT);
    }

    @Override
    @Transactional
    public void update(ClientDTO dto) {
        // 校验数据权限
        boolean allow = this.relationService.allow(dto.getId(), ResourceType.CLIENT);
        if (!allow) {
            throw new ServiceException("无数据权限");
        }
        // 查询客户端
        Client client = this.getById(dto.getId());
        if (client == null) {
            throw new ServiceException("");
        }
        // 判断同个环境和分组下的客户端名称是否已被使用
        long count = this.countBy(dto.getClientName(), dto.getEnvId(), dto.getGroupId(), dto.getId());
        if (count > 0) {
            throw new ServiceException("当前环境和分组下，客户端名称已存在");
        }
        // 更新客户端
        BeanUtils.copyProperties(dto, client);
        client.setUpdateTime(new Date());
        client.setUpdateUser(getLoginName());
        this.clientMapper.updateByPrimaryKeySelective(client);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        // 校验数据权限
        boolean allow = this.relationService.allow(id, ResourceType.CLIENT);
        if (!allow) {
            throw new ServiceException("无数据权限");
        }
        // 查询客户端
        Client client = this.getById(id);
        if (client == null) {
            throw new ServiceException("客户端不存在或已被删除");
        }
        // 查询客户端的配置，有则删除
        Long clientId = client.getId();
        Config config = this.configService.getByClientId(clientId);
        if (config != null) {
            this.configService.deleteById(config.getId());
        }
        Grayscale grayscale = this.grayscaleService.getByClientId(clientId);
        if (grayscale != null) {
            this.grayscaleService.deleteById(grayscale.getId());
        }
        // 删除客户端
        this.clientMapper.deleteByPrimaryKey(id);
    }

    @Override
    @Transactional
    public void deleteByGroupId(Long groupId) {
        // 同时需要删除客户端下的主配置和灰度配置
        List<Client> clients = this.clientMapper.selectByGroupId(groupId);
        for (Client client : clients) {
            Long clientId = client.getId();
            // 删除主配置
            Config config = this.configService.getByClientId(clientId);
            if (config != null) {
                this.configService.deleteById(config.getId());
            }
            // 删除灰度
            Grayscale grayscale = this.grayscaleService.getByClientId(clientId);
            if (grayscale != null) {
                this.grayscaleService.deleteById(grayscale.getId());
            }
            // 删除客户端
            this.clientMapper.deleteByPrimaryKey(clientId);
        }
    }

    @Override
    @Transactional
    public void cloned(CloneClientDTO dto) {
        List<Long> clientIds = dto.getClientIds();
        for (Long clientId : clientIds) {
            // 查询要克隆的客户端
            Client client = this.getById(clientId);
            if (client == null) {
                throw new ServiceException("编号为"+ clientId +"客户端不存在或已被删除");
            }
            // 判断要克隆的环境是否已存在对应的客户端
            long count = this.countBy(client.getClientName(), dto.getEnvId(), dto.getGroupId(), null);
            if (count > 0) {
                throw new ServiceException("目标环境和分组下，已存在相同名称为"+client.getClientName()+"的客户端，克隆失败");
            }
            // 拷贝客户端
            Client cloneClient = new Client();
            BeanUtils.copyProperties(client, cloneClient,
                    "id", "envName", "groupName", "createTime", "createUser", "updateTime", "updateUser");
            cloneClient.setEnvId(dto.getEnvId());
            cloneClient.setGroupId(dto.getGroupId());
            cloneClient.setCreateTime(new Date());
            cloneClient.setCreateUser(getLoginName());
            this.clientMapper.insertSelective(cloneClient);


            // 判断是否要克隆其配置
            if (Boolean.TRUE.equals(dto.getWithConfig())) {
                Config config = this.configService.getByClientId(clientId);
                if (config != null) {
                    // 拷贝主线配置
                    ConfigDTO configDTO = new ConfigDTO();
                    BeanUtils.copyProperties(config, configDTO, "id", "clientId");
                    configDTO.setClientId(cloneClient.getId());
                    this.configService.save(configDTO);

                    // 查询该配置的灰度配置
                    Grayscale grayscale = this.grayscaleService.getByClientId(clientId);
                    if (grayscale != null) {
                        // 拷贝灰度配置
                        GrayscaleDTO grayscaleDTO = new GrayscaleDTO();
                        BeanUtils.copyProperties(grayscale, grayscaleDTO, "id", "clientId");
                        grayscaleDTO.setClientId(cloneClient.getId());
                        this.grayscaleService.release(grayscaleDTO);
                    }
                }
            }
        }
    }

    @Override
    @Transactional
    public void imports(List<ClientWithConfigModel> imports) {
        for (ClientWithConfigModel model : imports) {
            Client client = model.getClient();
            // 判断要克隆的环境是否已存在对应的客户端
            long count = this.countBy(client.getClientName(), client.getEnvId(), client.getGroupId(), null);
            if (count > 0) {
                throw new ServiceException("目标环境和分组下，已存在相同名称为"+client.getClientName()+"的客户端，导入失败");
            }
            // 导入客户端
            Client importedClient = new Client();
            BeanUtils.copyProperties(client, importedClient,
                    "id", "envName", "groupName", "createTime", "createUser", "updateTime", "updateUser");
            importedClient.setEnvId(client.getEnvId());
            importedClient.setGroupId(client.getGroupId());
            importedClient.setCreateTime(new Date());
            importedClient.setCreateUser(getLoginName());
            this.clientMapper.insertSelective(importedClient);

            // 导入主配置
            Config config = model.getConfig();
            if (config != null) {
                ConfigDTO configDTO = new ConfigDTO();
                BeanUtils.copyProperties(config, configDTO, "id", "clientId");
                configDTO.setClientId(importedClient.getId());
                this.configService.save(configDTO);
            }
            // 导入灰度配置
            Grayscale grayscale = model.getGrayscale();
            if (grayscale != null) {
                // 拷贝灰度配置
                GrayscaleDTO grayscaleDTO = new GrayscaleDTO();
                BeanUtils.copyProperties(grayscale, grayscaleDTO, "id", "clientId");
                grayscaleDTO.setClientId(importedClient.getId());
                this.grayscaleService.release(grayscaleDTO);
            }
        }
    }

    private long countBy(String clientName, Long envId, Long groupId, Long excludeId) {
        return this.clientMapper.countBy(clientName, envId, groupId, excludeId);
    }
}
