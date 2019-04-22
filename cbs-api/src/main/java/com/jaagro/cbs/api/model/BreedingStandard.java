package com.jaagro.cbs.api.model;

import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author :tony
 * @date :2019/04/22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class BreedingStandard implements Serializable {
    /**
     * 养殖标准方案表id
     */
    private Integer id;

    /**
     * 标准方案名称
     */
    private String standardName;

    /**
     * 养殖类型(1-平养,2-笼养,3-网养)
     */
    private Integer breedingType;

    /**
     * 养殖天数
     */
    private Integer breedingDays;

    /**
     * 模板状态(0-待参数配置,1-可用)
     */
    private Integer standardStatus;

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