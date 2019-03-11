package com.jaagro.cbs.biz.mapper;

import javax.annotation.Resource;
import com.jaagro.cbs.api.model.PurchaseOrderItems;
import com.jaagro.cbs.api.model.PurchaseOrderItemsExample;
import com.jaagro.cbs.biz.mapper.base.BaseMapper;


/**
 * PurchaseOrderItemsMapperExt接口
 * @author :generator
 * @date :2019/3/9
 */
@Resource
public interface PurchaseOrderItemsMapperExt extends BaseMapper<PurchaseOrderItems,PurchaseOrderItemsExample> {

    /**
     * 根据采购订单删除订单明细
     * @param purchaseOrderId
     * @return
     */
    int deleteByOrderId(Integer purchaseOrderId);
}