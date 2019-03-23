package com.jaagro.cbs.api.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author :asus
 * @date :2019/03/21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class CustomerDrugStock implements Serializable {
    /**
     * 客户药品库存表id
     */
    private Integer id;

    /**
     * 客户id
     */
    private Integer customerId;

    /**
     * sku编码
     */
    private String skuNo;

    /**
     * 药品图片地址
     */
    private String imageUrl;

    /**
     * 药品名称
     */
    private String drugName;

    /**
     * 库存数量
     */
    private Integer stockQuantity;

    /**
     * 采购单价
     */
    private BigDecimal purchasePrice;

    /**
     * 是否参与预警(0-不参与,1-参与)
     */
    private Boolean alarm;

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