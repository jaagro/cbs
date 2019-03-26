package com.jaagro.cbs.api.service;

import com.jaagro.cbs.api.dto.base.BatchInfoCriteriaDto;

/**
 * @author baiyiran
 * @Date 2019/3/16
 */
public interface BatchInfoService {

    /**
     * 批次养殖情况汇总
     *
     * @param criteriaDto
     */
    void batchInfo(BatchInfoCriteriaDto criteriaDto);
}
