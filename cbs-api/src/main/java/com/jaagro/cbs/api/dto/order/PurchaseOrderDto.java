package com.jaagro.cbs.api.dto.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @description: 采购订单列表对象
 * @author: @Gao.
 * @create: 2019-03-06 13:59
 **/
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrderDto implements Serializable {

    /**
     * 养殖采购订单表id
     */
    private Integer id;

    /**
     * 商品采购单编号
     */
    private String purchaseNo;

    /**
     * 订单货物类型（1: 种苗 2: 饲料 3: 药品）
     */
    private Integer productType;

    /**
     * 订单货物类型（1: 种苗 2: 饲料 3: 药品）
     */
    private String strProductType;

    /**
     * 采购数量 采购重量
     */
    private BigDecimal quantity;

    /**
     * 单位(1-千克｜2-只｜3-个｜ 4-吨等)
     */
    private String strUnit;

    /**
     * 订单阶段：1-第一阶段；2-第二阶段；3-第三阶段
     */
    private Integer orderPhase;

    /**
     * 状态(1-已下单 ,2－待送达 ,3-已签收)
     */
    private Integer purchaseOrderStatus;

    /**
     * 状态(0-待审核,1-审核通过,2－已完成送货 ,3-审核拒绝)
     */
    private String strPurchaseOrderStatus;

    /**
     * 到货时间
     */
    private Date deliveryTime;

    /**
     *
     */
    private String strOrderPhase;
}
