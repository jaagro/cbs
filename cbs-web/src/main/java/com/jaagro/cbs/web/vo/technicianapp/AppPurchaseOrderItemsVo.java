package com.jaagro.cbs.web.vo.technicianapp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author :gavinwang
 * @date :2019/04/09
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class AppPurchaseOrderItemsVo implements Serializable {

    /**
     * 产品名称
     */
    private String productName;

    /**
     * 购买数量
     */
    private BigDecimal quantity;

    /**
     * 跟产品表的包装单位一致(1-瓶,2-袋,3-只,4-桶,5-盒, 6-吨)
     */

    private String strUnit;

}