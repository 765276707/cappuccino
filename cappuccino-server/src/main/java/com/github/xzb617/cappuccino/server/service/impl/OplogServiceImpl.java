package com.github.xzb617.cappuccino.server.service.impl;

import com.github.xzb617.cappuccino.server.aoplog.LoggerService;
import com.github.xzb617.cappuccino.server.base.TextAndTimeCondition;
import com.github.xzb617.cappuccino.server.domain.entity.Oplog;
import com.github.xzb617.cappuccino.server.mapper.OplogMapper;
import com.github.xzb617.cappuccino.server.service.OplogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class OplogServiceImpl implements OplogService, LoggerService {

    private final OplogMapper oplogMapper;

    public OplogServiceImpl(OplogMapper oplogMapper) {
        this.oplogMapper = oplogMapper;
    }

    @Override
    public List<Oplog> getList(TextAndTimeCondition condition) {
        return this.oplogMapper.selectList(condition);
    }

    @Override
    @Transactional
    public void saveAopLog(String reqURI, String opMethod, String opArgs, String opDesc, String opUser, Date opTime) {
        Oplog oplog = new Oplog();
        oplog.setReqUri(reqURI);
        oplog.setOpMethod(opMethod);
        oplog.setOpDesc(opDesc);
        oplog.setOpUser(opUser);
        oplog.setOpTime(opTime);
        oplog.setOpArgs(opArgs);
        this.oplogMapper.insertSelective(oplog);
    }
}
