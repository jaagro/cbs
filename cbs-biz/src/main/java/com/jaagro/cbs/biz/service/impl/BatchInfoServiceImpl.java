package com.jaagro.cbs.biz.service.impl;

import com.jaagro.cbs.api.dto.base.BatchInfoCriteriaDto;
import com.jaagro.cbs.api.model.BatchInfo;
import com.jaagro.cbs.api.model.BreedingPlan;
import com.jaagro.cbs.api.service.BatchInfoService;
import com.jaagro.cbs.biz.mapper.*;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * @author baiyiran
 * @Date 2019/3/16
 */
@Service
public class BatchInfoServiceImpl implements BatchInfoService {

    @Autowired
    private BatchInfoMapperExt batchInfoMapper;
    @Autowired
    private BreedingPlanMapperExt breedingPlanMapper;
    @Autowired
    private BreedingRecordMapperExt breedingRecordMapper;

    /**
     * 获取前天的日期 yyy-mm-dd
     *
     * @return
     */
    private Date getBeforeYesterdayDate() {
        return DateUtils.addDays(new Date(), -2);
    }

    /**
     * 批次养殖情况汇总
     */
    @Override
    public void batchInfo() {
        String todayDate = new Date().toString();
        //从breedingRecord统计
        List<BatchInfo> batchInfoList = breedingRecordMapper.listBatchInfoByParams(todayDate);
        if (!CollectionUtils.isEmpty(batchInfoList)) {
            BatchInfoCriteriaDto criteriaDto = new BatchInfoCriteriaDto();
            criteriaDto.setTodayDate(todayDate);
            for (BatchInfo info : batchInfoList) {
                //死淘数量
                criteriaDto.setPlanId(info.getPlanId());
                int deadAmount = 0;
                deadAmount = breedingRecordMapper.sumDeadAmountByPlanId(criteriaDto);
                info.setDeadAmount(deadAmount);
                //起始喂养数量 && 剩余喂养数量
                BatchInfo batchInfo = new BatchInfo();
                batchInfo.setCreateTime(getBeforeYesterdayDate()).setPlanId(info.getPlanId());
                Integer currentAmount = batchInfoMapper.getStartAmountByPlanId(batchInfo);
                if (currentAmount > 0) {
                    info.setStartAmount(currentAmount);
                    // 剩余喂养数量=起始-死淘
                    info.setCurrentAmount(info.getStartAmount() - info.getDeadAmount());
                } else {
                    //查询不到记录，就用breeding_plan的计划上鸡数量
                    BreedingPlan breedingPlan = breedingPlanMapper.selectByPrimaryKey(info.getPlanId());
                    if (breedingPlan != null) {
                        info.setStartAmount(breedingPlan.getPlanChickenQuantity());
                        // 剩余喂养数量=计划上鸡数量
                        info.setCurrentAmount(breedingPlan.getPlanChickenQuantity());
                    }
                }
            }
            //删除
            batchInfoMapper.deleteByDate(todayDate);
            //批量插入
            batchInfoMapper.insertBatch(batchInfoList);
        }

    }
}
