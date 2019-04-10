package com.jaagro.cbs.web.vo.technicianapp;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author gavin
 * @Date 20190410
 */
@Data
@Accessors(chain = true)
public class EmployeeInfoVo implements Serializable {


    /**
     * 员工姓名
     */
    private String name;

    /**
     * 手机号
     */
    private String phone;
    /**
     * 员工所属租户-公司名称
     */
    private String tenantName;

    /**
     * 员工拥有的角色名称
     */
    private String roleName;
}
