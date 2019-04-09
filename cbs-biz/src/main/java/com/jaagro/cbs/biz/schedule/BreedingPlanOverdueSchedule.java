package com.jaagro.cbs.biz.schedule;

import com.jaagro.cbs.api.model.BreedingPlan;
import com.jaagro.cbs.biz.mapper.BreedingPlanMapperExt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: 养殖计划过期取消养殖计划单
 * @author: @Gao.
 * @create: 2019-04-09 14:37
 **/
@Service
@Slf4j
public class BreedingPlanOverdueSchedule {

    @Autowired
    private BreedingPlanMapperExt breedingPlanMapper;

    /**
     * 50分钟执行一次
     */
    @Scheduled(cron = "0 0/50 * * * ?")
    public void cancelBreedingPlanOverdue() {
        log.info("O cancelBreedingPlanOverdue start");
        List<BreedingPlan> breedingPlans = breedingPlanMapper.listBreedingPlanOverdue();
        List<Integer> planIds = new ArrayList<>();
        for (BreedingPlan breedingPlan : breedingPlans) {
            planIds.add(breedingPlan.getId());
        }
        if (!CollectionUtils.isEmpty(breedingPlans)) {
            breedingPlanMapper.batchUpdateBreedingPlanStatus(planIds);
        }
        log.info("O cancelBreedingPlanOverdue end");
    }
}
