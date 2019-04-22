package com.jaagro.cbs.api.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
<<<<<<< HEAD
 * @author :tony
 * @date :2019/04/22
=======
 * @author :asus
 * @date :2019/04/19
>>>>>>> jaagro/v1.0.1-dev
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class BreedingStandardDrug implements Serializable {
    /**
     * 养殖参数模板药品配置表id
     */
    private Integer id;

    /**
     * 养殖模板id
     */
    private Integer breedingStandardId;

    /**
     * 商品id
     */
    private Integer productId;

    /**
     * sku编码
     */
    private String skuNo;

    /**
     * 日龄起(包含)
     */
    private Integer dayAgeStart;

    /**
     * 日龄止(包含)
     */
    private Integer dayAgeEnd;

    /**
     * 停药标识(0-否,1-是)
     */
    private Boolean stopDrugFlag;

    /**
     * 千只日喂量
     */
    private BigDecimal feedVolume;

    /**
     * 该日龄区间天数
     */
    private Integer days;

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