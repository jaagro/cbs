package com.jaagro.cbs.biz.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jaagro.cbs.api.dto.ValidList;
import com.jaagro.cbs.api.dto.plan.CustomerInfoParamDto;
import com.jaagro.cbs.api.dto.standard.*;
import com.jaagro.cbs.api.enums.BreedingStandardParamEnum;
import com.jaagro.cbs.api.enums.BreedingStandardValueTypeEnum;
import com.jaagro.cbs.api.enums.PlanStatusEnum;
import com.jaagro.cbs.api.enums.SortTypeEnum;
import com.jaagro.cbs.api.model.BreedingStandard;
import com.jaagro.cbs.api.model.BreedingStandardDrug;
import com.jaagro.cbs.api.model.BreedingStandardParameter;
import com.jaagro.cbs.api.model.BreedingStandardParameterExample;
import com.jaagro.cbs.api.service.BreedingPlanService;
import com.jaagro.cbs.api.service.BreedingStandardService;
import com.jaagro.cbs.biz.mapper.BreedingPlanMapperExt;
import com.jaagro.cbs.biz.mapper.BreedingStandardDrugMapperExt;
import com.jaagro.cbs.biz.mapper.BreedingStandardMapperExt;
import com.jaagro.cbs.biz.mapper.BreedingStandardParameterMapperExt;
import com.jaagro.constant.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * 养殖大脑管理
 *
 * @author yj
 * @date :2019/02/22
 */
@Slf4j
@Service
public class BreedingStandardServiceImpl implements BreedingStandardService {

    @Autowired
    private CurrentUserService currentUserService;
    @Autowired
    private BreedingStandardMapperExt breedingStandardMapper;
    @Autowired
    private BreedingStandardParameterMapperExt standardParameterMapper;
    @Autowired
    private BreedingStandardDrugMapperExt breedingStandardDrugMapper;
    @Autowired
    private BreedingPlanMapperExt breedingPlanMapper;
    @Autowired
    private BreedingPlanService breedingPlanService;

    /**
     * 创建养殖模版与参数
     *
     * @param dto
     */
    @Override
    public Integer createBreedingTemplate(CreateBreedingStandardDto dto) {
        log.info("O BreedingStandardServiceImpl.createBreedingTemplate input BreedingStandardDto:{}", dto);
        Integer currentUserId = getCurrentUserId();
        BreedingStandard breedingStandard = new BreedingStandard();
        BeanUtils.copyProperties(dto, breedingStandard);
        breedingStandard.setCreateUserId(currentUserId)
                .setCreateTime(new Date())
                .setEnable(Boolean.TRUE);
        breedingStandardMapper.insertSelective(breedingStandard);
        return breedingStandard.getId();
    }

    /**
     * 修改养殖模版与参数
     *
     * @param dto
     * @return
     */
    @Override
    public Integer updateBreedingTemplate(CreateBreedingStandardDto dto) {
        log.info("O BreedingStandardServiceImpl.updateBreedingTemplate input BreedingStandardDto:{}", dto);
        Integer currentUserId = getCurrentUserId();
        BreedingStandard breedingStandard = new BreedingStandard();
        BeanUtils.copyProperties(dto, breedingStandard);
        breedingStandard.setModifyTime(new Date())
                .setModifyUserId(currentUserId);
        breedingStandardMapper.updateByPrimaryKeySelective(breedingStandard);
        return breedingStandard.getId();
    }

    /**
     * 根据养殖模板ID获取养殖模板详情
     *
     * @param standardId
     * @return
     */
    @Override
    public BreedingStandardDto getBreedingStandardById(Integer standardId) {

        BreedingStandardDto breedingStandardDto = new BreedingStandardDto();
        BreedingStandard breedingStandard = breedingStandardMapper.selectByPrimaryKey(standardId);
        Assert.notNull(breedingStandard, "模板不存在");
        BeanUtils.copyProperties(breedingStandard, breedingStandardDto);

        BreedingStandardParameterExample example = new BreedingStandardParameterExample();
        example.createCriteria().andStandardIdEqualTo(standardId);
        List<BreedingStandardParameter> parameterList = standardParameterMapper.selectByExample(example);


        breedingStandardDto.setStandardParameterDos(parameterList);

        return breedingStandardDto;
    }

