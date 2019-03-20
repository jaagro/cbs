package com.jaagro.cbs.api.dto.standard;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 养殖模板参数日龄对应参数值
 * @author yj
 * @date 2019/3/13 14:20
 */
@Data
@Accessors(chain = true)
public class BreedingStandardParameterItemDto implements Serializable{
    private static final long serialVersionUID = -4310951495259536431L;
    /**
     * 养殖模板参数id
     */
    private Integer id;
    /**
     * 日龄
     */
    private Integer dayAge;
    /**
     * 参考值下限
     */
    private BigDecimal lowerLimit;

    /**
     * 参考值上限(临界值时有效)
     */
    private BigDecimal upperLimit;

    /**
     * 参数值(标准值,临界值)
     */
    private String paramValue;

    /**
     * 临界值方向(1->=,2<=)
     */
    private Integer thresholdDirection;
}
