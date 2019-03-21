package com.jaagro.cbs.api.model;

import java.io.Serializable;
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
public class MaterialConfig implements Serializable {
    /**
     * 养殖参数模板药品配置表id
     */
    private Integer id;

    /**
     * 商品id
     */
    private Integer productId;

    /**
     * 描述说明
     */
    private String description;

    /**
     * 日龄起
     */
    private Integer dayAgeStart;

    /**
     * 日龄止
     */
    private Integer dayAgeEnd;

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

    /**
     * 订单阶段：1-第一阶段；2-第二阶段；3-第三阶段
     */
    private Integer orderPhase;

    private Integer productType;

    private static final long serialVersionUID = 1L;
}