package com.jaagro.cbs.biz.service.impl;

import com.jaagro.cbs.api.model.BatchCoopDaily;
import com.jaagro.cbs.api.model.Coop;
import com.jaagro.cbs.api.service.BatchCoopDailyService;
import com.jaagro.cbs.biz.mapper.BatchCoopDailyMapperExt;
import com.jaagro.cbs.biz.mapper.BreedingPlanMapperExt;
import com.jaagro.cbs.biz.mapper.BreedingRecordMapperExt;
import com.jaagro.cbs.biz.mapper.CoopMapperExt;
import com.jaagro.cbs.biz.utils.RedisLock;
import com.jaagro.cbs.biz.utils.RedisUtil;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
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
    @Autowired
    private CoopMapperExt coopMapperExt;


    /**
     * 鸡舍养殖每日汇总
     */
    @Override
    public void batchCoopDaily(String todayDate) {
        //加锁
        long time = System.currentTimeMillis() + 10 * 1000;
        boolean success = redisLock.lock("Scheduled:redisLock:batchCoopDaily", String.valueOf(time), null, null);
        if (!success) {
            throw new RuntimeException("请求正在处理中");
        }
        //格式化今日
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (StringUtils.isEmpty(todayDate)) {
            todayDate = sdf.format(new Date());
        }
        //从BreedingRecord统计
        List<BatchCoopDaily> breedingRecordList = breedingRecordMapper.listCoopDailyByParams(todayDate);
        if (!CollectionUtils.isEmpty(breedingRecordList)) {
            for (BatchCoopDaily batchCoopDaily : breedingRecordList) {
                try {
                    //查询不到记录，就用coop的在养数量
                    Coop coop = coopMapperExt.selectByPrimaryKey(batchCoopDaily.getCoopId());
                    // 查询昨日剩余喂养数量 来当做今天的起始喂养数量
                    BatchCoopDaily coopDaily = new BatchCoopDaily();
                    BeanUtils.copyProperties(batchCoopDaily, coopDaily);
                    coopDaily
                            .setDayAge(batchCoopDaily.getDayAge() - 1)
                            .setCreateTime(DateUtils.addDays(sdf.parse(todayDate), -1));
                    // 昨日剩余喂养数量
                    Integer startAmount = batchCoopDailyMapper.getStartAmountByCoopId(coopDaily);
                    if (startAmount != null && startAmount > 0) {
                        batchCoopDaily.setStartAmount(startAmount);
                    } else {
                        batchCoopDaily.setStartAmount(coop.getBreedingValue());
                    }
                    if (batchCoopDaily.getDeadAmount() != null) {
                        // 剩余喂养数量=起始-死淘
                        batchCoopDaily.setCurrentAmount(batchCoopDaily.getStartAmount() - batchCoopDaily.getDeadAmount());
                    } else {
                        batchCoopDaily.setCurrentAmount(coop.getBreedingValue());
                    }

                    //出栏数量 待定
                    //创建人
                    batchCoopDaily
                            .setCreateTime(sdf.parse(todayDate))
                            .setCreateUserId(1);
                    //插入前先删除
                    batchCoopDailyMapper.deleteByDayAge(batchCoopDaily.getDayAge());
                } catch (Exception ex) {
                    ex.printStackTrace();
                    throw new RuntimeException(ex.getMessage());
                }
            }
            //插入鸡舍养殖每日汇总
            batchCoopDailyMapper.batchInsert(breedingRecordList);

        } else {
            try {
                breedingRecordList = batchCoopDailyMapper.listYesterdayData(sdf.format(DateUtils.addDays(sdf.parse(todayDate), -1)));
                if (!CollectionUtils.isEmpty(breedingRecordList)) {
                    for (BatchCoopDaily coopDaily : breedingRecordList) {
                        coopDaily.setCreateTime(sdf.parse(todayDate));
                        //插入前先删除
                        batchCoopDailyMapper.deleteByDayAge(coopDaily.getDayAge());
                    }
                    //插入鸡舍养殖每日汇总
                    batchCoopDailyMapper.batchInsert(breedingRecordList);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                throw new RuntimeException(ex.getMessage());
            }
        }
        //去锁
        redisLock.unLock("Scheduled:redisLock:batchCoopDaily");
    }
}
