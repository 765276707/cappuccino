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
        // ??????????????????
        boolean allow = this.relationService.allow(id, ResourceType.CLIENT);
        if (!allow) {
            throw new ServiceException("???????????????");
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
        // ??????????????????????????????????????????????????????????????????
        long count = this.countBy(dto.getClientName(), dto.getEnvId(), dto.getGroupId(), null);
        if (count > 0) {
            throw new ServiceException("???????????????????????????????????????????????????");
        }
        // ???????????????
        Client client = new Client();
        BeanUtils.copyProperties(dto, client, "id");
        client.setCreateTime(new Date());
        client.setCreateUser(getLoginName());
        this.clientMapper.insertSelective(client);
        // ?????????????????????????????????????????????
        this.relationService.assign(getLoginUid(), client.getId(), ResourceType.CLIENT);
    }

    @Override
    @Transactional
    public void update(ClientDTO dto) {
        // ??????????????????
        boolean allow = this.relationService.allow(dto.getId(), ResourceType.CLIENT);
        if (!allow) {
            throw new ServiceException("???????????????");
        }
        // ???????????????
        Client client = this.getById(dto.getId());
        if (client == null) {
            throw new ServiceException("");
        }
        // ??????????????????????????????????????????????????????????????????
        long count = this.countBy(dto.getClientName(), dto.getEnvId(), dto.getGroupId(), dto.getId());
        if (count > 0) {
            throw new ServiceException("???????????????????????????????????????????????????");
        }
        // ???????????????
        BeanUtils.copyProperties(dto, client);
        client.setUpdateTime(new Date());
        client.setUpdateUser(getLoginName());
        this.clientMapper.updateByPrimaryKeySelective(client);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        // ??????????????????
        boolean allow = this.relationService.allow(id, ResourceType.CLIENT);
        if (!allow) {
            throw new ServiceException("???????????????");
        }
        // ???????????????
        Client client = this.getById(id);
        if (client == null) {
            throw new ServiceException("?????????????????????????????????");
        }
        // ???????????????????????????????????????
        Long clientId = client.getId();
        Config config = this.configService.getByClientId(clientId);
        if (config != null) {
            this.configService.deleteById(config.getId());
        }
        Grayscale grayscale = this.grayscaleService.getByClientId(clientId);
        if (grayscale != null) {
            this.grayscaleService.deleteById(grayscale.getId());
        }
        // ???????????????
        this.clientMapper.deleteByPrimaryKey(id);
    }

    @Override
    @Transactional
    public void deleteByGroupId(Long groupId) {
        // ?????????????????????????????????????????????????????????
        List<Client> clients = this.clientMapper.selectByGroupId(groupId);
        for (Client client : clients) {
            Long clientId = client.getId();
            // ???????????????
            Config config = this.configService.getByClientId(clientId);
            if (config != null) {
                this.configService.deleteById(config.getId());
            }
            // ????????????
            Grayscale grayscale = this.grayscaleService.getByClientId(clientId);
            if (grayscale != null) {
                this.grayscaleService.deleteById(grayscale.getId());
            }
            // ???????????????
            this.clientMapper.deleteByPrimaryKey(clientId);
        }
    }

    @Override
    @Transactional
    public void cloned(CloneClientDTO dto) {
        List<Long> clientIds = dto.getClientIds();
        for (Long clientId : clientIds) {
            // ???????????????????????????
            Client client = this.getById(clientId);
            if (client == null) {
                throw new ServiceException("?????????"+ clientId +"?????????????????????????????????");
            }
            // ?????????????????????????????????????????????????????????
            long count = this.countBy(client.getClientName(), dto.getEnvId(), dto.getGroupId(), null);
            if (count > 0) {
                throw new ServiceException("???????????????????????????????????????????????????"+client.getClientName()+"???????????????????????????");
            }
            // ???????????????
            Client cloneClient = new Client();
            BeanUtils.copyProperties(client, cloneClient,
                    "id", "envName", "groupName", "createTime", "createUser", "updateTime", "updateUser");
            cloneClient.setEnvId(dto.getEnvId());
            cloneClient.setGroupId(dto.getGroupId());
            cloneClient.setCreateTime(new Date());
            cloneClient.setCreateUser(getLoginName());
            this.clientMapper.insertSelective(cloneClient);


            // ??????????????????????????????
            if (Boolean.TRUE.equals(dto.getWithConfig())) {
                Config config = this.configService.getByClientId(clientId);
                if (config != null) {
                    // ??????????????????
                    ConfigDTO configDTO = new ConfigDTO();
                    BeanUtils.copyProperties(config, configDTO, "id", "clientId");
                    configDTO.setClientId(cloneClient.getId());
                    this.configService.save(configDTO);

                    // ??????????????????????????????
                    Grayscale grayscale = this.grayscaleService.getByClientId(clientId);
                    if (grayscale != null) {
                        // ??????????????????
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
            // ?????????????????????????????????????????????????????????
            long count = this.countBy(client.getClientName(), client.getEnvId(), client.getGroupId(), null);
            if (count > 0) {
                throw new ServiceException("???????????????????????????????????????????????????"+client.getClientName()+"???????????????????????????");
            }
            // ???????????????
            Client importedClient = new Client();
            BeanUtils.copyProperties(client, importedClient,
                    "id", "envName", "groupName", "createTime", "createUser", "updateTime", "updateUser");
            importedClient.setEnvId(client.getEnvId());
            importedClient.setGroupId(client.getGroupId());
            importedClient.setCreateTime(new Date());
            importedClient.setCreateUser(getLoginName());
            this.clientMapper.insertSelective(importedClient);

            // ???????????????
            Config config = model.getConfig();
            if (config != null) {
                ConfigDTO configDTO = new ConfigDTO();
                BeanUtils.copyProperties(config, configDTO, "id", "clientId");
                configDTO.setClientId(importedClient.getId());
                this.configService.save(configDTO);
            }
            // ??????????????????
            Grayscale grayscale = model.getGrayscale();
            if (grayscale != null) {
                // ??????????????????
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
