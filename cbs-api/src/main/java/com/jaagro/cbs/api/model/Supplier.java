package com.jaagro.cbs.api.model;

import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
<<<<<<< HEAD
 * @author :tony
 * @date :2019/04/22
=======
 * @author :asus
 * @date :2019/04/19
>>>>>>> jaagro/v1.0.1-dev
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Supplier implements Serializable {
    /**
     * 供应商id
     */
    private Integer id;

    /**
     * 供应商名称
     */
    private String supplierName;

    /**
     * 供应商类型(1: 种苗 2: 饲料 3: 药品)
     */
    private Integer supplierType;

    /**
     * 租户id
     */
    private Integer tenantId;

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
     * 详细地址
     */
    private String address;

    /**
     * 联系人
     */
    private String contact;

    /**
     * 联系人手机号
     */
    private String contactPhoneNumber;

    /**
     * 供应商状态(0未审核，1-正常合作  10-停止合作 11-审核未通过 13-作废)
     */
    private Integer supplierStatus;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人
     */
    private Integer createUserId;

    /**
     * 更新人
     */
    private Date modifyTime;

    /**
     * 更新人
     */
    private Integer modifyUserId;

    /**
     * 逻辑删除
     */
    private Boolean enable;

    private static final long serialVersionUID = 1L;
}