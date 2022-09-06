package com.github.xzb617.cappuccino.server.domain.vo;

public class ElCheckboxOption {

    private String label;

    private Long value;

    private Boolean disabled = false;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }
}
