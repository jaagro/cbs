package com.jaagro.cbs.api.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author :gavinwang
 * @date :2019/04/08
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class DeviceAlarmLog implements Serializable {
    /**
     * 设备报警日志主键id
     */
    private Integer id;

    /**
     * 计划id
     */
    private Integer planId;

    /**
     * 养殖场id
     */
    private Integer plantId;

    /**
     * 鸡舍id
     */
    private Integer coopId;

    /**
     * 设备id
     */
    private Integer deviceId;

    /**
     * 日龄
     */
    private Integer dayAge;

    /**
     * 当前设备值
     */
    private BigDecimal currentValue;

    /**
     * 标准值
     */
    private String paramStandardValue;

    /**
     * 处理状态(0-待处理,1-已处理)
     */
    private Integer handleStatus;

    /**
     * 处理时间
     */
    private Date handleTime;

    /**
     * 处理人id
     */
    private Integer handleUserId;

    /**
     * 处理描述
     */
    private String handleDesc;

    /**
     * 处理类型(1-电话询问,2-远程协助,3-上门处理,4-故障误报)
     */
    private Integer handleType;

    /**
     * 创建时间
     */
    private Date createTime;

    private static final long serialVersionUID = 1L;
}