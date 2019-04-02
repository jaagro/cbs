package com.jaagro.cbs.api.model;

import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author :gaoxin
 * @date :2019/04/01
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Coop implements Serializable {
    /**
     * 客户鸡舍信息表id
     */
    private Integer id;

    /**
     * 鸡舍编号
     */
    private String coopNo;

    /**
     * 鸡舍名称
     */
    private String coopName;

    /**
     * 客户id
     */
    private Integer customerId;

    /**
     * 养殖场id
     */
    private Integer plantId;

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

    /**
     * 备注
     */
    private String notes;

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

    /**
     * 是否有效（1- 有效 0- 无效）
     */
    private Boolean enable;

    private static final long serialVersionUID = 1L;
}