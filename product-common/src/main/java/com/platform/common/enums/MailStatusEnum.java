package com.platform.common.enums;

public enum MailStatusEnum {
    SUCCESS("1", "成功"),
    FAIL("2", "失败"),
    NO_SEND("3", "未发送");

    private final String code;
    private final String description;

    public boolean is(String code) {
        return this.code.equals(code);
    }

    private MailStatusEnum(String code, String description) {
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
