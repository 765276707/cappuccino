package com.github.xzb617.cappuccino.client.converter;

import com.github.xzb617.cappuccino.commons.enums.FileExtension;

import java.util.Map;

public interface FileConverter {

    public FileExtension support();

    public Map convert(String source);

}
