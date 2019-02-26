package com.jaagro.cbs.biz.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jaagro.cbs.api.dto.base.CustomerContactsReturnDto;
import com.jaagro.cbs.api.dto.plan.BreedingPlanParamDto;
import com.jaagro.cbs.api.dto.plan.CreateBreedingPlanDto;
import com.jaagro.cbs.api.dto.plan.ReturnBreedingPlanDto;
import com.jaagro.cbs.api.dto.plan.UpdateBreedingPlanDto;
import com.jaagro.cbs.api.enums.PlanStatusEnum;
import com.jaagro.cbs.api.model.*;
import com.jaagro.cbs.api.service.BatchPlantCoopService;
import com.jaagro.cbs.api.service.BreedingPlanService;
import com.jaagro.cbs.biz.mapper.*;
import com.jaagro.cbs.biz.service.CustomerClientService;
import com.jaagro.cbs.biz.utils.SequenceCodeUtils;
import com.jaagro.constant.UserInfo;
import com.jaagro.utils.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * 养殖计划管理
 *
 * @author @Gao.
 */
@Slf4j
@Service
public class BreedingPlanServiceImpl implements BreedingPlanService {

    @Autowired
    private SequenceCodeUtils sequenceCodeUtils;
    @Autowired
    private BreedingPlanMapperExt breedingPlanMapper;
    @Autowired
    private BatchPlantCoopMapperExt batchPlantCoopMapper;
    @Autowired
    private CurrentUserService currentUserService;
    @Autowired
    private CustomerClientService customerClientService;
    @Autowired
    private CoopMapperExt coopMapper;
    @Autowired
    private BatchPlantCoopService batchPlantCoopService;
    @Autowired
    private BreedingBatchParameterMapperExt breedingBatchParameterMapperExt;
    @Autowired
    private PlantMapperExt plantMapper;

    /**
     * 创建养殖计划
     *
     * @param dto
     * @author @Gao.
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createBreedingPlan(CreateBreedingPlanDto dto) {
        UserInfo currentUser = currentUserService.getCurrentUser();
        String batchNo = sequenceCodeUtils.genSeqCode("AT");
        BreedingPlan breedingPlan = new BreedingPlan();
        breedingPlan
                .setBatchNo(batchNo)
                .setCreateUserId(currentUser.getId())
                .setCreateUserName(currentUser.getName())
                .setPlanStatus(PlanStatusEnum.ENTER_CONTRACT.getCode());
        BeanUtils.copyProperties(dto, breedingPlan);
        //插入养殖计划
        breedingPlanMapper.insertSelective(breedingPlan);
        //插入养殖关联表
        if (!CollectionUtils.isEmpty(dto.getPlantIds())) {
            List<Integer> plantIds = dto.getPlantIds();
            for (Integer plantId : plantIds) {
                BatchPlantCoop batchPlantCoop = new BatchPlantCoop();
                batchPlantCoop
                        .setCreateUserId(currentUser.getId())
                        .setPlantId(plantId);
                batchPlantCoopMapper.insertSelective(batchPlantCoop);
            }
        }
    }

    /**
     * 养殖计划列表
     *
     * @param dto
     * @return
     * @author @Gao.
     */
    @Override
    public PageInfo listBreedingPlan(BreedingPlanParamDto dto) {
        PageHelper.startPage(dto.getPageNum(), dto.getPageSize());
//        dto.setTenantId();    先注释,等待从userInfo获取租户id
        if (dto.getCustomerInfo() != null) {
            BaseResponse<List<Integer>> listBaseResponse = customerClientService.listCustomerIdByKeyWord(dto.getCustomerInfo());
            if (!CollectionUtils.isEmpty(listBaseResponse.getData())) {
                List<Integer> customerIds = listBaseResponse.getData();
                dto.setCustomerIds(customerIds);
            }
        }
        List<ReturnBreedingPlanDto> planDtoList = breedingPlanMapper.listBreedingPlan(dto);
        for (ReturnBreedingPlanDto returnBreedingPlanDto : planDtoList) {
            //填充养殖户信息
            CustomerContactsReturnDto contactsReturnDto = customerClientService.getCustomerContactByCustomerId(returnBreedingPlanDto.getCustomerId());
            if (contactsReturnDto != null) {
                returnBreedingPlanDto
                        .setCustomerName(contactsReturnDto.getContact())
                        .setCustomerPhone(contactsReturnDto.getPhone());
            }
            //填充养殖场信息
            PlantExample plantExample = new PlantExample();
            plantExample.createCriteria()
                    .andCustomerIdEqualTo(returnBreedingPlanDto.getCustomerId())
                    .andEnableEqualTo(true);
            List<Plant> plants = plantMapper.selectByExample(plantExample);
            returnBreedingPlanDto.setPlants(plants);
            //填充鸡舍
            List<Integer> coopId = batchPlantCoopService.listCoopIdByPlanId(returnBreedingPlanDto.getId());
            if (!CollectionUtils.isEmpty(coopId)) {
                CoopExample coopExample = new CoopExample();
                coopExample.createCriteria()
                        .andIdIn(coopId)
                        .andEnableEqualTo(true);
                returnBreedingPlanDto.setCoops(coopMapper.selectByExample(coopExample));
            }
            //填充进度
            BreedingBatchParameterExample parameterExample = new BreedingBatchParameterExample();
            parameterExample.createCriteria()
                    .andEnableEqualTo(true)
                    .andPlanIdEqualTo(returnBreedingPlanDto.getId())
                    .andBatchNoEqualTo(returnBreedingPlanDto.getBatchNo());
            List<BreedingBatchParameter> breedingBatchParameters = breedingBatchParameterMapperExt.selectByExample(parameterExample);
            if (!CollectionUtils.isEmpty(breedingBatchParameters)) {
                BreedingBatchParameter parameter = breedingBatchParameters.get(0);
                returnBreedingPlanDto.setAllDayAge(parameter.getDayAge());
                try {
                    long day = getInterval(new Date(), returnBreedingPlanDto.getPlanTime());
                    returnBreedingPlanDto.setAlreadyDayAge((int) day);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
        return new PageInfo(planDtoList);
    }

    private long getInterval(Date begin_date, Date end_date) throws Exception {
        long day = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        if (begin_date != null) {
            String begin = sdf.format(begin_date);
            begin_date = sdf.parse(begin);
        }
        if (end_date != null) {
            String end = sdf.format(end_date);
            end_date = sdf.parse(end);
        }
        day = (end_date.getTime() - begin_date.getTime()) / (24 * 60 * 60 * 1000);
        return day;
    }

    /**
     * 更新养殖计划
     *
     * @param dto
     * @author @Gao.
     */
    @Override
    public void upDateBreedingPlanDetails(UpdateBreedingPlanDto dto) {
        BreedingPlan breedingPlan = new BreedingPlan();
        BeanUtils.copyProperties(dto, breedingPlan);
        breedingPlanMapper.updateByPrimaryKeySelective(breedingPlan);
    }
}
