package com.jaagro.cbs.api.service;

import com.jaagro.cbs.api.dto.base.BatchInfoCriteriaDto;

/**
 * @author baiyiran
 * @Date 2019/3/16
 */
public interface BreedingRecordDailyService {

    /**
     * 批次养殖记录表日汇总
     *
     * @param criteriaDto
     */
    void breedingRecordDaily(BatchInfoCriteriaDto criteriaDto);

}
