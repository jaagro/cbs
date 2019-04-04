package com.jaagro.cbs.api.enums;

/**
 * @author @Gao.
 * 商品类型
 */
public enum ProductTypeEnum {
    /**
     * 商品类型
     */
    SPROUT(1, "SPROUT", "种苗"),
    FEED(2, "FEED", "饲料"),
    DRUG(3, "DRUG", "药品"),
    VACCINE(4, "VACCINE", "疫苗");


    private int code;
    private String type;
    private String desc;

    ProductTypeEnum(int code, String type, String desc) {
        this.code = code;
        this.type = type;
        this.desc = desc;
    }

    public static String getDescByCode(int code) {
        for (ProductTypeEnum type : ProductTypeEnum.values()) {
            if (type.getCode() == code) {
                return type.getDesc();
            }
        }
        return null;
    }

    public static String getTypeByCode(int code) {
        for (ProductTypeEnum type : ProductTypeEnum.values()) {
            if (type.getCode() == code) {
                return type.getType();
            }
        }
        return null;
    }

    public static Integer getCode(String desc) {
        for (ProductTypeEnum type : ProductTypeEnum.values()) {
            if (type.getDesc().equalsIgnoreCase(desc)) {
                return type.getCode();
            }
        }
        return null;
    }

    public static ProductTypeEnum toEnum(int code) {
        for (ProductTypeEnum type : ProductTypeEnum.values()) {
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
