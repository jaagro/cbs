package com.jaagro.cbs.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author :asus
 * @date :2019/04/19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class BatchInfo implements Serializable {
    /**
     * 批次养殖情况汇总表id
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
     * 起始喂养数量
     */
    private Integer startAmount;

    /**
     * 剩余喂养数量
     */
    private Integer currentAmount;

    /**
     * 上鸡日期
     */
    private Date startDay;

    /**
     * 出栏日期
     */
    private Date finishDay;

    /**
     * 喂养日龄
     */
    private Integer dayAge;

    /**
     * 技术员姓名
     */
    private String technician;

    /**
     * 技术员id
     */
    private Integer technicianId;

    /**
     * 死淘数量
     */
    private Integer deadAmount;

    /**
     * 出栏数量
     */
    private Integer saleAmount;

    /**
     * 喂料量
     */
    private BigDecimal fodderAmount;

    /**
     * 喂料价值成本
     */
    private BigDecimal fodderValue;

    /**
     * 鸡苗成本
     */
    private BigDecimal babychickValue;

    /**
     * 用药成本
     */
    private BigDecimal drugValue;

    /**
     * 成品销售收入
     */
    private BigDecimal saleValue;

    /**
     * 批次利润
     */
    private BigDecimal balance;

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