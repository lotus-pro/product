package com.platform.common.enums;

public enum TopicConsumerEnum {
    SUCCESS("1", "成功"),
    FAIL("0", "失败");

    private final String code;
    private final String description;

    public boolean is(String code) {
        return this.code.equals(code);
    }

    private TopicConsumerEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return this.code;
    }

    public String getDescription() {
        return this.description;
    }
}
