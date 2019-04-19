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
public class BatchContract implements Serializable {
    /**
     * 计划合同表id
     */
    private Integer id;

    /**
     * 计划id
     */
    private Integer planId;

    /**
     * 合同编号
     */
    private String contractNumber;

    /**
     * 合同状态(0-未审核,1-正常)
     */
    private Integer contractStatus;

    /**
     * 租户id
     */
    private Integer customerId;

    /**
     * 合同开始时间
     */
    private Date startDate;

    /**
     * 合同结束时间
     */
    private Date endDate;

    /**
     * 签约时间
     */
    private Date contractDate;

    /**
     * 鸡苗数量
     */
    private Integer babychickQuantity;

    /**
     * 鸡苗单价
     */
    private BigDecimal babychickPrice;

    /**
     * 付款方式(1-授信,2-预付,3-现金,4-保证金,5-代扣)
     */
    private Integer paymentMethod;

    /**
     * 预付金额
     */
    private BigDecimal prepaidAmount;

    /**
     * 饲料单价
     */
    private BigDecimal fodderPrice;

    /**
     * 配送方式(1-系统自动,2-专车专送)
     */
    private Integer deliveryMethod;

    /**
     * 回收保护单价（kg）
     */
    private BigDecimal protectionPrice;

    /**
     * 备注
     */
    private String notes;

    /**
     * 是否有效
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