    /**
     * 分页查询所有养殖模板
     *
     * @return
     * @author yj
     */
    @Override
    public List<BreedingStandard> listAllBreedingStandard() {
        return breedingStandardMapper.listAllBreedingStandard();
    }

    /**
     * 创建或者更新养殖模板参数
     *
     * @param dto
     */
    @Override
    public void saveOrUpdateParameter(BreedingParameterListDto dto) {
        List<BreedingStandardParameterItemDto> breedingStandardParameterList = dto.getBreedingStandardParameterList();
        if (!CollectionUtils.isEmpty(breedingStandardParameterList)) {
            List<BreedingStandardParameterItemDto> newParameterList = new ArrayList<>();
            List<BreedingStandardParameterItemDto> oldParameterList = new ArrayList<>();
            for (BreedingStandardParameterItemDto itemDto : breedingStandardParameterList) {
                if (itemDto.getId() != null) {
                    oldParameterList.add(itemDto);
                } else {
                    newParameterList.add(itemDto);
                }
            }
            Integer currentUserId = getCurrentUserId();
            if (!CollectionUtils.isEmpty(newParameterList)) {
                List<BreedingStandardParameter> parameterList = new ArrayList<>();
                for (BreedingStandardParameterItemDto itemDto : newParameterList) {
                    BreedingStandardParameter parameter = new BreedingStandardParameter();
                    BeanUtils.copyProperties(dto, parameter);
                    BeanUtils.copyProperties(itemDto, parameter);
                    if (dto.getAlarm() == null) {
                        parameter.setAlarm(Boolean.FALSE);
                    }
                    parameter.setEnable(Boolean.TRUE)
                            .setCreateTime(new Date())
                            .setCreateUserId(currentUserId);
                    parameterList.add(parameter);
                }
                standardParameterMapper.batchInsert(parameterList);
            }
            if (!CollectionUtils.isEmpty(oldParameterList)) {
                List<BreedingStandardParameter> parameterList = new ArrayList<>();
                for (BreedingStandardParameterItemDto itemDto : oldParameterList) {
                    BreedingStandardParameter parameter = new BreedingStandardParameter();
                    BeanUtils.copyProperties(dto, parameter);
                    BeanUtils.copyProperties(itemDto, parameter);
                    parameter
                            .setModifyTime(new Date())
                            .setModifyUserId(currentUserId);
                    parameterList.add(parameter);
                }
                standardParameterMapper.batchUpdateByPrimaryKeySelective(parameterList);
            }
        }
    }

    /**
     * 养殖大脑 养殖参数列表
     *
     * @param criteria
     * @return
     * @author @Gao.
     */
    @Override
    public PageInfo listBreedingParamTemplate(BreedingParamTemplateCriteria criteria) {
        PageHelper.startPage(criteria.getPageNum(), criteria.getPageSize());
        if (criteria.getCustomerInfo() != null) {
            List<Integer> listCustomerIds = breedingPlanService.listCustomerIdsByKeyword(criteria.getCustomerInfo());
            criteria.setCustomerIds(listCustomerIds);
        }
        List<ReturnBreedingParamTemplateDto> returnBreedingParamTemplateDtos = breedingPlanMapper.listBreedingParamTemplate(criteria);
        for (ReturnBreedingParamTemplateDto returnBreedingParamTemplateDto : returnBreedingParamTemplateDtos) {
            if (returnBreedingParamTemplateDto.getPlanStatus() != null) {
                returnBreedingParamTemplateDto
                        .setStrPlanStatus(PlanStatusEnum.getDescByCode(returnBreedingParamTemplateDto.getPlanStatus()));
                if (returnBreedingParamTemplateDto.getCustomerId() != null) {
                    CustomerInfoParamDto customerInfo = breedingPlanService.getCustomerInfo(returnBreedingParamTemplateDto.getCustomerId());
                    if (customerInfo != null) {
                        if (customerInfo.getCustomerName() != null) {
                            returnBreedingParamTemplateDto.setCustomerName(customerInfo.getCustomerName());
                        }
                        if (customerInfo.getCustomerPhone() != null) {
                            returnBreedingParamTemplateDto.setCustomerPhone(customerInfo.getCustomerPhone());
                        }
                    }
                }
            }
        }
        return new PageInfo(returnBreedingParamTemplateDtos);
    }

