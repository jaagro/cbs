package com.jaagro.cbs.api.dto.order;

import com.jaagro.cbs.api.dto.farmer.ReturnProductDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @description: 采购预置订单详情
 * @author: @Gao.
 * @create: 2019-03-15 09:51
 **/
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ReturnPurchaseOrderPresetDetailsDto implements Serializable {

    /**
     * 批次号
     */
    private String batchNo;

    /**
     * 计划上鸡数量
     */
    private Integer planChickenQuantity;

    /**
     * 订单货物类型（1: 种苗 2: 饲料 3: 药品）
     */
    private Integer productType;

    /**
     * 上鸡时间
     */
    private Date planTime;

    /**
     * 商品采购单编号
     */
    private String purchaseNo;

    /**
     * 状态(0-待审核,1-审核通过,2－已完成送货 ,3-审核拒绝)
     */
    private String purchaseOrderStatus;
    /**
     * 计划送达时间
     */
    private Date planDeliveryTime;

    /**
     * 开单时间
     */
    private Date createTime;

    /**
     * 待送达时间
     */
    private Date deliveryTime;

    /**
     * 签收时间
     */
    private Date signerTime;

    /**
     * 养殖户名称
     */
    private String customerName;

    /**
     * 养殖户电话
     */
    private String customerPhone;

    /**
     * 养殖户地址
     */
    private String customerAddress;
    /**
     * 采购订单商品
     */
    private List<ReturnProductDto> returnProductDtos;
}
