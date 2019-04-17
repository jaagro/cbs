package com.jaagro.cbs.biz.schedule;

import com.jaagro.cbs.api.enums.PlanStatusEnum;
import com.jaagro.cbs.api.model.BreedingPlan;
import com.jaagro.cbs.api.model.BreedingPlanExample;
import com.jaagro.cbs.biz.mapper.BreedingPlanMapperExt;
import com.jaagro.cbs.biz.utils.DateUtil;
import com.jaagro.cbs.biz.utils.JsonUtils;
import com.jaagro.cbs.biz.utils.RedisLock;
import com.jaagro.cbs.biz.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 养殖大脑定时钟
 *
 * @author gavin
 * @Date 2019/3/16
 */
@Service
@Slf4j
public class BreedingBrainSchedule {


    @Autowired
    private BreedingPlanMapperExt breedingPlanMapper;
    @Autowired
    private com.jaagro.cbs.api.service.BreedingBrainService breedingBrainService;
    @Autowired
    private RedisUtil redis;
    @Autowired
    private RedisLock redisLock;


    /**
     * 第二阶段510饲料、511饲料自动生成养殖计划采购订单
     * 每天晚上11:50点跑一次
     */
    @Scheduled(cron = "0 50 23 1/1 * ?")
//    @Scheduled(cron = "0 * * * * ?")
    @Transactional(rollbackFor = Exception.class)
    public void calculatePhaseTwoFoodWeight() {
        log.info("calculatePhaseTwoFoodWeight:定时钟执行开始");
        List<BreedingPlan> breedingPlanList = this.getBreedingPlanList("calculatePhaseTwoFoodWeight");
        if (!CollectionUtils.isEmpty(breedingPlanList)) {
            for (BreedingPlan breedingPlan : breedingPlanList) {
                try {
                    breedingBrainService.calculatePhaseTwoFoodWeightById(breedingPlan);
                } catch (Exception ex) {
                    log.error("第二阶段(15->19天)510饲料订单自动生成时失败,planId:" + breedingPlan.getId() + ",原因：" + ex);
                    continue;
                }
            }
            for (BreedingPlan breedingPlan : breedingPlanList) {
                try {
                    breedingBrainService.calculatePhaseThreeFoodWeightById(breedingPlan);
                } catch (Exception ex) {
                    log.error("第二阶段(20->28天)的511饲料订单自动生成时失败,planId:" + breedingPlan.getId() + ",原因：" + ex);
                    continue;
                }
            }
        }
        log.info("calculatePhaseTwoFoodWeight:定时钟执行结束");
    }

    /**
     * 第三阶段29->计划养殖天数的511饲料订单自动生成养殖计划采购订单
     * 每天晚上11:55点跑一次
     */
    @Scheduled(cron = "0 55 23 1/1 * ?")
//    @Scheduled(cron = "0 * * * * ?")
    @Transactional(rollbackFor = Exception.class)
    public void calculatePhaseFourFoodWeight() {
        log.info("calculatePhaseFourFoodWeight:定时钟执行开始");
        List<BreedingPlan> breedingPlanList = this.getBreedingPlanList("calculatePhaseFourFoodWeight");
        if (!CollectionUtils.isEmpty(breedingPlanList)) {
            for (BreedingPlan breedingPlan : breedingPlanList) {
                try {
                    breedingBrainService.calculatePhaseFourFoodWeightById(breedingPlan);
                } catch (Exception ex) {
                    log.error("第三阶段29->计划养殖天数的511饲料订单自动生成养殖计划采购订单失败,planId:" + breedingPlan.getId() + ",原因：" + ex);
                    continue;
                }
            }
        }
        log.info("calculatePhaseFourFoodWeight:定时钟执行结束");
    }

    private List<BreedingPlan> getBreedingPlanList(String method) {
        List<BreedingPlan> breedingPlanList = null;
        //加锁
        long time = System.currentTimeMillis() + 10 * 1000;
        boolean success = redisLock.lock("Scheduled:redisLock:" + method, String.valueOf(time), 10, TimeUnit.MINUTES);
        if (!success) {
            log.info("BreedingBrainSchedule method:"+ method+" 方法在执行....");
            return breedingPlanList;
        }
        //从redis里去取养殖计划
        String key = "BreedingPlanList-" + DateUtil.getStringDateShort();
        String breedingPlanListJson = redis.get(key);
        if (StringUtils.isEmpty(breedingPlanListJson)) {
            List<Integer> statusList = new ArrayList<>();
            statusList.add(PlanStatusEnum.SIGN_CHICKEN.getCode());
            statusList.add(PlanStatusEnum.BREEDING.getCode());
            statusList.add(PlanStatusEnum.SLAUGHTER_CONFIRM.getCode());

            BreedingPlanExample example = new BreedingPlanExample();
            example.createCriteria()
                    .andEnableEqualTo(true)
                    .andPlanStatusIn(statusList)
                    .andPlanChickenQuantityGreaterThan(0)
                    .andBreedingDaysGreaterThan(0);
            breedingPlanList = breedingPlanMapper.selectByExample(example);

            if (!CollectionUtils.isEmpty(breedingPlanList)) {
                redis.set(key, JsonUtils.objectToJson(breedingPlanList), 1800);
            }
        } else {
            breedingPlanList = JsonUtils.jsonToList(breedingPlanListJson, BreedingPlan.class);
        }

        redisLock.unLock("Scheduled:redisLock:" + method);
        return breedingPlanList;
    }

}