    /**
     * 查询养殖模板下的参数分类列表
     *
     * @param standardId
     * @return
     */
    @Override
    public List<ParameterTypeDto> listParameterNameByStandardId(Integer standardId) {
        BreedingStandard breedingStandard = breedingStandardMapper.selectByPrimaryKey(standardId);
        Assert.notNull(breedingStandard, "模板不存在");
        BreedingStandardParameterExample example = new BreedingStandardParameterExample();
        example.createCriteria().andStandardIdEqualTo(standardId).andEnableEqualTo(Boolean.TRUE);
        List<BreedingStandardParameter> parameterList = standardParameterMapper.selectByExample(example);
        Set<ParameterTypeDto> initParameterTypeDtoSet = getInitParameterTypeDtoSet(standardId);
        Set<ParameterTypeDto> parameterTypeDtoSet = new HashSet<>();
        Set<ParameterTypeDto> result = new HashSet<>();
        if (!CollectionUtils.isEmpty(parameterList)) {
            for (BreedingStandardParameter parameter : parameterList) {
                // 同一养殖模板下相同参数名称相同参数类型展示顺序一样
                parameterTypeDtoSet.add(new ParameterTypeDto(standardId, parameter.getParamName(), parameter.getParamType(), parameter.getUnit(), parameter.getDisplayOrder()));
            }
        }
        if (!CollectionUtils.isEmpty(parameterTypeDtoSet)) {
            Iterator<ParameterTypeDto> iterator = parameterTypeDtoSet.iterator();
            while (iterator.hasNext()) {
                ParameterTypeDto dto = iterator.next();
                result.add(dto);
                for (ParameterTypeDto init : initParameterTypeDtoSet) {
                    if (!init.getParamName().equals(dto.getParamName()) || !init.getParamType().equals(dto.getParamType())) {
                        result.add(init);
                    }
                }
            }
        } else {
            result.addAll(initParameterTypeDtoSet);
        }
        List<ParameterTypeDto> parameterTypeDtoList = new ArrayList<>(result);
        boolean displayOrderHasNull = false;
        for (ParameterTypeDto parameterTypeDto : parameterTypeDtoList) {
            if (parameterTypeDto.getDisplayOrder() == null) {
                displayOrderHasNull = true;
            }
        }
        // 排序
        if (!displayOrderHasNull) {
            Collections.sort(parameterTypeDtoList, Comparator.comparingInt(ParameterTypeDto::getDisplayOrder));
        }
        return parameterTypeDtoList;
    }

    private Set<ParameterTypeDto> getInitParameterTypeDtoSet(Integer standardId) {
        Set<ParameterTypeDto> result = new HashSet<>();
        ParameterTypeDto parameterTypeDtoWeight = new ParameterTypeDto(standardId, "体重标准", BreedingStandardParamEnum.WEIGHT.getCode(), "克", 1);
        result.add(parameterTypeDtoWeight);
        ParameterTypeDto parameterTypeDtoFeedingWeight = new ParameterTypeDto(standardId, "饲喂重量", BreedingStandardParamEnum.FEEDING_WEIGHT.getCode(), "克", 2);
        result.add(parameterTypeDtoFeedingWeight);
        ParameterTypeDto parameterTypeDtoFeedingFodderNum = new ParameterTypeDto(standardId, "喂料次数", BreedingStandardParamEnum.FEEDING_FODDER_NUM.getCode(), "次", 3);
        result.add(parameterTypeDtoFeedingFodderNum);
        ParameterTypeDto parameterTypeDtoDie = new ParameterTypeDto(standardId, "死淘", BreedingStandardParamEnum.DIE.getCode(), "只", 4);
        result.add(parameterTypeDtoDie);
        return result;
    }

