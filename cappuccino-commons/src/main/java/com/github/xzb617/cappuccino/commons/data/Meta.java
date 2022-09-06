package com.github.xzb617.cappuccino.commons.data;

import java.util.HashMap;
import java.util.Map;

/**
 * 客户端元数据
 * @author xzb617
 */
public class Meta {

    private String env;

    private String group;

    private String name;

    private String grayscale;

    public Meta() {
    }

    public Meta(String env, String group, String name, String grayscale) {
        this.env = env;
        this.group = group;
        this.name = name;
        this.grayscale = grayscale;
    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGrayscale() {
        return grayscale;
    }

    public void setGrayscale(String grayscale) {
        this.grayscale = grayscale;
    }


    /**
     * 将Meta转换成Map类型的参数
     * @return
     */
    public Map<String, String> toParameterMap() {
        Map<String, String> metaMap = new HashMap<>(4);
        metaMap.put("env", this.getEnv());
        metaMap.put("group", this.getGroup());
        metaMap.put("name", this.getName());
        metaMap.put("grayscale", this.getGrayscale());
        return metaMap;
    }

    @Override
    public String toString() {
        return "ClientMeta{" +
                "env='" + env + '\'' +
                ", group='" + group + '\'' +
                ", name='" + name + '\'' +
                ", grayscale='" + grayscale + '\'' +
                '}';
    }
}
