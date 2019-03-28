package com.jaagro.cbs.api.enums;

/**
 * @author gavin
 * 处理类型枚举
 * 20190327
 */
public enum TechConsultHandleTypeEnum {
    /**
     * (1-电话询问,2-上门查看,3-已经解决,4-暂时搁置)
     */

    PHONE_CONSULT(1, "PHONE_CONSULT", "电话询问"),
    VISIT_CHECK(2, "VISIT_CHECK", "上门查看"),
    ALL_RESOLVED(3, "ALL_RESOLVED", "已经解决"),
    HANG_ON(4, "HANG_ON", "暂时搁置");


    private int code;
    private String type;
    private String desc;

    TechConsultHandleTypeEnum(int code, String type, String desc) {
        this.code = code;
        this.type = type;
        this.desc = desc;
    }

    public static String getDescByCode(int code) {
        for (TechConsultHandleTypeEnum type : TechConsultHandleTypeEnum.values()) {
            if (type.getCode() == code) {
                return type.getDesc();
            }
        }
        return null;
    }

    public static String getTypeByCode(int code) {
        for (TechConsultHandleTypeEnum type : TechConsultHandleTypeEnum.values()) {
            if (type.getCode() == code) {
                return type.getType();
            }
        }
        return null;
    }

    public static Integer getCode(String desc) {
        for (TechConsultHandleTypeEnum type : TechConsultHandleTypeEnum.values()) {
            if (type.getDesc().equalsIgnoreCase(desc)) {
                return type.getCode();
            }
        }
        return null;
    }

    public static TechConsultHandleTypeEnum toEnum(int code) {
        for (TechConsultHandleTypeEnum type : TechConsultHandleTypeEnum.values()) {
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
