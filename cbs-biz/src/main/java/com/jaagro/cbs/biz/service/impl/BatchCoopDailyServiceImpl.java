package com.jaagro.cbs.biz.service.impl;

import com.jaagro.cbs.api.model.BatchCoopDaily;
import com.jaagro.cbs.api.model.BreedingPlan;
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
     * 鸡舍养殖每日汇总
     */
    @Override
    public void batchCoopDaily() {
        //加锁
        long time = System.currentTimeMillis() + 10 * 1000;
        boolean success = redisLock.lock("Scheduled:redisLock:batchCoopDaily", String.valueOf(time), null, null);
        if (!success) {
            throw new RuntimeException("请求正在处理中");
        }
        //格式化今日
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String todayDate = sdf.format(new Date());
        //鸡舍养殖每日汇总列表
        List<BatchCoopDaily> dailyList = new ArrayList<>();
        //从BreedingRecord统计
        List<BatchCoopDaily> breedingRecordList = breedingRecordMapper.listCoopDailyByParams(todayDate);
        if (!CollectionUtils.isEmpty(breedingRecordList)) {
            for (BatchCoopDaily batchCoopDaily : breedingRecordList) {
                //查询昨日剩余喂养数量 来当做今天的起始喂养数量
                BatchCoopDaily coopDaily = new BatchCoopDaily();
                BeanUtils.copyProperties(batchCoopDaily, coopDaily);
                coopDaily.setCreateTime(DateUtils.addDays(batchCoopDaily.getCreateTime(), -1));
                //得到 昨日剩余喂养数量
                Integer startAmount = batchCoopDailyMapper.getStartAmountByCoopId(coopDaily);
                if (startAmount != null && startAmount > 0) {
                    batchCoopDaily.setStartAmount(startAmount);
                    if (batchCoopDaily.getDeadAmount() != null) {
                        // 剩余喂养数量=起始-死淘
                        batchCoopDaily.setCurrentAmount(batchCoopDaily.getStartAmount() - batchCoopDaily.getDeadAmount());
                    }
                } else {
                    //查询不到记录，就用breeding_plan的计划上鸡数量
                    BreedingPlan breedingPlan = breedingPlanMapper.selectByPrimaryKey(batchCoopDaily.getPlanId());
                    if (breedingPlan != null) {
                        batchCoopDaily.setStartAmount(breedingPlan.getPlanChickenQuantity());
                        // 剩余喂养数量=计划上鸡数量
                        batchCoopDaily.setCurrentAmount(breedingPlan.getPlanChickenQuantity());
                    }
                }
                //出栏数量 待定
                //创建人
                batchCoopDaily.setCreateUserId(1);
                //
                dailyList.add(batchCoopDaily);
            }
            //插入前先删除
            batchCoopDailyMapper.deleteByDate(todayDate);
            //插入鸡舍养殖每日汇总
            batchCoopDailyMapper.batchInsert(dailyList);

            //去锁
            redisLock.unLock("Scheduled:redisLock:batchCoopDaily");
        }
    }
}
