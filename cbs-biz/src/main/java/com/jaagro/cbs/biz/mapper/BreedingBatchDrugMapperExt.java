package com.jaagro.cbs.biz.mapper;

import com.jaagro.cbs.api.dto.standard.BreedingStandardDrugDto;
import com.jaagro.cbs.api.model.BreedingBatchDrug;
import com.jaagro.cbs.api.model.BreedingBatchDrugExample;
import com.jaagro.cbs.biz.mapper.base.BaseMapper;
import org.apache.ibatis.annotations.Param;

import javax.annotation.Resource;
import java.util.List;


/**
 * BreedingBatchDrugMapperExt接口
 *
 * @author :generator
 * @date :2019/3/8
 */
@Resource
public interface BreedingBatchDrugMapperExt extends BaseMapper<BreedingBatchDrug, BreedingBatchDrugExample> {
    /**
     * 批量插入
     * @param breedingBatchDrugList
     */
    void batchInsert(@Param("breedingBatchDrugList") List<BreedingBatchDrug> breedingBatchDrugList);

    /**
     * 根据计划id查询
     * @param planId
     * @return
     */
    List<BreedingStandardDrugDto> listBreedingBatchDrugs(@Param("planId") Integer planId);
}