    /**
     * 根据模板id参数名称参数类型查看养殖模板参数
     *
     * @param standardId
     * @param paramName
     * @param paramType
     * @return
     */
    @Override
    public BreedingParameterListDto listParameterListByName(Integer standardId, String paramName, Integer paramType) {
        BreedingStandardParameterExample parameterExample = new BreedingStandardParameterExample();
        parameterExample.createCriteria().andEnableEqualTo(Boolean.TRUE)
                .andStandardIdEqualTo(standardId).andParamTypeEqualTo(paramType).andParamNameEqualTo(paramName);
        parameterExample.setOrderByClause("day_age asc");
        List<BreedingStandardParameter> parameterList = standardParameterMapper.selectByExample(parameterExample);
        BreedingParameterListDto dto = new BreedingParameterListDto();
        if (!CollectionUtils.isEmpty(parameterList)) {
            BreedingStandardParameter parameter = parameterList.get(0);
            dto.setAlarm(parameter.getAlarm())
                    .setNecessary(parameter.getNecessary())
                    .setParamName(paramName)
                    .setParamType(paramType)
                    .setStandardId(standardId)
                    .setStatus(parameter.getStatus())
                    .setUnit(parameter.getUnit())
                    .setValueType(parameter.getValueType())
                    .setDisplayOrder(parameter.getDisplayOrder());
            List<BreedingStandardParameterItemDto> breedingStandardParameterList = new ArrayList<>();
            for (BreedingStandardParameter parameterIn : parameterList) {
                BreedingStandardParameterItemDto itemDto = new BreedingStandardParameterItemDto();
                itemDto.setDayAge(parameterIn.getDayAge())
                        .setId(parameterIn.getId())
                        .setLowerLimit(parameterIn.getLowerLimit())
                        .setUpperLimit(parameterIn.getUpperLimit())
                        .setParamValue(parameterIn.getParamValue())
                        .setThresholdDirection(parameterIn.getThresholdDirection());
                breedingStandardParameterList.add(itemDto);
            }
            dto.setBreedingStandardParameterList(breedingStandardParameterList);
            return dto;
        } else {
            generateInitParamList(dto, paramName, paramType, standardId);
        }
        return dto;
    }

    private void generateInitParamList(BreedingParameterListDto dto, String paramName, Integer paramType, Integer standardId) {
        boolean isInitParam = ("体重标准".equals(paramName) && BreedingStandardParamEnum.WEIGHT.getCode() == paramType)
                || ("饲喂重量".equals(paramName) && BreedingStandardParamEnum.FEEDING_WEIGHT.getCode() == paramType)
                || ("饲喂次数".equals(paramName) && BreedingStandardParamEnum.FEEDING_FODDER_NUM.getCode() == paramType)
                || ("死淘".equals(paramName) && BreedingStandardParamEnum.DIE.getCode() == paramType);
        if (isInitParam) {
            BreedingStandard breedingStandard = breedingStandardMapper.selectByPrimaryKey(standardId);
            if (breedingStandard == null) {
                throw new RuntimeException("模板id=" + standardId + "不存在");
            }
            Integer breedingDays = breedingStandard.getBreedingDays();
            if (breedingDays == null) {
                throw new RuntimeException("喂养天数为空");
            }
            switch (paramName) {
                case "体重标准":
                    dto.setDisplayOrder(1);
                    dto.setUnit("克");
                    break;
                case "饲喂重量":
                    dto.setDisplayOrder(2);
                    dto.setUnit("克");
                    break;
                case "饲喂次数":
                    dto.setDisplayOrder(3);
                    dto.setUnit("次");
                    break;
                case "死淘":
                    dto.setDisplayOrder(4);
                    dto.setUnit("只");
                    break;
                default:
                    break;
            }
            dto.setValueType(BreedingStandardValueTypeEnum.STANDARD_VALUE.getCode())
                    .setStatus(1)
                    .setStandardId(standardId)
                    .setParamName(paramName)
                    .setParamType(paramType)
                    .setNecessary(Boolean.TRUE)
                    .setAlarm(Boolean.TRUE);
            List<BreedingStandardParameterItemDto> breedingStandardParameterList = new ArrayList<>();
            for (int i = 0; i < breedingDays; i++) {
                BreedingStandardParameterItemDto itemDto = new BreedingStandardParameterItemDto();
                itemDto.setDayAge(i + 1);
                breedingStandardParameterList.add(itemDto);
            }
            dto.setBreedingStandardParameterList(breedingStandardParameterList);
        }
    }

