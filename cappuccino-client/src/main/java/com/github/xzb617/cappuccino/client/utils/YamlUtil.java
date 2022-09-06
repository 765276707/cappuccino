package com.github.xzb617.cappuccino.client.utils;

import org.yaml.snakeyaml.Yaml;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class YamlUtil {

    public static LinkedHashMap<String, Object> ymlToMap(String yamlContent) {
        Yaml yaml = new Yaml();
        LinkedHashMap load = yaml.load(yamlContent);
        return mapToProperties(load);
    }

    private static LinkedHashMap<String, Object> mapToProperties(LinkedHashMap<String, Object> m) {
        final LinkedHashMap<String, Object> resultMap = new LinkedHashMap<>();
        mapToProperties(null, resultMap, m);
        return resultMap;
    }

    private static void mapToProperties(String key, final LinkedHashMap<String, Object> toMap, LinkedHashMap<String, Object> fromMap) {
        Iterator<Map.Entry<String, Object>> it = fromMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Object> entry = it.next();
            Object value = entry.getValue();
            if (value instanceof Map) {
                String relKey = entry.getKey();
                if (key != null) {
                    relKey = key + "." + entry.getKey();
                }
                mapToProperties(relKey, toMap, (LinkedHashMap<String, Object>) value);
            } else {
                String relKey = entry.getKey();
                if (key != null) {
                    relKey = key + "." + entry.getKey();
                }
                toMap.put(relKey, entry.getValue());
            }
        }
    }


}
