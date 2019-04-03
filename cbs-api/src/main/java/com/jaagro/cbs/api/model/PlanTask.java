package com.jaagro.cbs.api.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author :gaoxin
 * @date :2019/04/03
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class PlanTask implements Serializable {
    /**
     * 计划任务表id
     */
    private Integer id;

    /**
     * 养殖计划任务编号
     */
    private String planTaskNo;

    /**
     * 计划id
     */
    private Integer planId;

    /**
     * 计划任务名称
     */
    private String planTaskName;

    /**
     * 客户id
     */
    private Integer customerId;

    /**
     * 客户名称
     */
    private String customerName;

    /**
     * 客户手机号
     */
    private String customerPhoneNumber;

    /**
     * 重量
     */
    private BigDecimal weight;

    /**
     * 数量
     */
    private Integer quantity;

    /**
     * 单位(1-千克,2-只,3-个)
     */
    private Integer unit;

    /**
     * 货物类型(1-鸡苗,2-药品,3-饲料)
     */
    private Integer goodsType;

    /**
     * 计划时间
     */
    private Date planTime;

    /**
     * 执行时间
     */
    private Date executeTime;

    /**
     * 金额
     */
    private BigDecimal money;

    /**
     *  任务状态(0-待执行,1-已执行,2-已暂停)
     */
    private Integer planTaskStatus;

    /**
     * 备注
     */
    private String notes;

    /**
     * 是否有效（1-有效 0-无效）
     */
    private Boolean enable;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人
     */
    private Integer createUserId;

    /**
     * 更新时间
     */
    private Date modifyTime;

    /**
     * 更新人
     */
    private Integer modifyUserId;

    private static final long serialVersionUID = 1L;
}