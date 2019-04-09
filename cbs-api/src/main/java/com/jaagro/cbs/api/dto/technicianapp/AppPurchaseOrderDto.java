package com.jaagro.cbs.api.dto.technicianapp;

import com.jaagro.cbs.api.model.PurchaseOrder;
import com.jaagro.cbs.api.model.PurchaseOrderItems;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @description: 采购订单列表对象
 * @author: @gavin
 * @create: 2019-04-09 13:59
 **/
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class AppPurchaseOrderDto extends PurchaseOrder implements Serializable {

    /**
     * 上鸡时间
     */
    private Date planTime;
    /**
     * 计划上鸡数量
     */
    private Integer planChickenQuantity;

    private List<PurchaseOrderItems> orderItems;
}
