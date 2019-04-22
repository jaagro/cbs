package com.jaagro.cbs.api.service;

import com.jaagro.cbs.api.dto.plant.*;
import com.jaagro.cbs.api.model.Plant;

import java.util.List;
import java.util.Map;

/**
 * 养殖基本资料server，包含养殖场、图片、鸡舍等api都在这个里
 *
 * @author tonyZheng
 * @date 2019-02-21 14:28
 */
public interface BreedingPlantService {

    /**
     * 新增
     *
     * @param plantDto
     * @return
     */
    Boolean createPlant(CreatePlantDto plantDto);

    /**
     * 修改
     *
     * @param plantDto
     * @return
     */
    Map<String, Object> updatePlant(UpdatePlantDto plantDto);

    /**
     * 通过养殖id获取养殖场详情
     *
     * @param id
     * @return
     */
    ReturnPlantDto getPlantDetailsById(Integer id);

    /**
     * 通过养殖户id获取养殖场列表
     *
     * @param customerId
     * @return
     */
    List<ReturnPlantDto> listPlantByCustomerId(Integer customerId);

    /**
     * 根据养殖计划id 获取养殖场信息
     *
     * @param plantId
     * @return
     */
    List<Plant> listPlantInfoByPlanId(Integer plantId);

    /**
     * 新增鸡舍
     *
     * @param coopDto
     * @return
     */
    Map<String, Object> createCoop(CreateCoopDto coopDto);

    /**
     * 通过养殖场id获得鸡舍列表
     *
     * @param plantId
     * @return
     */
    List<ReturnCoopDto> listCoopByPlantId(Integer plantId);

    /**
     * 逻辑删除鸡舍
     *
     * @param coopId
     */
    void deleteCoop(Integer coopId);

    /**
     * 通过养殖户id获取养殖场列表
     *
     * @param
     * @return
     * @author @Gao.
     */
    List<ReturnPlantDto> listPlantAndCoopByPlantIds(List<Integer> planIds);

    /**
     * 查询当前客户空闲的养殖场
     *
     * @param customerId
     * @return
     */
    List<ReturnBasicPlantDto> listFreePlantByCustomerId(Integer customerId);

    /**
     * 修改鸡舍
     *
     * @param coopDto
     * @return
     */
    Map<String, Object> updateCoop(UpdateCoopDto coopDto);
}
