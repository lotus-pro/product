package com.platform.common.enums;

public enum BooleanEnum {
    TRUE("1", "是"),
    FALSE("0", "否"),
    ;

    private final String flag;
    private final String description;

    public boolean is(String flag) {
        return this.flag.equals(flag);
    }

    private BooleanEnum(String flag, String description) {
        this.flag = flag;
        this.description = description;
    }

    public String getFlag() {
        return this.flag;
    }

    public String getDescription() {
        return this.description;
    }
}
