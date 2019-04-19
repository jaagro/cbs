package com.jaagro.cbs.api.dto.plant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @author baiyiran
 * @Date 2019/2/22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class UpdateCoopDto implements Serializable {

    private Integer id;

    /**
     * 养殖场id
     */
    private Integer plantId;

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
     * 物联网设备供应商(1-梵龙)
     */
    private Integer iotBrand;

    /**
     * 物联网登录用户名
     */
    private String iotUsername;

    /**
     * 物联网登录密码
     */
    private String iotPassword;

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

}
