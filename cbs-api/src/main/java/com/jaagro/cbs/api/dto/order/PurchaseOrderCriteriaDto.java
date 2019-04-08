package com.jaagro.cbs.api.dto.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;


/**
 * @description: 采购订单查询条件
 * @author: @Gao.
 * @create: 2019-03-30 15:51
 **/
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrderCriteriaDto implements Serializable {

    /**
     * 客户id
     */
    private Integer customerId;

    /**
     * 状态(1-已下单 ,2－待送达 ,3-已签收)
     */
    private List<Integer> purchaseOrderStatus;
}
