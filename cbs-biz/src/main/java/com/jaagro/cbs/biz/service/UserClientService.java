package com.jaagro.cbs.biz.service;

import com.jaagro.cbs.api.dto.base.EmployeeAndRoleDto;
import com.jaagro.cbs.api.dto.base.GetCustomerUserDto;
import com.jaagro.cbs.api.dto.base.GetEmployeeDto;
import com.jaagro.cbs.api.dto.base.ListEmployeeDto;
import com.jaagro.constant.UserInfo;
import com.jaagro.utils.BaseResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @description: User服务
 * @author: @Gao.
 * @create: 2019-02-26 15:59
 **/
@FeignClient("user")
public interface UserClientService {

    /**
     * 获取当前token可查询的数据范围 -- 依据部门id
     *
     * @return
     */
    @PostMapping("/getDownDepartment")
    List<Integer> getDownDepartment();

    /**
     * 获取指定部门id及下属部门id数组
     *
     * @return
     */
    @PostMapping("/getDownDepartmentByDeptId/{deptId}")
    List<Integer> getDownDepartmentByDeptId(@PathVariable("deptId") Integer deptId);

    /**
     * 根据部门id查询部门名称
     *
     * @param id
     * @return
     */
    @GetMapping("/getDeptNameById/{id}")
    String getDeptNameById(@PathVariable("id") Integer id);

    /**
     * 根据id列表查用户列表
     *
     * @param userIdList
     * @param userType
     * @return
     */
    @GetMapping("/listUserInfo")
    List<UserInfo> listUserInfo(@RequestParam("userIdList") List<Integer> userIdList, @RequestParam("userType") String userType);

    /**
     * 根据userId获取对应的用户数据
     *
     * @param userId
     * @return
     */
    @GetMapping("/getGlobalUser/{userId}")
    BaseResponse<UserInfo> getGlobalUser(@PathVariable("userId") int userId);

    /**
     * 根据部门id查询员工列表
     *
     * @param
     * @return
     */
    @GetMapping("/listEmpByDeptId/{deptId}")
    BaseResponse<List<ListEmployeeDto>> getEmpByDeptId(@PathVariable("deptId") Integer deptId);

    /**
     * 获取技术员列表
     *
     * @return
     * @author yj
     */

    @GetMapping("/listTechnician")
    BaseResponse<List<ListEmployeeDto>> listTechnician();

    /**
     * 根据客户id 查询客户信息
     *
     * @param id
     * @return
     */
    @GetMapping("/customerUser/{id}")
    GetCustomerUserDto getCustomerUserById(@PathVariable("id") Integer id);

    /**
     * 查询单个技术员
     *
     * @param employeeId
     * @return
     */
    @GetMapping("/getTechnician/{employeeId}")
    BaseResponse<ListEmployeeDto> getTechnicianById(@PathVariable("employeeId") Integer employeeId);

    /**
     * 获取所有员工和对应的角色列表
     *
     * @return
     */
    @GetMapping("/getAllEmpAndRole")
    BaseResponse<List<EmployeeAndRoleDto>> getAllEmpAndRole();

    /**
     * 单个查询
     *
     * @param id
     * @return
     */
    @GetMapping("/getEmpById/{id}")
    BaseResponse<GetEmployeeDto> getEmp(@PathVariable("id") Integer id);
}

