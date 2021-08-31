package com.platform.common.enums;

public enum StatusEnum {
    SUCCESS("200", "成功"),
    FAIL("300", "失败"),
    OFFLINE_USER("301", "互踢"),
    JWT_EXPIRE("302", "JWT过期"),
    JWT_ERROR("303", "JWT异常"),
    VERIFY_CODE_ERROR("304", "登录验证码错误");

    private final String code;
    private final String description;

    public boolean is(String code) {
        return this.code.equals(code);
    }

    StatusEnum(String code, String description) {
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