    /**
     * 根据模板id查询药品配置信息
     *
     * @param standardId
     * @return
     */
    @Override
    public List<BreedingStandardDrugDto> listBreedingStandardDrugs(Integer standardId) {
        return breedingStandardDrugMapper.listBreedingStandardDrugs(standardId);
    }

    /**
     * 更新参数排序
     *
     * @param dto
     * @return
     */
    @Override
    public List<ParameterTypeDto> changeParameterDisplayOrder(ChangeParameterDisplayOrderDto dto) {
        List<ParameterTypeDto> result = new ArrayList<>();
        Integer sortType = dto.getSortType();
        if (!SortTypeEnum.UP.equals(sortType) && !SortTypeEnum.DOWN.equals(sortType)) {
            throw new RuntimeException("排序类型不正确");
        }
        List<ParameterTypeDto> parameterTypeDtoList = listParameterNameByStandardId(dto.getStandardId());
        if (CollectionUtils.isEmpty(parameterTypeDtoList)) {
            throw new RuntimeException("养殖模板参数为空");
        }
        List<BreedingStandardParameter> needUpdateDisplayOrderList = new ArrayList<>();
        for (ParameterTypeDto parameterTypeDto : parameterTypeDtoList) {
            if (dto.getParamName().equals(parameterTypeDto.getParamName()) && dto.getParamType().equals(parameterTypeDto.getParamType())) {
                if (SortTypeEnum.UP.equals(sortType)) {
                    if (dto.getDisplayOrder() <= 1) {
                        break;
                    }
                    if (dto.getParamType().equals(parameterTypeDto.getParamType()) && dto.getParamName().equals(parameterTypeDto.getParamName())) {
                        parameterTypeDto.setDisplayOrder(parameterTypeDto.getDisplayOrder() - 1);
                    }
                    if (dto.getDisplayOrder() < parameterTypeDto.getDisplayOrder() || (dto.getDisplayOrder() == parameterTypeDto.getDisplayOrder() - 1)) {
                        parameterTypeDto.setDisplayOrder(parameterTypeDto.getDisplayOrder() + 1);
                    }
                } else {
                    if (dto.getDisplayOrder() >= parameterTypeDtoList.size()) {
                        break;
                    }
                    if (dto.getParamType().equals(parameterTypeDto.getParamType()) && dto.getParamName().equals(parameterTypeDto.getParamName())) {
                        parameterTypeDto.setDisplayOrder(parameterTypeDto.getDisplayOrder() + 1);
                    }
                    if (dto.getDisplayOrder() > parameterTypeDto.getDisplayOrder() || (dto.getDisplayOrder() == parameterTypeDto.getDisplayOrder() + 1)) {
                        parameterTypeDto.setDisplayOrder(parameterTypeDto.getDisplayOrder() - 1);
                    }
                }
                BreedingStandardParameterExample parameterExample = new BreedingStandardParameterExample();
                List<BreedingStandardParameter> parameterList = standardParameterMapper.selectByExample(parameterExample);
                needUpdateDisplayOrderList.addAll(parameterList);
            }
        }
        standardParameterMapper.batchUpdateByPrimaryKeySelective(needUpdateDisplayOrderList);
        return result;
    }


