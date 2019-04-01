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
 * @date :2019/04/01
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class BreedingRecordItems implements Serializable {
    /**
     * 喂养记录明细表id
     */
    private Integer id;

    /**
     * 养殖记录id
     */
    private Integer breedingRecordId;

    /**
     * 产品id
     */
    private Integer productId;

    /**
     * 喂养数量
     */
    private BigDecimal breedingValue;

    /**
     * 喂养时间
     */
    private Date breedingTime;

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

    private static final long serialVersionUID = 1L;
}