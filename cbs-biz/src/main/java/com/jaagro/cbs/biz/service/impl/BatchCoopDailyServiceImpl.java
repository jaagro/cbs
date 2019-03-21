package com.jaagro.cbs.biz.service.impl;

import com.jaagro.cbs.api.model.BatchCoopDaily;
import com.jaagro.cbs.api.model.BreedingPlan;
import com.jaagro.cbs.api.model.BreedingRecord;
import com.jaagro.cbs.api.model.BreedingRecordExample;
import com.jaagro.cbs.api.service.BatchCoopDailyService;
import com.jaagro.cbs.biz.mapper.BatchCoopDailyMapperExt;
import com.jaagro.cbs.biz.mapper.BreedingPlanMapperExt;
import com.jaagro.cbs.biz.mapper.BreedingRecordMapperExt;
import com.jaagro.cbs.biz.utils.RedisLock;
import com.jaagro.cbs.biz.utils.RedisUtil;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author baiyiran
 * @Date 2019/3/16
 */
@Service
public class BatchCoopDailyServiceImpl implements BatchCoopDailyService {

    @Autowired
    private BreedingPlanMapperExt breedingPlanMapper;
    @Autowired
    private BatchCoopDailyMapperExt batchCoopDailyMapper;
    @Autowired
    private BreedingRecordMapperExt breedingRecordMapper;
    @Autowired
    private RedisLock redisLock;
    @Autowired
    private RedisUtil redis;

    /**
     * 获取昨天的日期
     *
     * @return
     */
    private String getDay() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date());
    }

    /**
     * 鸡舍养殖每日汇总
     */
    @Override
    public void batchCoopDaily() {
        //加锁
        long time = System.currentTimeMillis() + 10 * 1000;
        boolean success = redisLock.lock("Scheduled:BatchCoopDailyService:batchCoopDaily", String.valueOf(time), null, null);
        if (!success) {
            throw new RuntimeException("请求正在处理中");
        }
        String todayDate = getDay();
        //鸡舍日汇总列表
        List<BatchCoopDaily> dailyList = new ArrayList<>();
        //从BreedingRecord统计
        BreedingRecordExample breedingRecordExample = new BreedingRecordExample();
        breedingRecordExample.createCriteria()
                .andEnableEqualTo(true);
        List<BreedingRecord> breedingRecordList = breedingRecordMapper.listCoopDailySumByParams(todayDate);
        if (!CollectionUtils.isEmpty(breedingRecordList)) {
            for (BreedingRecord record : breedingRecordList) {
                BatchCoopDaily daily = new BatchCoopDaily();
                BeanUtils.copyProperties(record, daily);
                //死淘数量
                Integer deadAmount = breedingRecordMapper.sumDeadAmountByCoopId(record);
                daily.setDeadAmount(deadAmount);
                //起始喂养数量 && 剩余喂养数量
                BatchCoopDaily coopDaily = new BatchCoopDaily();
                coopDaily.setCreateTime(DateUtils.addDays(record.getCreateTime(), -1)).setCoopId(daily.getCoopId()).setPlanId(daily.getPlanId());
                Integer startAmount = batchCoopDailyMapper.getStartAmountByCoopId(coopDaily);
                if (startAmount != null && startAmount > 0) {
                    daily.setStartAmount(startAmount);
                    if (deadAmount != null) {
                        // 剩余喂养数量=起始-死淘
                        daily.setCurrentAmount(daily.getStartAmount() - daily.getDeadAmount());
                    }
                } else {
                    //查询不到记录，就用breeding_plan的计划上鸡数量
                    BreedingPlan breedingPlan = breedingPlanMapper.selectByPrimaryKey(record.getPlanId());
                    if (breedingPlan != null) {
                        daily.setStartAmount(breedingPlan.getPlanChickenQuantity());
                        // 剩余喂养数量=计划上鸡数量
                        daily.setCurrentAmount(breedingPlan.getPlanChickenQuantity());
                    }
                }
                //出栏数量
                //当日累积喂料次数
                Integer countFeed = breedingRecordMapper.countFodderTimesByCoopId(record);
                daily.setFodderTimes(countFeed);
                //当日累积喂料量
                daily.setFodderAmount(record.getBreedingValue());
                daily.setCreateUserId(1);
                //
                dailyList.add(daily);
            }
            //插入前先删除
            batchCoopDailyMapper.deleteByDate(todayDate);
            //插入鸡舍养殖每日汇总
            batchCoopDailyMapper.batchInsert(dailyList);

            //去锁
            redis.del("Scheduled:BatchCoopDailyService:batchCoopDaily");
        }
    }
}
