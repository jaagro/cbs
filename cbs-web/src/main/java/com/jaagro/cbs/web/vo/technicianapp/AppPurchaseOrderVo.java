package com.jaagro.cbs.web.vo.technicianapp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @description: 采购订单列表对象
 * @author: @gavin
 * @create: 2019-04-09 13:59
 **/
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class AppPurchaseOrderVo implements Serializable {

    /**
     * 养殖采购订单表id
     */
    private Integer id;

    /**
     * 上鸡时间
     */
    private Date planTime;

    /**
     * 计划上鸡数量
     */
    private Integer planChickenQuantity;
    /**
     * 客户名称
     */
    private String customerName;
    /**
     * 批次号
     */
    private String batchNo;

    /**
     * 商品采购单编号
     */
    private String purchaseNo;
    /**
     * 状态(1-已下单 ,2－待送达 ,3-已签收)
     */
    private String strPurchaseOrderStatus;
    /**
     * 订单货物类型（1: 种苗 2: 饲料 3: 药品）
     */
    private String strProductType;
    /**
     * 采购名称
     */
    private String purchaseName;
    /**
     * 计划送达时间
     */
    private Date planDeliveryTime;

    private List<AppPurchaseOrderItemsVo> itemsVoList;
}
