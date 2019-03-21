package com.jaagro.cbs.biz.mapper;

import com.jaagro.cbs.api.dto.farmer.BreedingRecordDto;
import com.jaagro.cbs.api.model.*;
import com.jaagro.cbs.biz.mapper.base.BaseMapper;
import org.apache.ibatis.annotations.Param;

import javax.annotation.Resource;
import java.util.List;


/**
 * BreedingRecordMapperExt接口
 *
 * @author :generator
 * @date :2019/2/21
 */
@Resource
public interface BreedingRecordMapperExt extends BaseMapper<BreedingRecord, BreedingRecordExample> {

    /**
     * 查询每个批次的日汇总
     *
     * @param todayDate
     * @return
     */
    List<BreedingRecordDaily> listBreedingDailyByParams(@Param("todayDate") String todayDate);

    /**
     * 鸡舍养殖每日汇总
     *
     * @param todayDate
     * @return
     */
    List<BatchCoopDaily> listCoopDailyByParams(@Param("todayDate") String todayDate);

    /**
     * 批次养殖情况汇总
     *
     * @param todayDate
     * @return
     */
    List<BatchInfo> listBatchInfoByParams(@Param("todayDate") String todayDate);

    /**
     * 根据条件查询养殖记录列表
     *
     * @param params
     * @return
     */
    List<BreedingRecordDto> listByParams(BreedingRecord params);

}