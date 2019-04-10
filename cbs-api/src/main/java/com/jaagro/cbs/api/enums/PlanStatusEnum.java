package com.jaagro.cbs.api.enums;

/**
 * @author @Gao.
 * 计划状态
 */

public enum PlanStatusEnum {
    /**
     * 计划状态类型
     */
    ENTER_CONTRACT(0, "ENTER_CONTRACT", "待录入合同"),
    PARAM_CORRECT(1, "PARAM_CORRECT", "待参数配置"),
    SIGN_CHICKEN(2, "SIGN_CHICKEN", "待上鸡签收"),
    BREEDING(3, "BREEDING", "养殖中"),
    SLAUGHTER_CONFIRM(4, "SLAUGHTER_CONFIRM", "待出栏确认"),
    SLAUGHTER_PLAN(5, "SLAUGHTER_PLAN", "待出栏计划"),
    COMPLETED(6, "COMPLETED", "已完成"),
    CANCEL(7, "CANCEL", "已取消");

    private int code;
    private String type;
    private String desc;

    PlanStatusEnum(int code, String type, String desc) {
        this.code = code;
        this.type = type;
        this.desc = desc;
    }

    public static String getDescByCode(int code) {
        for (PlanStatusEnum type : PlanStatusEnum.values()) {
            if (type.getCode() == code) {
                return type.getDesc();
            }
        }
        return null;
    }

    public static String getTypeByCode(int code) {
        for (PlanStatusEnum type : PlanStatusEnum.values()) {
            if (type.getCode() == code) {
                return type.getType();
            }
        }
        return null;
    }

    public static Integer getCode(String desc) {
        for (PlanStatusEnum type : PlanStatusEnum.values()) {
            if (type.getDesc().equalsIgnoreCase(desc)) {
                return type.getCode();
            }
        }
        return null;
    }

    public static PlanStatusEnum toEnum(int code) {
        for (PlanStatusEnum type : PlanStatusEnum.values()) {
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
