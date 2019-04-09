package com.jaagro.cbs.api.model;

import java.io.Serializable;
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
public class TechConsultRecord implements Serializable {
    /**
     * 技术咨询记录表id
     */
    private Integer id;

    /**
     * 养殖户id
     */
    private Integer customerId;

    /**
     * 养殖户名称
     */
    private String customerName;

    /**
     * 客户手机号
     */
    private String customerPhoneNumber;

    /**
     * 喂养日龄
     */
    private Integer dayAge;

    /**
     * 鸡舍id
     */
    private Integer coopId;

    /**
     * 鸡舍名称
     */
    private String coopName;

    /**
     * 养殖场id
     */
    private Integer plantId;

    /**
     * 养殖场名称
     */
    private String plantName;

    /**
     * 计划id
     */
    private Integer planId;

    /**
     * 批次号
     */
    private String batchNo;

    /**
     * 紧急程度(1-一般,2-次要,3-重要,4-紧急)
     */
    private Integer emergencyLevel;

    /**
     * 问题描述
     */
    private String problemDesc;

    /**
     * 技术咨询状态(0-待处理,1-已处理)
     */
    private Integer techConsultStatus;

    /**
     * 处理时间
     */
    private Date handleTime;

    /**
     * 技术员id
     */
    private Integer technicianId;

    /**
     * 处理人
     */
    private Integer handleUserId;

    /**
     * 处理描述
     */
    private String handleDesc;

    /**
     * 处理类型(1-电话询问,2-上门查看,3-已经解决,4-暂时搁置)
     */
    private Integer handleType;

    /**
     * 是否有效(0-无效,1-有效)
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