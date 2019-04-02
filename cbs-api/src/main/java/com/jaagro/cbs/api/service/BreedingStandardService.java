package com.jaagro.cbs.api.service;


import com.github.pagehelper.PageInfo;
import com.jaagro.cbs.api.dto.ValidList;
import com.jaagro.cbs.api.dto.standard.*;
import com.jaagro.cbs.api.dto.standard.*;
import com.jaagro.cbs.api.model.BreedingStandard;
import com.jaagro.cbs.api.model.BreedingStandardParameter;
import org.jboss.logging.Param;

import java.util.List;
import java.util.Map;

/**
 * 养殖大脑管理
 *
 * @author yj
 * @date :2019/02/22
 */
public interface BreedingStandardService {
    /**
     * 创建养殖模版与参数
     *
     * @param dto
     * @return
     */
    Integer createBreedingTemplate(CreateBreedingStandardDto dto);

    /**
     * 修改养殖模版与参数
     *
     * @param dto
     * @return
     */
    Integer updateBreedingTemplate(CreateBreedingStandardDto dto);

    /**
     * 根据养殖模板ID获取养殖模板详情
     *
     * @param id
     * @return
     */
    BreedingStandardDto getBreedingStandardById(Integer id);

    /**
     * 查询所有养殖模板
     *
     * @return
     * @author yj
     */
    List<BreedingStandard> listAllBreedingStandard();

    /**
     * 创建或者更新养殖模板参数
     * @param dto
     */
    Map<String,Object> saveOrUpdateParameter(BreedingParameterListDto dto);

    /**
     * 养殖大脑 养殖参数列表
     *
     * @param criteria
     * @return
     */
   PageInfo listBreedingParamTemplate(BreedingParamTemplateCriteria criteria);

    /**
     * 查询养殖模板下的参数分类列表
     * @param standardId
     * @return
     */
    List<ParameterTypeDto> listParameterNameByStandardId(Integer standardId);

    /**
     * 根据模板id参数名称参数类型查看养殖模板参数
     * @param standardId
     * @param paramName
     * @param paramType
     * @return
     */
    BreedingParameterListDto listParameterListByName(Integer standardId, String paramName, Integer paramType);

    /**
     * 根据模板id查询药品配置信息
     * @param standardId
     * @return
     */
    List<BreedingStandardDrugDto> listBreedingStandardDrugs(Integer standardId);

    /**
     * 更新参数排序
     * @param dto
     * @return
     */
    List<ParameterTypeDto> changeParameterDisplayOrder(ChangeParameterDisplayOrderDto dto);

    /**
     * 获取养殖模板基本信息
     * @param id
     * @return
     */
    BreedingStandard getStandardBaseInfoById(Integer id);

    /**
     * 删除养殖模板参数
     * @param dto
     */
    void delBreedingStandardParam(DelBreedingStandardParamDto dto);

    /**
     * 养殖模板药品配置
     * @param drugList
     */
    void configurationDrugs(ValidList<BreedingStandardDrugListDto> drugList);

    /**
     * 逻辑删除养殖参数模板
     * @param standardId
     */
    void delBreedingStandard(Integer standardId);

    /**
     * 养殖计划参数配置可选择养殖模板列表
     * @return
     */
    List<BreedingStandard> listBreedingStandardForParamConfiguration();
}
