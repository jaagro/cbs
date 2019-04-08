package com.jaagro.cbs.api.model;

import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author :gavinwang
 * @date :2019/04/08
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ContractSource implements Serializable {
    /**
     * 计划合同资源表id
     */
    private Integer id;

    /**
     * 计划id
     */
    private Integer planId;

    /**
     * 计划合同表id
     */
    private Integer planContractId;

    /**
     * 资质类型(1-图片,2-文档)
     */
    private Integer sourceType;

    /**
     * 资源地址
     */
    private String sourceUrl;

    /**
     * 证件状态(0-未审核 1-正常 2-审核未通过 3-停止合作)
     */
    private Integer sourceStatus;

    /**
     * 备注
     */
    private String notes;

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
     * 是否有效（1-有效 0-无效）
     */
    private Boolean enable;

    private static final long serialVersionUID = 1L;
}