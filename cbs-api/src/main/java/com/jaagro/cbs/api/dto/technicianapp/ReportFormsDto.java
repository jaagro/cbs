package com.jaagro.cbs.api.dto.technicianapp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 报表返回dto
 *
 * @Author baiyiran
 * @Date 2019-04-10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ReportFormsDto implements Serializable {

    private static final long serialVersionUID = 3538281775105066673L;
    /**
     * 租户d
     */
    private Integer tenantId;

    /**
     * 上鸡总量
     */
    private Integer chickenQuantity;

    /**
     * 当前存栏
     */
    private Integer currentAmount;

    /**
     * 总成活率
     */
    private Double totalSurvivalRate;

    /**
     * 计划采购
     */
    private Integer purchaseOrder;

    /**
     * 消耗饲料
     */
    private BigDecimal consumptionOfFeed;

    /**
     * 药品疫苗
     */
    private Integer drugsVaccine;

    /**
     * 总养农户
     */
    private Integer customerAmount;

    /**
     * 总鸡舍数
     */
    private Integer coopAmount;

}
