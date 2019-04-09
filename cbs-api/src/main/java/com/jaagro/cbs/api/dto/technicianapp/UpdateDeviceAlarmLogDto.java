package com.jaagro.cbs.api.dto.technicianapp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Author gavin
 *
 * @Date 20190408
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class UpdateDeviceAlarmLogDto implements Serializable {

    private static final long serialVersionUID = 8456524244134888224L;
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
     * 处理描述
     */
    private String handleDesc;

    /**
     * 处理类型(1-电话询问,2-远程协助,3-上门处理,4-故障误报)
     */
    private Integer handleType;


}
