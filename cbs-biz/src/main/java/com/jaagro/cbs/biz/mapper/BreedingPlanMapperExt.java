package com.jaagro.cbs.biz.mapper;

import com.jaagro.cbs.api.dto.farmer.BreedingPlanDetailDto;
import com.jaagro.cbs.api.dto.farmer.ReturnBreedingBatchDetailsDto;
import com.jaagro.cbs.api.dto.order.PurchaseOrderPresetCriteriaDto;
import com.jaagro.cbs.api.dto.order.ReturnPurchaseOrderPresetDto;
import com.jaagro.cbs.api.dto.plan.BreedingPlanParamDto;
import com.jaagro.cbs.api.dto.plan.ReturnBreedingPlanDto;
import com.jaagro.cbs.api.dto.standard.BreedingParamTemplateCriteria;
import com.jaagro.cbs.api.dto.standard.ReturnBreedingParamTemplateDto;
import com.jaagro.cbs.api.model.BreedingPlan;
import com.jaagro.cbs.api.model.BreedingPlanExample;
import com.jaagro.cbs.biz.mapper.base.BaseMapper;
import org.apache.ibatis.annotations.Param;

import javax.annotation.Resource;
import java.util.List;


/**
 * BreedingPlanMapperExt接口
 *
 * @author :generator
 * @date :2019/2/21
 */
@Resource
public interface BreedingPlanMapperExt extends BaseMapper<BreedingPlan, BreedingPlanExample> {

    /**
     * 查询养殖列表信息
     *
     * @param dto
     * @return
     */
    List<ReturnBreedingPlanDto> listBreedingPlan(BreedingPlanParamDto dto);

    /**
     * 根据客户id 查询当前客户养殖批次详情
     *
     * @param customerId
     * @return
     */
    List<ReturnBreedingBatchDetailsDto> listBreedingPlanByCustomerId(@Param("customerId") Integer customerId);

    /**
     * 根据客户id 查询当前客户所有养殖批次详情
     *
     * @param customerId
     * @return
     */
    List<ReturnBreedingBatchDetailsDto> listAllBreedingPlanByCustomerId(@Param("customerId") Integer customerId);

    /**
     * 根据当前客户id 查询所有养殖计划id 集合
     *
     * @param customerId
     * @return
     */
    List<Integer> listBreedingPlanIdByCustomerId(@Param("customerId") Integer customerId);

    /**
     * 根据客户id查询
     *
     * @param customerId
     * @return
     */
    List<BreedingPlanDetailDto> listBreedingBatchForFarmer(@Param("customerId") Integer customerId);

    /**
     * @param dto
     * @return
     * @author @Gao.
     */
    List<ReturnPurchaseOrderPresetDto> listPurchaseOrderPreset(PurchaseOrderPresetCriteriaDto dto);

    /**
     * 养殖大脑 养殖参数列表
     *
     * @param criteria
     * @return
     */
    List<ReturnBreedingParamTemplateDto> listBreedingParamTemplate(BreedingParamTemplateCriteria criteria);

    /**
     * 查看可能需要从养殖中到待出栏确认的养殖计划
     *
     * @return
     */
    List<BreedingPlan> listToSlaughterConfirm();

    /**
     * 查询今日创建的、未提交喂料记录的批次
     *
     * @return
     */
    List<Integer> listTodayPlanInteger();

    /**
     * 过期的养殖计划单
     *
     * @return
     */
    List<BreedingPlan> listBreedingPlanOverdue();

    /**
     *
     * 批量更新养殖计划超时状态了
     * @param planIds
     */
    void batchUpdateBreedingPlanStatus(@Param("planIds") List<Integer> planIds);

}