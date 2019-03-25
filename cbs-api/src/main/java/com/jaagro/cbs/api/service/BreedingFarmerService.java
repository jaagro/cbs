package com.jaagro.cbs.api.service;

import com.github.pagehelper.PageInfo;
import com.jaagro.cbs.api.dto.farmer.*;
import com.jaagro.cbs.api.dto.order.*;
import com.jaagro.cbs.api.dto.plan.ReturnCustomerInfoDto;

import java.util.Date;


/**
 * 农户端app 相关api
 *
 * @author @Gao.
 */
public interface BreedingFarmerService {

    /**
     * 农户端app 首页数据统计
     *
     * @return
     * @author @Gao.
     */
    ReturnBreedingFarmerIndexDto breedingFarmerIndexStatistical();

    /**
     * 农户端app 首页
     *
     * @return
     * @author @Gao.
     */
    PageInfo breedingFarmerIndex(BreedingBatchParamDto dto);

    /**
     * 农户端技术询问
     *
     * @param dto
     * @author @Gao.
     */
    void technicalInquiries(CreateTechnicalInquiriesDto dto);

    /**
     * 农户端个人中心
     *
     * @return
     * @author @Gao.
     */
    FarmerPersonalCenterDto farmerPersonalCenter();

    /**
     * 商品采购列表
     *
     * @return
     * @author @Gao.
     */
    PageInfo listPurchaseOrder(PurchaseOrderListParamDto dto);

    /**
     * 农户端采购订单详情
     *
     * @param purchaseOrderId
     * @return
     * @author @Gao.
     */
    ReturnFarmerPurchaseOrderDetailsDto purchaseOrderDetails(Integer purchaseOrderId);

    /**
     * 更新采购订单状态
     *
     * @param dto
     */
    void updatePurchaseOrder(UpdatePurchaseOrderParamDto dto);


    /**
     * 农户端上鸡计划列表
     *
     * @param dto
     * @return
     * @author @Gao.
     */
    PageInfo listPublishedChickenPlan(BreedingBatchParamDto dto);

    /**
     * 技术询问列表
     *
     * @param dto
     * @return
     */
    PageInfo listTechnicalInquiries(BreedingBatchParamDto dto);

    /**
     * 获取客户名称与id
     *
     * @return
     */
    ReturnCustomerInfoDto getCustomerInfo();

    /**
     * 根据上鸡计划时间获取当前日龄
     *
     * @param beginDate
     * @return
     * @author: @Gao.
     */
    Integer getDayAge(Date beginDate) throws Exception;

}
