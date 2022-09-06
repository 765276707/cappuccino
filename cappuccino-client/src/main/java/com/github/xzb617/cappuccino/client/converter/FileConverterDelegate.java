package com.github.xzb617.cappuccino.client.converter;

import com.github.xzb617.cappuccino.commons.data.Config;
import com.github.xzb617.cappuccino.commons.enums.FileExtension;
import java.util.LinkedHashMap;
import java.util.Map;

public class FileConverterDelegate {

    public static Map delegateConvert(Config config) {
        if (config == null) {
            return new LinkedHashMap(0);
        }
        FileExtension fileExtension = config.getFileExtension();
        FileConverter fileConverter = FileConverterFactory.getConverter(fileExtension);
        return fileConverter.convert(config.getContent());
    }

}
