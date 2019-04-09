package com.jaagro.cbs.api.enums;

/**
 * @author gavin
 * 报警处理类型枚举
 * 20190408
 */
public enum AlarmLogHandleTypeEnum {
    /**
     * 处理类型(1-电话询问,2-远程协助,3-上门处理,4-故障误报)
     */

    PHONE_CONSULT(1, "PHONE_CONSULT", "电话询问"),
    REMOTE_HELP(2, "REMOTE_HELP", "远程协助"),
    VISIT_HANDLE(3, "VISIT_HANDLE", "上门处理"),
    ERROR_REPORT(4, "ERROR_REPORT", "故障误报");


    private int code;
    private String type;
    private String desc;

    AlarmLogHandleTypeEnum(int code, String type, String desc) {
        this.code = code;
        this.type = type;
        this.desc = desc;
    }

    public static String getDescByCode(int code) {
        for (AlarmLogHandleTypeEnum type : AlarmLogHandleTypeEnum.values()) {
            if (type.getCode() == code) {
                return type.getDesc();
            }
        }
        return null;
    }

    public static String getTypeByCode(int code) {
        for (AlarmLogHandleTypeEnum type : AlarmLogHandleTypeEnum.values()) {
            if (type.getCode() == code) {
                return type.getType();
            }
        }
        return null;
    }

    public static Integer getCode(String desc) {
        for (AlarmLogHandleTypeEnum type : AlarmLogHandleTypeEnum.values()) {
            if (type.getDesc().equalsIgnoreCase(desc)) {
                return type.getCode();
            }
        }
        return null;
    }

    public static AlarmLogHandleTypeEnum toEnum(int code) {
        for (AlarmLogHandleTypeEnum type : AlarmLogHandleTypeEnum.values()) {
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
