package com.jaagro.cbs.biz.service.impl;

import com.jaagro.cbs.api.model.BatchInfo;
import com.jaagro.cbs.api.model.BreedingPlan;
import com.jaagro.cbs.api.service.BatchInfoService;
import com.jaagro.cbs.biz.mapper.BatchInfoMapperExt;
import com.jaagro.cbs.biz.mapper.BreedingPlanMapperExt;
import com.jaagro.cbs.biz.mapper.BreedingRecordMapperExt;
import com.jaagro.cbs.biz.utils.RedisLock;
import com.jaagro.cbs.biz.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.SimpleDateFormat;
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
    @Autowired
    private RedisUtil redis;
    @Autowired
    private RedisLock redisLock;

    /**
     * 批次养殖情况汇总
     */
    @Override
    public void batchInfo() {
        // 加锁
        long time = System.currentTimeMillis() + 10 * 1000;
        boolean success = redisLock.lock("Scheduled:redisLock:batchInfo", String.valueOf(time), null, null);
        if (!success) {
            throw new RuntimeException("请求正在处理中");
        }
        // 初始化今日
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String todayDate = sdf.format(new Date());
        // 从breedingRecord统计
        // 得到批次养殖情况汇总列表
        List<BatchInfo> batchInfoList = breedingRecordMapper.listBatchInfoByParams(todayDate);
        if (!CollectionUtils.isEmpty(batchInfoList)) {
            for (BatchInfo info : batchInfoList) {
                // 计划
                BreedingPlan breedingPlan = breedingPlanMapper.selectByPrimaryKey(info.getPlanId());
                if (breedingPlan == null) {
                    throw new RuntimeException("计划有误");
                }
                // 昨日的剩余喂养数量
                Integer currentAmount = batchInfoMapper.getStartAmountByPlanId(info);
                if (currentAmount != null && currentAmount > 0) {
                    info.setStartAmount(currentAmount);
                    // 剩余喂养数量=起始-死淘
                    if (info.getDeadAmount() != null && info.getDeadAmount() > 0) {
                        info.setCurrentAmount(info.getStartAmount() - info.getDeadAmount());
                    }
                } else {
                    //查询不到记录，就用breeding_plan的计划上鸡数量
                    info.setStartAmount(breedingPlan.getPlanChickenQuantity());
                    // 剩余喂养数量 = 计划上鸡数量
                    info.setCurrentAmount(breedingPlan.getPlanChickenQuantity());
                }
                //填充其他数据
                info
                        .setEnable(true)
                        .setCreateUserId(1)
                        .setStartDay(breedingPlan.getPlanTime())
                        .setTechnician(breedingPlan.getTechnician())
                        .setTechnicianId(breedingPlan.getTechnicianId());
            }
            //删除
            batchInfoMapper.deleteByDate(todayDate);
            //批量插入
            batchInfoMapper.insertBatch(batchInfoList);

            redis.del("Scheduled:redisLock:batchInfo");
        }

    }
}
