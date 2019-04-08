package com.jaagro.cbs.api.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author :gavinwang
 * @date :2019/04/08
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class SalesOrder implements Serializable {
    /**
     * 销售单表id
     */
    private Integer id;

    /**
     * 养殖计划id
     */
    private Integer planId;

    /**
     * 批次号
     */
    private Integer batchNo;

    /**
     * 养殖户id
     */
    private Integer customerId;

    /**
     * 销售单号
     */
    private String salesNo;

    /**
     * 订单货物类型（1: 种苗 2: 饲料 3: 药品）
     */
    private Integer productType;

    /**
     * 产品id
     */
    private Integer productId;

    /**
     * 重量
     */
    private BigDecimal weight;

    /**
     * 数量
     */
    private Integer quantity;

    /**
     * 单位(1-千克,2-只,3-个)
     */
    private Integer unit;

    /**
     * 单价
     */
    private BigDecimal price;

    /**
     * 金额
     */
    private BigDecimal amount;

    /**
     * 状态(0-待审核,1-审核通过,2已完成送货,3-审核拒绝)
     */
    private Integer salesOrderStatus;

    /**
     * 送达时间
     */
    private Date deliveryTime;

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