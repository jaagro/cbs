package com.jaagro.cbs.api.dto.farmer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @description: 采购商品
 * @author: @Gao.
 * @create: 2019-03-09 16:46
 **/
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ReturnProductDto implements Serializable {

    /**
     * 产品名称
     */
    private String productName;

    /**
     * 订单货物类型（1: 种苗 2: 饲料 3: 药品）
     */
    private Integer productType;

    /**
     * 购买数量 重量
     */
    private BigDecimal quantity;

    /**
     * 单位
     */

    private String unit;


}
