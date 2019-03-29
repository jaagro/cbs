package com.jaagro.cbs.biz.mapper;

import com.jaagro.cbs.api.dto.base.BatchInfoCriteriaDto;
import com.jaagro.cbs.api.model.BatchInfo;
import com.jaagro.cbs.api.model.BatchInfoExample;
import com.jaagro.cbs.biz.mapper.base.BaseMapper;
import org.apache.ibatis.annotations.Param;


import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;


/**
 * BatchInfoMapperExt接口
 *
 * @author :generator
 * @date :2019/2/21
 */
@Resource
public interface BatchInfoMapperExt extends BaseMapper<BatchInfo, BatchInfoExample> {

    /**
     * 根据日期删除
     *
     * @param todayDate
     */
    void deleteByDate(@Param("todayDate") String todayDate);

    /**
     * 根据日龄删除
     *
     * @param todayDate
     */
    void deleteByDateAge(@Param("dayAge") Integer dayAge);

    /**
     * 批量插入
     *
     * @param batchInfoList
     */
    void insertBatch(@Param("batchInfoList") List<BatchInfo> batchInfoList);

    /**
     * 根据批次、日期 查询 上一次的剩余数
     *
     * @param batchInfo
     * @return
     */
    Integer getStartAmountByPlanId(BatchInfo batchInfo);

    /**
     * 查询当前养殖计划 最新一条记录
     *
     * @param planId
     * @return
     */
    BatchInfo getTheLatestBatchInfo(@Param("planId") Integer planId);

    /**
     * 累计饲料喂养量
     *
     * @param planId
     * @return
     */
    BigDecimal accumulativeFeed(@Param("planId") Integer planId);

    /**
     * 累计死淘数量
     *
     * @param planId
     * @return
     */
    BigDecimal accumulativeDeadAmount(@Param("planId") Integer planId);

    /**
     * 累计出栏量
     *
     * @param planId
     * @return
     */
    BigDecimal accumulativeSaleAmount(@Param("planId") Integer planId);

    /**
     * 累计客户养殖计划中所有死淘数量
     *
     * @param planIds
     * @return
     */
    BigDecimal accumulativeTotalDeadAmount(@Param("planIds") Set planIds);

    /**
     * 累计客户养殖计划中食用饲料总量
     *
     * @param planIds
     * @return
     */
    BigDecimal accumulativeTotalFeed(@Param("planIds") Set planIds);

    /**
     * 累计客户养殖计划中食用饲料总量
     *
     * @param planIds
     * @return
     */
    BigDecimal accumulativeTotalSaleAmount(@Param("planIds") Set planIds);

    /**
     * 查询昨日数据
     *
     * @param criteriaDto
     * @return
     */
    List<BatchInfo> listYestodayData(BatchInfoCriteriaDto criteriaDto);
}