package com.jaagro.cbs.api.dto.order;

import com.jaagro.cbs.api.dto.farmer.ReturnProductDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @description: 农户端采购订单详情
 * @author: @Gao.
 * @create: 2019-03-07 10:56
 **/
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ReturnFarmerPurchaseOrderDetailsDto implements Serializable {
    /**
     * 批次号
     */
    private String batchNo;

    /**
     * 养殖计划id
     */
    private Integer planId;

    /**
     * 订单货物类型（1: 种苗 2: 饲料 3: 药品）
     */
    private Integer productType;

    /**
     * 商品采购单编号
     */
    private String purchaseNo;

    /**
     * 状态(0-待审核,1-审核通过,2－已完成送货 ,3-审核拒绝)
     */
    private String purchaseOrderStatus;

    /**
     * 开单时间
     */
    private Date createTime;

    /**
     * 计划送达时间
     */
    private Date planDeliveryTime;

    /**
     * 待签收时间
     */
    private Date deliveryTime;

    /**
     * 签收时间
     */
    private Date signerTime;

    /**
     *
     */
    private String orderPhase;

    /**
     * 采购订单商品
     */
    private List<ReturnProductDto> returnProductDtos;
}
