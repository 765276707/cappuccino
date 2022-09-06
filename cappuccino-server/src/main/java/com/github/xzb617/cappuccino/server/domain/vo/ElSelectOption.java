package com.github.xzb617.cappuccino.server.domain.vo;

public class ElSelectOption {

    private Long value;

    private String label;

    private Boolean disabled = false;

    public ElSelectOption() {
    }

    public ElSelectOption(Long value, String label) {
        this.value = value;
        this.label = label;
    }

    public ElSelectOption(Long value, String label, Boolean disabled) {
        this.value = value;
        this.label = label;
        this.disabled = disabled;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }
}
