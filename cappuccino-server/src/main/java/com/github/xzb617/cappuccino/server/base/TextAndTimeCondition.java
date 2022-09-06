package com.github.xzb617.cappuccino.server.base;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class TextAndTimeCondition extends TextCondition {

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date bgnTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    public Date getBgnTime() {
        return bgnTime;
    }

    public void setBgnTime(Date bgnTime) {
        this.bgnTime = bgnTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
