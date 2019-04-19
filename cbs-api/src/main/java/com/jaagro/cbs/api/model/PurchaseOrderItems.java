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
 * @date :2019/04/19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class PurchaseOrderItems implements Serializable {
    private Integer id;

    /**
     * 采购订单id
     */
    private Integer purchaseOrderId;

    /**
     * 产品id
     */
    private Integer productId;

    /**
     * 购买数量
     */
    private BigDecimal quantity;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 是否有效(0-无效,1-有效)
     */
    private Boolean enable;

    /**
     * 跟产品表的包装单位一致(1-瓶,2-袋,3-只,4-桶,5-盒, 6-吨)
     */
    private Integer unit;

    /**
     * 单价
     */
    private BigDecimal unitPrice;

    /**
     * 价格=quantity * prodcuct.purchase_price
     */
    private BigDecimal totalPrice;

    private static final long serialVersionUID = 1L;
}