package com.jaagro.cbs.api.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author :asus
 * @date :2019/03/21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class BreedingBatchDrug implements Serializable {
    /**
     * 养殖计划药品配置表id
     */
    private Integer id;

    /**
     * 计划id
     */
    private Integer planId;

    /**
     * 批次号
     */
    private String batchNo;

    /**
     * 养殖参数模板药品配置表id
     */
    private Integer standardDrugId;

    /**
     * 商品id
     */
    private Integer productId;

    /**
     * sku编码
     */
    private String skuNo;

    /**
     * 日龄起(包含)
     */
    private Integer dayAgeStart;

    /**
     * 日龄止(包含)
     */
    private Integer dayAgeEnd;

    /**
     * 停药标识(0-否,1-是)
     */
    private Boolean stopDrugFlag;

    /**
     * 该日龄区间天数
     */
    private Integer days;

    /**
     * 千只日喂量
     */
    private BigDecimal feedVolume;

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