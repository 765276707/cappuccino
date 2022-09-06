package com.github.xzb617.cappuccino.client.converter.impl;

import com.github.xzb617.cappuccino.client.converter.FileConverter;
import com.github.xzb617.cappuccino.client.utils.YamlUtil;
import com.github.xzb617.cappuccino.commons.enums.FileExtension;

import java.util.Map;

public class YamlFileConverter implements FileConverter {

    @Override
    public FileExtension support() {
        return FileExtension.YAML;
    }

    @Override
    public Map convert(String source) {
        return YamlUtil.ymlToMap(source);
    }

}
