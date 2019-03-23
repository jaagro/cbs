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
public class BatchCoopDaily implements Serializable {
    /**
     * 鸡舍每日养殖汇总表id
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
     * 起始喂养数量
     */
    private Integer startAmount;

    /**
     * 剩余喂养数量
     */
    private Integer currentAmount;

    /**
     * 死淘数量
     */
    private Integer deadAmount;

    /**
     * 出栏数量
     */
    private Integer saleAmount;

    /**
     * 当日累积喂料次数
     */
    private Integer fodderTimes;

    /**
     * 当日累积喂料量
     */
    private BigDecimal fodderAmount;

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

    private Integer modifyUserId;

    private static final long serialVersionUID = 1L;
}