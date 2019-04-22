package com.jaagro.cbs.api.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author :tony
 * @date :2019/04/22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Product implements Serializable {
    /**
     * 产品id
     */
    private Integer id;

    /**
     * 产品名称
     */
    private String productName;

    /**
     * 订单货物类型（1: 种苗 2: 饲料 3: 药品 4: 疫苗）
     */
    private Integer productType;

    /**
     * 生产商名称
     */
    private String manufacturer;

    /**
     * 产品图片地址
     */
    private String imageUrl;

    /**
     * SKU编码
     */
    private String skuNo;

    /**
     * 采购单价
     */
    private BigDecimal purchasePrice;

    /**
     * 销售单价
     */
    private BigDecimal salePrice;

    /**
     * 商品容量
     */
    private BigDecimal productCapacity;

    /**
     * 容量单位(1-ml,2-g)
     */
    private Integer capacityUnit;

    /**
     * 包装单位(1-瓶,2-袋,3-只,4-桶,5-盒, 6-吨)
     */
    private Integer packageUnit;

    /**
     * 商品备注
     */
    private String notes;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 是否有效（1-有效 0 -无效）
     */
    private Boolean enable;

    private static final long serialVersionUID = 1L;
}