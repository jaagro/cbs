package com.jaagro.cbs.api.service;


import com.github.pagehelper.PageInfo;
import com.jaagro.cbs.api.dto.farmer.*;
import com.jaagro.cbs.api.dto.order.AccumulationPurchaseOrderParamDto;
import com.jaagro.cbs.api.dto.plan.*;
import com.jaagro.cbs.api.model.BreedingPlan;

import java.util.List;


/**
 * 养殖计划管理
 *
 * @author @Gao.
 */
public interface BreedingPlanService {
    /**
     * 创建养殖计划
     *
     * @param dto
     */
    void createBreedingPlan(CreateBreedingPlanDto dto);

    /**
     * 录入合同
     *
     * @param createPlanContractDto
     * @author yj
     */
    void createPlanContract(CreatePlanContractDto createPlanContractDto);

    /**
     * 分页查询养殖计划
     *
     * @param dto
     * @return
     */
    PageInfo listBreedingPlan(BreedingPlanParamDto dto);

    /**
     * 更新养殖计划
     *
     * @param dto
     */
    void upDateBreedingPlanDetails(UpdateBreedingPlanDto dto);

    /**
     * 养殖计划详情
     *
     * @param planId
     * @return
     */
    ReturnBreedingPlanDetailsDto breedingPlanDetails(Integer planId);

    /**
     * 待上鸡签收详情
     *
     * @param planId
     * @return
     */
    ReturnChickenSignDetailsDto chickenSignDetails(Integer planId);

    /**
     * 养殖中详情
     *
     * @param planId
     */
    ReturnBreedingDetailsDto breedingDetails(Integer planId);

    /**
     * 获取养殖计划鸡舍信息
     *
     * @param planId
     * @return
     * @author yj
     */
    List<BreedingPlanCoopDto> listBreedingPlanCoopsForChoose(Integer planId);

    /**
     * 养殖计划参数配置
     *
     * @param dto
     * @author yj
     */
    void breedingPlanParamConfiguration(BreedingPlanParamConfigurationDto dto);

    /**
     * 根据计划id获取当前日龄
     *
     * @param planId
     * @return
     */
    long getDayAge(Integer planId) throws Exception;

    /**
     * 养殖过程批次参数
     *
     * @param planId
     * @param coopId
     * @param dayAge
     * @param strDate
     * @return
     * @throws Exception if has error(异常说明)
     * @author yj
     */
    BreedingBatchParamListDto getBreedingBatchParamForApp(Integer planId, Integer coopId, Integer dayAge, String strDate) throws Exception;

    /**
     * 获取农户某个计划某个鸡舍每日应喂药列表
     *
     * @param planId
     * @param coopId
     * @return
     * @author yj
     */
    List<BreedingRecordItemsDto> listBreedingRecordDrug(Integer planId, Integer coopId);

    /**
     * 农户端上传养殖记录
     *
     * @param createBreedingRecordDto
     * @author yj
     */
    void uploadBreedingRecord(CreateBreedingRecordDto createBreedingRecordDto);

    /**
     * 养殖页上鸡计划列表查询
     *
     * @param dto
     * @return
     */
    PageInfo<BreedingPlanDetailDto> listBreedingBatchForFarmer(BreedingBatchParamDto dto);

    /**
     * 根据客户名称 手机号码 模糊搜索 获取客户id集合
     *
     * @param keyword
     * @return
     */
    List<Integer> listCustomerIdsByKeyword(String keyword);

    /**
     * 根据客户id获取客户信息
     *
     * @param customerId
     * @return
     */

    CustomerInfoParamDto getCustomerInfo(Integer customerId);


    /**
     * 累计采购订单明细
     *
     * @param purchaseOrderId
     * @returnr
     */
    AccumulationPurchaseOrderParamDto accumulationPurchaseOrderItems(Integer purchaseOrderId);

    /**
     * 根据技术员ID查找该技术员负责的所有养殖计划
     *
     * @param technicianId
     * @return
     */
    List<BreedingPlan> listBreedingPlanByTechnicianId(Integer technicianId);

    /**
     * 根据技术员ID查找养殖计划分页
     *
     * @param dto
     * @return
     */
    PageInfo<BreedingPlanDetailDto> listBreedingBatchForTechnician(BreedingBatchParamDto dto);
}
