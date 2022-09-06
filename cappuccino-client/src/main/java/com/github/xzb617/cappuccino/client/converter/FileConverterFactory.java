package com.github.xzb617.cappuccino.client.converter;

import com.github.xzb617.cappuccino.client.converter.impl.PropertiesFileConverter;
import com.github.xzb617.cappuccino.client.converter.impl.YamlFileConverter;
import com.github.xzb617.cappuccino.commons.enums.FileExtension;

import java.util.HashMap;
import java.util.Map;

public class FileConverterFactory {

    private final static Map<FileExtension, FileConverter> CONVERTER_CACHE = new HashMap<>(5);

    static {
        CONVERTER_CACHE.put(FileExtension.PROPERTIES, new PropertiesFileConverter());
        CONVERTER_CACHE.put(FileExtension.YAML, new YamlFileConverter());
    }

    public static FileConverter getConverter(FileExtension fileExtension) {
        FileConverter fileConverter = CONVERTER_CACHE.get(fileExtension);
        if (fileConverter == null) {
            throw new IllegalArgumentException("Unsupported file extension of config: " + fileExtension.getValue());
        }
        return fileConverter;
    }

}
