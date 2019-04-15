package com.jaagro.cbs.api.dto.technicianapp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 养殖计划鸡舍信息
 *
 * @author byr
 * @date 2019/4/15
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class BreedingPlanCoopsDto implements Serializable {
    /**
     * 客户鸡舍信息表id
     */
    private Integer id;
    /**
     * 鸡舍名称
     */
    private String coopName;

    /**
     * 容量
     */
    private Integer capacity;

    /**
     * 在养数量
     */
    private Integer breedingValue;

    /**
     * 绑定硬件的数量
     */
    private Integer deviceQuantity;

    /**
     * 状态(0-维护中,1-空闲,2-饲养中)
     */
    private Integer coopStatus;

    /**
     * 上次起始时间
     */
    private Date lastStartDate;

    /**
     * 上次截止时间
     */
    private Date lastEndDate;

}
