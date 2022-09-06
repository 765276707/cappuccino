package com.github.xzb617.cappuccino.server.service;

import com.github.xzb617.cappuccino.server.domain.condition.ReleaseCondition;
import com.github.xzb617.cappuccino.server.domain.entity.Config;
import com.github.xzb617.cappuccino.server.domain.entity.Grayscale;
import com.github.xzb617.cappuccino.server.domain.entity.Release;
import com.github.xzb617.cappuccino.server.domain.vo.ReleaseVO;

import java.util.List;

public interface ReleaseService {

    List<ReleaseVO> getList(ReleaseCondition condition);

    Integer getLastVersion(Long clientId, int releaseType);

    void rollback(Long id);

    void snapshot(Config config);

    void snapshot(Grayscale grayscale);

}
