package com.jaagro.cbs.api.model;

import java.io.Serializable;
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
public class TenantDrugStock implements Serializable {
    /**
     * 租户药品库存表id
     */
    private Integer id;

    /**
     * 租户id
     */
    private Integer tenantId;

    /**
     * 产品id
     */
    private Integer productId;

    /**
     * 库存数量
     */
    private Integer stockQuantity;

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