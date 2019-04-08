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
public class ContractPriceSection implements Serializable {
    /**
     * 回收价格区间表id
     */
    private Integer id;

    /**
     * 计划id
     */
    private Integer planId;

    /**
     * 计划合同id
     */
    private Integer contractId;

    /**
     * 回收价格
     */
    private BigDecimal recyclingPrice;

    /**
     * 体重区间下限(包含)
     */
    private BigDecimal weightLower;

    /**
     * 体重区间上限(不包含)
     */
    private BigDecimal weightUpper;

    /**
     *  是否是市场价(0-不是,1-是)
     */
    private Boolean marketPriceFlag;

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