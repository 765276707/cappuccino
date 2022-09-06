package com.github.xzb617.cappuccino.server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.xzb617.cappuccino.commons.utils.StrUtil;
import com.github.xzb617.cappuccino.server.aoplog.Logger;
import com.github.xzb617.cappuccino.server.base.AjaxResponse;
import com.github.xzb617.cappuccino.server.base.Page;
import com.github.xzb617.cappuccino.server.base.PageCondition;
import com.github.xzb617.cappuccino.server.base.PageData;
import com.github.xzb617.cappuccino.server.domain.condition.ClientCondition;
import com.github.xzb617.cappuccino.server.domain.dto.ClientDTO;
import com.github.xzb617.cappuccino.server.domain.dto.CloneClientDTO;
import com.github.xzb617.cappuccino.server.domain.eimod.ClientModel;
import com.github.xzb617.cappuccino.server.domain.eimod.ClientWithConfigModel;
import com.github.xzb617.cappuccino.server.domain.entity.Auditlog;
import com.github.xzb617.cappuccino.server.domain.entity.Client;
import com.github.xzb617.cappuccino.server.domain.entity.Config;
import com.github.xzb617.cappuccino.server.domain.entity.Grayscale;
import com.github.xzb617.cappuccino.server.domain.vo.ClientImportAndExport;
import com.github.xzb617.cappuccino.server.domain.vo.ConfigInfo;
import com.github.xzb617.cappuccino.server.security.auth.Anonymous;
import com.github.xzb617.cappuccino.server.service.AuditlogService;
import com.github.xzb617.cappuccino.server.service.ClientService;
import com.github.xzb617.cappuccino.server.service.ConfigService;
import com.github.xzb617.cappuccino.server.service.GrayscaleService;
import com.github.xzb617.cappuccino.server.utils.FileValidator;
import com.github.xzb617.cappuccino.server.validation.group.Insert;
import com.github.xzb617.cappuccino.server.validation.group.Update;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Validated
@RequestMapping("/client")
@RestController
public class ClientController {

    private final ObjectMapper objectMapper;
    private final ClientService clientService;
    private final ConfigService configService;
    private final GrayscaleService grayscaleService;
    private final AuditlogService auditlogService;

    public ClientController(ObjectMapper objectMapper, ClientService clientService, ConfigService configService, GrayscaleService grayscaleService, AuditlogService auditlogService) {
        this.objectMapper = objectMapper;
        this.clientService = clientService;
        this.configService = configService;
        this.grayscaleService = grayscaleService;
        this.auditlogService = auditlogService;
    }

    @GetMapping("/getById")
    public AjaxResponse getById(@NotNull(message = "客户端编号不能为空") Long id) {
        Client client = this.clientService.getById(id);
        return AjaxResponse.success()
                .message("查询成功").data(client);
    }

    @GetMapping("/getPage")
    public AjaxResponse getPage(ClientCondition condition, PageCondition page) {
        System.out.println(condition.toString());
        PageHelper.startPage(page);
        List<Client> list = this.clientService.getList(condition);
        PageInfo<Client> pageInfo = new PageInfo<>(list);
        PageData pageData = Page.toData(pageInfo);
        return AjaxResponse.success()
                .message("查询成功").data(pageData);
    }

    @GetMapping("/getConfigInfo")
    public AjaxResponse getConfigInfo(@NotNull(message = "客户端编号不能为空") Long id) {
        Client client = this.clientService.getById(id);
        Config config = this.configService.getByClientId(id);
        Grayscale grayscale = this.grayscaleService.getByClientId(id);
        List<Auditlog> auditlogs = this.auditlogService.getRecentlyList(id, 5);
        ConfigInfo configInfo = new ConfigInfo();
        configInfo.setClient(client);
        configInfo.setConfig(config);
        configInfo.setGrayscale(grayscale);
        configInfo.setAuditlogs(auditlogs);
        return AjaxResponse.success()
                .message("查询成功").data(configInfo);
    }

    @PostMapping("/save")
    @Logger(desc = "创建客户端")
    public AjaxResponse save(@Validated({Insert.class}) @RequestBody ClientDTO dto) {
        this.clientService.save(dto);
        return AjaxResponse.success().message("添加成功");
    }

    @PutMapping("/update")
    @Logger(desc = "更新客户端")
    public AjaxResponse update(@Validated({Update.class})@RequestBody ClientDTO dto) {
        this.clientService.update(dto);
        return AjaxResponse.success().message("更新成功");
    }

    @PutMapping("/clone")
    @Logger(desc = "克隆客户端")
    public AjaxResponse cloned(@RequestBody CloneClientDTO dto) {
        this.clientService.cloned(dto);
        return AjaxResponse.success().message("克隆成功");
    }


    @DeleteMapping("/deleteById")
    @Logger(desc = "删除客户端")
    public AjaxResponse deleteById(@NotNull(message = "客户端编号不能为空") Long id) {
        this.clientService.deleteById(id);
        return AjaxResponse.success().message("删除成功");
    }

    @GetMapping("/exports")
    @Anonymous
    public void exports(String ids, HttpServletResponse response) throws IOException {
        List<ClientWithConfigModel> models = this.clientService.getExportList(StrUtil.strToLongList(ids));
        response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode("clients.json", "UTF-8"));
        ClientModel clientModel = new ClientModel();
        clientModel.setClientWithConfigModels(models);
        this.objectMapper.writeValue(response.getOutputStream(), clientModel);
    }

    @PostMapping("/imports")
    public AjaxResponse imports(MultipartFile file) throws IOException {
        // 校验文件
        FileValidator.notNull(file, "客户端导入的文件不能为空");
        FileValidator.supportExtensions(file.getOriginalFilename(), "客户端导入的文件格式不受支持", "json");
        FileValidator.limitMaxsize(file.getSize(), 1024*1024*2, "客户端导入的文件大小不允许超过2M");

        // 转换数据
        ClientModel importModel = this.objectMapper.readValue(file.getInputStream(), ClientModel.class);

        // 校验数据
        if (importModel.getClientWithConfigModels().size() > 50) {
            return AjaxResponse.failure("单次导入的客户端不允许超过50个");
        }

        // 执行导入
        this.clientService.imports(importModel.getClientWithConfigModels());

        // 返回结果
        Map<String, Number> result = new HashMap<>(2);
        result.put("total", importModel.getClientWithConfigModels().size());
        return AjaxResponse.success().message("导入成功").data(result);
    }

}
