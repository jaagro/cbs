package com.jaagro.cbs.biz.mapper;

import com.jaagro.cbs.api.dto.plan.BreedingPlanCoopDto;
import com.jaagro.cbs.api.dto.technicianapp.BreedingPlanCoopsDto;
import com.jaagro.cbs.api.dto.technicianapp.BreedingPlanPlantsDto;
import com.jaagro.cbs.api.model.BatchPlantCoop;
import com.jaagro.cbs.api.model.BatchPlantCoopExample;
import com.jaagro.cbs.biz.bo.BatchPlantCoopBo;
import com.jaagro.cbs.biz.mapper.base.BaseMapper;
import org.apache.ibatis.annotations.Param;

import javax.annotation.Resource;
import java.util.List;


/**
 * BatchPlantCoopMapperExt接口
 *
 * @author :generator
 * @date :2019/2/21
 */
@Resource
public interface BatchPlantCoopMapperExt extends BaseMapper<BatchPlantCoop, BatchPlantCoopExample> {

    /**
     * 通过批次id获得养殖场id
     *
     * @param planId
     * @return
     */
    List<Integer> listPlantIdByPlanId(@Param("planId") Integer planId);

    /**
     * 批量插入养殖计划关联表
     *
     * @param batchPlantCoops
     */
    void insertBatch(@Param("batchPlantCoops") List<BatchPlantCoop> batchPlantCoops);

    /**
     * 查询可供选择养殖计划鸡舍
     *
     * @param planId
     * @return
     */
    List<BreedingPlanCoopDto> listBreedingPlanCoopsForChoose(@Param("planId") Integer planId);

    /**
     * 查询可供选择养殖计划鸡舍 -技术管理app
     *
     * @param planId
     * @return
     */
    List<BreedingPlanPlantsDto> listPlanPlantsForTechnicianChoose(@Param("planId") Integer planId);

    /**
     * 查询可供选择养殖计划鸡舍 -技术管理app
     *
     * @param plantId
     * @return
     */
    List<BreedingPlanCoopsDto> listPlanCoopsByPlanId(BreedingPlanPlantsDto dto);

    /**
     * 通过鸡舍id得到planId
     *
     * @param coopId
     * @return
     */
    Integer getPlanIdByCoopId(@Param("coopId") Integer coopId);

    /**
     * 根据计划id获取计划养殖场鸡舍信息列表
     *
     * @param planId
     * @return
     */
    List<BatchPlantCoopBo> listPlantCoopInfoByPlanId(@Param("planId") Integer planId);

}