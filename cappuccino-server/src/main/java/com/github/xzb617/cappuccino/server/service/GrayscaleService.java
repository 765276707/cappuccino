package com.github.xzb617.cappuccino.server.service;

import com.github.xzb617.cappuccino.server.domain.dto.GrayscaleDTO;
import com.github.xzb617.cappuccino.server.domain.entity.Grayscale;

public interface GrayscaleService {
    Grayscale getByClientId(Long clientId);

    void deleteById(Long id);

    void release(GrayscaleDTO dto);

    void releaseFull(Long id);

}
