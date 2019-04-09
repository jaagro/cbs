package com.jaagro.cbs.web.vo.technicianapp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @description: 技术端 确认出栏列表
 * @author: baiyiran
 * @create: 2019-04-09
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class UnConfirmChickenPlanVo implements Serializable {
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
     * 计划上鸡数量
     */
    private Integer planChickenQuantity;

    /**
     * 上鸡时间
     */
    private Date planTime;

    /**
     * 创建时间
     */
    private Date createTime;

}
