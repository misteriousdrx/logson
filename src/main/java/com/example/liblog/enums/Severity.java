package com.example.liblog.enums;

public enum Severity {

    ERROR("ERROR"),
    WARN("WARNING"),
    INFO("INFO"),
    DEBUG("DEBUG"),
    TRACE("TRACE");

    String value;

    Severity() {
    }

    Severity(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
