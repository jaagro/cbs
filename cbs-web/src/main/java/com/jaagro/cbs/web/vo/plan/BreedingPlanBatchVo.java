package com.jaagro.cbs.web.vo.plan;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jaagro.cbs.api.model.Plant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @description: 批次管理
 * @author: baiyiran
 * @create: 2019-02-26
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class BreedingPlanBatchVo implements Serializable {
    /**
     * 计划id
     */
    private Integer id;

    /**
     * 批次号
     */
    private String batchNo;

    /**
     * 养殖户的名称
     */
    private String customerName;

    /**
     * 养殖户电话
     */
    private String customerPhone;

    /**
     * 计划状态(0-待录入合同,1-待参数纠偏,2-待上鸡签收,3-养殖中,4-待出栏确认,5-已完成,6-已取消)
     */
    private Integer planStatus;

    /**
     * 计划上鸡数量
     */
    private Integer planChickenQuantity;

    /**
     * 上鸡时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date planTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 进度 - 已进行的天数
     */
    private Integer alreadyDayAge;

    /**
     * 进度 - 日龄
     */
    private Integer AllDayAge;

    /**
     * 养殖场
     */
    private List<Plant> plants;
}
