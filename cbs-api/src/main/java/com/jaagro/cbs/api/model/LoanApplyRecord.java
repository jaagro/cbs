package com.jaagro.cbs.api.model;

import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author :asus
 * @date :2019/03/29
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class LoanApplyRecord implements Serializable {
    /**
     * 贷款申请记录表id
     */
    private Integer id;

    /**
     * 客户id
     */
    private Integer customerId;

    /**
     * 计划id
     */
    private Integer planId;

    /**
     * 采购订单id
     */
    private Integer purchaseOrderId;

    /**
     * 是否有效(0-无效,1-有效)
     */
    private Boolean enable;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date modifyTime;

    private static final long serialVersionUID = 1L;
}