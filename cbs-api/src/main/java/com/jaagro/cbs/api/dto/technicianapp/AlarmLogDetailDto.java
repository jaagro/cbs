package com.jaagro.cbs.api.dto.technicianapp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author gavin
 *
 * @Date 20190408
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class AlarmLogDetailDto implements Serializable {

    private static final long serialVersionUID = 3538281775105066673L;
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
     * 标准值
     */
    private String paramStandardValue;

    /**
     * 养殖户id
     */
    private Integer customerId;
    /**
     * 客户名称
     */
    private String customerName;
    /**
     * 养殖天数
     */
    private Integer breedingDays;
    /**
     * 设备名称
     */
    private String deviceName;
    /**
     * 设备报警次数
     */
    private Integer times;

    /**
     * 批次号
     */
    private String batchNo;
    /**
     * 养殖场名称
     */
    private String plantName;
    /**
     * 鸡舍名称
     */
    private String coopName;
    /**
     * 设备的最近一次报警值
     */
    private BigDecimal latestValue;
    /**
     * 设备的最近一次报警值时间
     */
    private Date latestAlarmDate;

    /**
     * 客户联系人名称
     */
    private String customerContactName;

    /**
     * 联系人电话
     */
    private String customerContactPhone;

    /**
     * 鸡舍存栏量
     */
    private int livingAmount;
    /**
     * 计划创建时间
     */
    private Date planCreateTime;

    /**
     * 处理的方式、途径
     */
    private String handleTypeStr;

    /**
     * 处理备注说明
     */
    private String handleDesc;

}
