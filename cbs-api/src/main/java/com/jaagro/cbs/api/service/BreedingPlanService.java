package com.jaagro.cbs.api.service;


import com.github.pagehelper.PageInfo;
import com.jaagro.cbs.api.dto.plan.BreedingPlanParamDto;
import com.jaagro.cbs.api.dto.plan.CreateBreedingPlanDto;
import com.jaagro.cbs.api.dto.plan.ReturnBreedingPlanDto;

import java.util.List;

/**
 * 养殖计划管理
 *
 * @author @Gao.
 */
public interface BreedingPlanService {
    /**
     * 创建养殖计划
     *
     * @param dto
     */
    void createBreedingPlan(CreateBreedingPlanDto dto);

    PageInfo<List<ReturnBreedingPlanDto>> listBreedingPlan(BreedingPlanParamDto dto);
}
