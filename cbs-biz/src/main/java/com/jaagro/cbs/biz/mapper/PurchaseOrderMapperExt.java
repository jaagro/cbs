package com.jaagro.cbs.biz.mapper;

import javax.annotation.Resource;

import com.jaagro.cbs.api.dto.supplychain.PurchaseOrderManageCriteria;
import com.jaagro.cbs.api.dto.supplychain.ReturnPurchaseOrderManageDto;
import com.jaagro.cbs.api.model.PurchaseOrder;
import com.jaagro.cbs.api.model.PurchaseOrderExample;
import com.jaagro.cbs.biz.bo.PurchaseOrderBo;
import com.jaagro.cbs.biz.mapper.base.BaseMapper;

import java.math.BigDecimal;
import java.util.List;


/**
 * PurchaseOrderMapperExt接口
 * Ø
 *
 * @author :generator
 * @date :2019/2/21
 */
@Resource
public interface PurchaseOrderMapperExt extends BaseMapper<PurchaseOrder, PurchaseOrderExample> {

    /**
     * 查询要删除的订单
     *
     * @param orderBo
     * @return
     */
    List<Integer> queryPurchaseOrderByCondition(PurchaseOrderBo orderBo);

    /**
     * @param orderBo
     * @return
     */
    int deleteByCriteria(PurchaseOrderBo orderBo);

    /**
     * 供应链采购订单
     *
     * @param criteria
     * @return
     */
    List<ReturnPurchaseOrderManageDto> listPurchasingManagement(PurchaseOrderManageCriteria criteria);

    /**
     * 根据养殖计划id获取已经签收的饲料
     *
     * @param planId
     * @return
     */
    BigDecimal getTotalSignedFoodByPlanId(Integer planId);

}