package com.jaagro.cbs.api.enums;

/**
 * @author gavin
 * 临界值方向
 */

public enum ThresholdDirectionEnum {
    /**
     * 临界值方向(1->=,2<=)
     */
    MORE_THAN(1, "MORE_THAN", "大于等于"),
    LESS_THAN(2, "LESS_THAN", "小于等于");
    private int code;
    private String type;
    private String desc;

    ThresholdDirectionEnum(int code, String type, String desc) {
        this.code = code;
        this.type = type;
        this.desc = desc;
    }

    public static String getDescByCode(int code) {
        for (ThresholdDirectionEnum type : ThresholdDirectionEnum.values()) {
            if (type.getCode() == code) {
                return type.getDesc();
            }
        }
        return null;
    }

    public static String getTypeByCode(int code) {
        for (ThresholdDirectionEnum type : ThresholdDirectionEnum.values()) {
            if (type.getCode() == code) {
                return type.getType();
            }
        }
        return null;
    }

    public static Integer getCode(String desc) {
        for (ThresholdDirectionEnum type : ThresholdDirectionEnum.values()) {
            if (type.getDesc().equalsIgnoreCase(desc)) {
                return type.getCode();
            }
        }
        return null;
    }

    public static ThresholdDirectionEnum toEnum(int code) {
        for (ThresholdDirectionEnum type : ThresholdDirectionEnum.values()) {
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
