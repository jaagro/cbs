package com.jaagro.cbs.biz.service.impl;

import com.jaagro.cbs.api.dto.standard.BreedingParameterListDto;
import com.jaagro.cbs.api.dto.standard.BreedingStandardDto;
import com.jaagro.cbs.api.dto.standard.BreedingStandardParameterItemDto;
import com.jaagro.cbs.api.dto.standard.CreateBreedingStandardDto;
import com.jaagro.cbs.api.model.BreedingStandard;
import com.jaagro.cbs.api.model.BreedingStandardParameter;
import com.jaagro.cbs.api.model.BreedingStandardParameterExample;
import com.jaagro.cbs.api.service.BreedingStandardService;
import com.jaagro.cbs.biz.mapper.BreedingStandardMapperExt;
import com.jaagro.cbs.biz.mapper.BreedingStandardParameterMapperExt;
import com.jaagro.constant.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        BeanUtils.copyProperties(dto,breedingStandard);
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
        BeanUtils.copyProperties(dto,breedingStandard);
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
        if (!CollectionUtils.isEmpty(breedingStandardParameterList)){
            List<BreedingStandardParameterItemDto> newParameterList = new ArrayList<>();
            List<BreedingStandardParameterItemDto> oldParameterList = new ArrayList<>();
            for (BreedingStandardParameterItemDto itemDto : breedingStandardParameterList){
                if (itemDto.getId() != null){
                    oldParameterList.add(itemDto);
                }else{
                    newParameterList.add(itemDto);
                }
            }
            Integer currentUserId = getCurrentUserId();
            if (!CollectionUtils.isEmpty(newParameterList)){
                List<BreedingStandardParameter> parameterList = new ArrayList<>();
                for (BreedingStandardParameterItemDto itemDto : newParameterList){
                    BreedingStandardParameter parameter = new BreedingStandardParameter();
                    BeanUtils.copyProperties(dto,parameter);
                    BeanUtils.copyProperties(itemDto,parameter);
                    if (dto.getAlarm() == null){
                        parameter.setAlarm(Boolean.FALSE);
                    }
                    parameter.setEnable(Boolean.TRUE)
                            .setCreateTime(new Date())
                            .setCreateUserId(currentUserId);
                    parameterList.add(parameter);
                }
                standardParameterMapper.batchInsert(parameterList);
            }
            if (!CollectionUtils.isEmpty(oldParameterList)){
                List<BreedingStandardParameter> parameterList = new ArrayList<>();
                for (BreedingStandardParameterItemDto itemDto : oldParameterList){
                    BreedingStandardParameter parameter = new BreedingStandardParameter();
                    BeanUtils.copyProperties(dto,parameter);
                    BeanUtils.copyProperties(itemDto,parameter);
                    parameter
                            .setModifyTime(new Date())
                            .setModifyUserId(currentUserId);
                    parameterList.add(parameter);
                }
                standardParameterMapper.batchUpdateByPrimaryKeySelective(parameterList);
            }
        }
    }

    private Integer getCurrentUserId() {
        UserInfo  userInfo = currentUserService.getCurrentUser();
        return userInfo == null ? null : userInfo.getId();
    }
}
