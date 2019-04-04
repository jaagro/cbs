package com.jaagro.cbs.api.dto.base;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @description: 客户信息
 * @author: @Gao.
 * @create: 2019-03-05 14:26
 **/

@Data
@Accessors(chain = true)
public class GetCustomerUserDto implements Serializable {
    /**
     * 用户ID
     */
    private Integer id;

    /**
     * 所属客户(关联客户表)
     */
    private Integer relevanceId;

    /**
     * 显示名称
     */
    private String name;

    /**
     * 手机号码
     */
    private String phoneNumber;


}
