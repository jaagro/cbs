package com.jaagro.cbs.api.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author :gaoxin
 * @date :2019/04/04
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class BreedingRecord implements Serializable {
    /**
     * 批次养殖记录表id
     */
    private Integer id;

    /**
     * 养殖计划id
     */
    private Integer planId;

    /**
     * 批次号
     */
    private String batchNo;

    /**
     * 鸡舍id
     */
    private Integer coopId;

    /**
     * 日龄
     */
    private Integer dayAge;

    /**
     * 喂养类型（1-投料 2-喂药 3-通风 4-死淘 5-喂水。。。。）
     */
    private Integer recordType;

    /**
     * 喂养数量
     */
    private BigDecimal breedingValue;

    /**
     * 计量单位
     */
    private String unit;

    /**
     * 饲喂时间
     */
    private Date breedingTime;

    /**
     * 备注
     */
    private String notes;

    /**
     * 是否有效(0-无效,1-有效)
     */
    private Boolean enable;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人
     */
    private Integer createUserId;

    /**
     * 更新时间
     */
    private Date modifyTime;

    /**
     * 更新人
     */
    private Integer modifyUserId;

    private static final long serialVersionUID = 1L;
}