    /**
     * 获取养殖模板基本信息
     *
     * @param id
     * @return
     */
    @Override
    public BreedingStandard getStandardBaseInfoById(Integer id) {
        return breedingStandardMapper.selectByPrimaryKey(id);
    }

    /**
     * 删除养殖模板参数
     *
     * @param dto
     */
    @Override
    public void delBreedingStandardParam(DelBreedingStandardParamDto dto) {
        standardParameterMapper.delByCondition(dto);
    }

    /**
     * 养殖模板药品配置
     *
     * @param drugList
     */
    @Override
    public void configurationDrugs(ValidList<BreedingStandardDrugListDto> drugList) {
        if (!CollectionUtils.isEmpty(drugList)) {
            Integer standardId = drugList.get(0).getStandardId();
            Integer currentUserId = getCurrentUserId();
            breedingStandardDrugMapper.delByStandardId(standardId);
            List<BreedingStandardDrug> standardDrugList = new ArrayList<>();
            for (BreedingStandardDrugListDto dto : drugList) {
                List<BreedingStandardDrugItemDto> drugItemVoList = dto.getBreedingStandardDrugItemVoList();
                if (CollectionUtils.isEmpty(drugItemVoList)) {
                    BreedingStandardDrug drug = generateStandardDrug(dto, currentUserId);
                    standardDrugList.add(drug);
                } else {
                    for (BreedingStandardDrugItemDto itemDto : drugItemVoList) {
                        BreedingStandardDrug drug = generateStandardDrug(dto, currentUserId);
                        drug.setFeedVolume(itemDto.getFeedVolume())
                                .setProductId(itemDto.getProductId())
                                .setSkuNo(itemDto.getSkuNo());
                        standardDrugList.add(drug);
                    }
                }
            }
            breedingStandardDrugMapper.batchInsert(standardDrugList);
        } else {
            throw new RuntimeException("药品配置信息列表为空");
        }
    }

    /**
     * 逻辑删除养殖参数模板
     *
     * @param standardId
     */
    @Override
    public void delBreedingStandard(Integer standardId) {
        BreedingStandard breedingStandard = breedingStandardMapper.selectByPrimaryKey(standardId);
        if (breedingStandard == null) {
            throw new RuntimeException("养殖模板id=" + standardId + "不存在");
        }
        breedingStandardMapper.deleteByPrimaryKey(standardId);
        standardParameterMapper.deleteByStandardId(standardId);
    }

    private BreedingStandardDrug generateStandardDrug(BreedingStandardDrugListDto dto, Integer currentUserId) {
        BreedingStandardDrug drug = new BreedingStandardDrug();
        drug.setBreedingStandardId(dto.getStandardId())
                .setCreateUserId(currentUserId)
                .setCreateTime(new Date())
                .setEnable(Boolean.TRUE)
                .setDayAgeEnd(dto.getDayAgeEnd())
                .setDayAgeStart(dto.getDayAgeStart());
        if (dto.getDayAgeEnd() != null && dto.getDayAgeStart() != null) {
            drug.setDays(dto.getDayAgeEnd() - dto.getDayAgeStart() + 1);
        }
        if (dto.getStopDrugFlag() == null) {
            drug.setStopDrugFlag(Boolean.FALSE);
        } else {
            drug.setStopDrugFlag(dto.getStopDrugFlag());
        }
        return drug;
    }

    private Integer getCurrentUserId() {
        UserInfo userInfo = currentUserService.getCurrentUser();
        return userInfo == null ? null : userInfo.getId();
    }
}
