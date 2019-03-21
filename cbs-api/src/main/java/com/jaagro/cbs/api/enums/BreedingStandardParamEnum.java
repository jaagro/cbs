package com.jaagro.cbs.api.enums;

/**
 * @author @Gao.
 * 养殖模板参数类型
 */

public enum BreedingStandardParamEnum {
    /**
     * 养殖模板参数类型（10-温度,11-湿度,12-光照强度,13-光照时间,14-氮气,
     * 15-二氧化碳,16-通风,17-负压值,20-饲喂(次/日),21-饲喂(克/日),
     * 22-体重(克/只/日),23-死淘(只),24-药品/疫苗)
     */
    TEMPERATURE(10, "TEMPERATURE", "温度"),
    HUMIDITY(11, "HUMIDITY", "湿度"),
    LIGHT_INTENSITY(12, "LIGHT_INTENSITY", "光照强度"),
    LIGHT_TIME(13, "LIGHT_TIME", "光照时间"),
    NITROGEN(14, "NITROGEN", "氮气"),
    CARBON_DIOXIDE(15, "CARBON_DIOXIDE", "二氧化碳"),
    VENTILATION(16, "VENTILATION", "通风"),
    NEGATIVE_PRESSURE(17, "NEGATIVE_PRESSURE", "负压值"),
    DENSITY(18,"DENSITY","密度"),
    FEEDING_FODDER_NUM(20, "FEEDING_FODDER_NUM", "饲喂次数(次/日)"),
    FEEDING_WATER_NUM(21, "FEEDING_WATER_NUM", "喂水次数(次/日)"),
    FEEDING_WEIGHT(22, "FEEDING_WEIGHT", "饲喂重量(克/只/日)"),
    WEIGHT(23, "WEIGHT", "体重标准(克/只/日)"),
    DIE(24, "DIE", "死淘(只)"),
    DRUG(25, "DRUG", "药品/疫苗");
    private int code;
    private String type;
    private String desc;

    BreedingStandardParamEnum(int code, String type, String desc) {
        this.code = code;
        this.type = type;
        this.desc = desc;
    }

    public static String getDescByCode(int code) {
        for (BreedingStandardParamEnum type : BreedingStandardParamEnum.values()) {
            if (type.getCode() == code) {
                return type.getDesc();
            }
        }
        return null;
    }

    public static String getTypeByCode(int code) {
        for (BreedingStandardParamEnum type : BreedingStandardParamEnum.values()) {
            if (type.getCode() == code) {
                return type.getType();
            }
        }
        return null;
    }

    public static Integer getCode(String desc) {
        for (BreedingStandardParamEnum type : BreedingStandardParamEnum.values()) {
            if (type.getDesc().equalsIgnoreCase(desc)) {
                return type.getCode();
            }
        }
        return null;
    }

    public static BreedingStandardParamEnum toEnum(int code) {
        for (BreedingStandardParamEnum type : BreedingStandardParamEnum.values()) {
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
