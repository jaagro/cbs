package com.jaagro.cbs.api.dto.farmer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 养殖计划详情
 * @author yj
 * @date 2019/3/5 16:58
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class BreedingPlanDetailDto implements Serializable{
    private static final long serialVersionUID = -8675493136651098575L;
    /**
     * 计划id
     */
    private Integer id;

    /**
     * 批次号
     */
    private String batchNo;

    /**
     * 计划状态(0-待录入合同,1-待参数纠偏,2-待上鸡签收,3-养殖中,4-待出栏确认,5-已完成,6-已取消)
     */
    private Integer planStatus;

    /**
     * 计划状态(0-待录入合同,1-待参数纠偏,2-待上鸡签收,3-养殖中,4-待出栏确认,5-已完成,6-已取消)
     */
    private String strPlanStatus;

    /**
     * 计划上鸡数量
     */
    private Integer planChickenQuantity;

    /**
     * 存栏量
     */
    private BigDecimal breedingStock;

    /**
     * 上鸡时间
     */
    private Date planTime;

    /**
     * 进度 - 日龄
     */
    private Integer dayAge;

    /**
     * 养殖天数
     */
    private Integer breedingDays;

    /**
     * 今日饲料耗量
     */
    private BigDecimal fodderAmount;

    /**
     * 上次喂鸡时间
     */
    private Date lastFeedTime;

    /**
     * 批次养殖场列表
     */
    private List<BatchPlantDto> batchPlantDtoList;
}
