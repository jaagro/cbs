package com.jaagro.cbs.api.model;

import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author :gaoxin
 * @date :2019/03/27
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Plant implements Serializable {
    /**
     * 养殖场id
     */
    private Integer id;

    /**
     * 租户id
     */
    private Integer tenantId;

    /**
     * 养殖户id
     */
    private Integer customerId;

    /**
     * 养殖场名称
     */
    private String plantName;

    /**
     * 养殖场类型(1-平养,2-笼养,3-网养)
     */
    private Integer plantType;

    /**
     * 产权情况
     */
    private String equityType;

    /**
     * 使用年限
     */
    private Integer durableYears;

    /**
     * 是否可以扩建(0-不可扩建,1-可扩建)
     */
    private Boolean expandable;

    /**
     * 省
     */
    private String province;

    /**
     * 市
     */
    private String city;

    /**
     * 区县
     */
    private String county;

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
     * 修改时间
     */
    private Date modifyTime;

    /**
     * 修改人
     */
    private Integer modifyUserId;

    private static final long serialVersionUID = 1L;
}