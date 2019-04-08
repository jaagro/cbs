package com.jaagro.cbs.api.dto.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

/**
 * @description: 采购订单查询参数
 * @author: @Gao.
 * @create: 2019-03-04 16:48
 **/
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrderItemsParamDto implements Serializable {
    /**
     * 商品类型
     */
    private Integer productType;
    /**
     * 养殖计划集合id
     */
    private Set<Integer> planIds;
}
