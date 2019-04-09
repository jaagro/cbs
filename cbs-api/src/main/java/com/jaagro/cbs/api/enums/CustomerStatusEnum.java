package com.jaagro.cbs.api.enums;

/**
 * 客户审核状态
 */
public enum CustomerStatusEnum {

    /**
     * 客户审核状态
     */
    UNCHECKED(0, "UNCHECKED", "未审核"),

    NORMAL_COOPERATION(1, "NORMAL_COOPERATION", "正常合作"),

    AUDIT_FAILED(2, "AUDIT_FAILED", "审核未通过"),

    STOP_COOPERATION(3, "STOP_COOPERATION", "停止合作");

    private int code;
    private String type;
    private String desc;

    CustomerStatusEnum(int code, String type, String desc) {
        this.code = code;
        this.type = type;
        this.desc = desc;
    }

    public static String getDescByCode(int code) {
        for (CustomerStatusEnum type : CustomerStatusEnum.values()) {
            if (type.getCode() == code) {
                return type.getDesc();
            }
        }
        return null;
    }

    public static String getTypeByCode(int code) {
        for (CustomerStatusEnum type : CustomerStatusEnum.values()) {
            if (type.getCode() == code) {
                return type.getType();
            }
        }
        return null;
    }

    public static Integer getCode(String desc) {
        for (CustomerStatusEnum type : CustomerStatusEnum.values()) {
            if (type.getDesc().equalsIgnoreCase(desc)) {
                return type.getCode();
            }
        }
        return null;
    }

    public static CustomerStatusEnum toEnum(int code) {
        for (CustomerStatusEnum type : CustomerStatusEnum.values()) {
            if (type.getCode() == code) {
                return type;
            }
        }
        return null;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
