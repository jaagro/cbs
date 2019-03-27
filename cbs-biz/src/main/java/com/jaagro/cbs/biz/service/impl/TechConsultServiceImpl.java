package com.jaagro.cbs.biz.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jaagro.cbs.api.dto.techconsult.ReturnTechConsultRecordDto;
import com.jaagro.cbs.api.dto.techconsult.TechConsultParamDto;
import com.jaagro.cbs.api.dto.techconsult.UpdateTechConsultDto;
import com.jaagro.cbs.api.enums.TechConsultStatusEnum;
import com.jaagro.cbs.api.model.*;
import com.jaagro.cbs.api.service.TechConsultService;
import com.jaagro.cbs.biz.mapper.BatchCoopDailyMapperExt;
import com.jaagro.cbs.biz.mapper.BreedingPlanMapperExt;
import com.jaagro.cbs.biz.mapper.TechConsultImagesMapperExt;
import com.jaagro.cbs.biz.mapper.TechConsultRecordMapperExt;
import com.jaagro.cbs.biz.utils.UrlPathUtil;
import com.jaagro.constant.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * @author gavin
 * @date 2019/2/28 13:31
 */
@Service
@Slf4j
public class TechConsultServiceImpl implements TechConsultService {
    @Autowired
    private CurrentUserService currentUserService;
    @Autowired
    private TechConsultRecordMapperExt techConsultRecordMapper;
    @Autowired
    private BreedingPlanMapperExt breedingPlanMapper;
    @Autowired
    private BatchCoopDailyMapperExt batchCoopDailyMapper;
    @Autowired
    private TechConsultImagesMapperExt techConsultImagesMapperExt;

    /**
     * 技术询问分页列表
     *
     * @param dto
     * @return
     */
    @Override
    public PageInfo listTechConsultRecords(TechConsultParamDto dto) {
        PageHelper.startPage(dto.getPageNum(), dto.getPageSize());

        TechConsultRecordExample example = new TechConsultRecordExample();
        TechConsultRecordExample.Criteria criteriaOne = example.createCriteria();
        if(null != dto.getStatus()) {
            criteriaOne.andTechConsultStatusEqualTo(dto.getStatus());
        }
        if(null != dto.getBatchNo()) {
            criteriaOne.andBatchNoLike(dto.getBatchNo() + "%");
        }
        if(null != dto.getKeyWord()) {
            criteriaOne.andCustomerNameLike(dto.getKeyWord() + "%");
        }

        TechConsultRecordExample.Criteria criteriaTwo = example.createCriteria();
        if(null != dto.getStatus()) {
            criteriaTwo.andTechConsultStatusEqualTo(dto.getStatus());
        }
        if(null != dto.getBatchNo()) {
            criteriaTwo.andBatchNoLike(dto.getBatchNo() + "%");
        }
        if(null != dto.getKeyWord()) {
            criteriaTwo.andCustomerPhoneNumberLike(dto.getKeyWord() + "%");
        }
        example.or(criteriaTwo);
        List<TechConsultRecord> techConsultRecordDos = techConsultRecordMapper.selectByExample(example);

        return new PageInfo(techConsultRecordDos);
    }

    /**
     * 根据技术询问主键Id获取详情
     *
     * @param id
     * @return
     */
    @Override
    public ReturnTechConsultRecordDto getDetailTechConsultDtoById(Integer id) {

        ReturnTechConsultRecordDto returnDto = new ReturnTechConsultRecordDto();
        TechConsultRecord techConsultRecordDo = techConsultRecordMapper.selectByPrimaryKey(id);
        if(null == techConsultRecordDo)
        {
            throw new RuntimeException("技术询问id=" + id + "不存在");
        }
        BeanUtils.copyProperties(techConsultRecordDo, returnDto);
        returnDto.setStrTechConsultStatus(TechConsultStatusEnum.getDescByCode(techConsultRecordDo.getTechConsultStatus()));
        int planId = techConsultRecordDo.getPlanId();
        int livingAmount = 0;
        BreedingPlan breedingPlan = breedingPlanMapper.selectByPrimaryKey(planId);
        if (null != breedingPlan) {
            returnDto.setBreedingDays(breedingPlan.getBreedingDays());
            int planChickenQuantity = breedingPlan.getPlanChickenQuantity();
            int coopId = techConsultRecordDo.getCoopId();

            BatchCoopDailyExample example = new BatchCoopDailyExample();
            example.createCriteria().andPlanIdEqualTo(planId).andCoopIdEqualTo(coopId).andEnableEqualTo(true);
            List<BatchCoopDaily> batchCoopDailyList = batchCoopDailyMapper.selectByExample(example);
            int deadAmount = 0;
            if (!CollectionUtils.isEmpty(batchCoopDailyList)) {
                for (BatchCoopDaily batchCoopDaily : batchCoopDailyList) {
                    deadAmount = deadAmount + batchCoopDaily.getDeadAmount();
                }
            }
            livingAmount = planChickenQuantity - deadAmount;
        }
        returnDto.setLivingAmount(livingAmount);

        TechConsultImagesExample imagesExample = new TechConsultImagesExample();
        imagesExample.createCriteria().andTechConsultRecordIdEqualTo(id);
        List<TechConsultImages> images = techConsultImagesMapperExt.selectByExample(imagesExample);
        if(!CollectionUtils.isEmpty(images)) {
            for (TechConsultImages image : images) {
                image.setImageUrl(UrlPathUtil.getAbstractImageUrl(image.getImageUrl()));
            }
        }
        returnDto.setImagesList(images);
        return returnDto;
    }

    /**
     * 处理技术申请
     *
     * @param updateDto
     * @return
     */
    @Override
    public boolean handleTechConsultRecord(UpdateTechConsultDto updateDto) {
        try {
            log.info("O TechConsultServiceImpl.HandleTechConsultRecord input updateDto:{}", updateDto);
            UserInfo currentUser = currentUserService.getCurrentUser();
            TechConsultRecord techConsultRecordDo = new TechConsultRecord();
            BeanUtils.copyProperties(updateDto, techConsultRecordDo);
            techConsultRecordDo.setModifyTime(new Date());
            techConsultRecordDo.setHandleTime(new Date());
            techConsultRecordDo.setTechConsultStatus(TechConsultStatusEnum.STATUS_SOLVED.getCode());
            techConsultRecordDo.setModifyUserId(currentUser != null ? currentUser.getId() : null);
            techConsultRecordDo.setHandleUserId(currentUser != null ? currentUser.getId() : null);
            techConsultRecordMapper.updateByPrimaryKeySelective(techConsultRecordDo);
        } catch (Exception e) {
            log.error("R TechConsultServiceImpl.HandleTechConsultRecord error:" + e);
            return false;
        }
        return true;
    }


}
