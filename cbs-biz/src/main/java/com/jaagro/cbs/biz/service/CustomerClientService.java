package com.jaagro.cbs.biz.service;

import com.jaagro.cbs.api.dto.base.CustomerContacts;
import com.jaagro.cbs.api.dto.base.CustomerContactsReturnDto;
import com.jaagro.cbs.api.dto.base.ShowCustomerDto;
import com.jaagro.utils.BaseResponse;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author tony
 */
@FeignClient(value = "${feignClient.application.crm}")
public interface CustomerClientService {

    /**
     * 从crm项目获取显示客户对象
     *
     * @param id
     * @return
     */
    @GetMapping("/getShowCustomer/{id}")
    ShowCustomerDto getShowCustomerById(@PathVariable("id") Integer id);

    /**
     * 根据关键字查询客户id集合
     *
     * @param keyword
     * @return
     */
    @GetMapping("/listCustomerIdByKeyWord/{keyword}")
    BaseResponse<List<Integer>> listCustomerIdByKeyWord(@PathVariable("keyword") String keyword);

    /**
     * 查询客户信息
     *
     * @param id
     * @return
     */
    @GetMapping("/getContactsById/{id}")
    BaseResponse<CustomerContacts> getContactsById(@PathVariable("id") Integer id);

    /**
     * 根据客户id查询主客户联系人
     *
     * @param customerId
     * @return
     */
    @Ignore
    @GetMapping("/getCustomerContactByCustomerId/{customerId}")
    CustomerContactsReturnDto getCustomerContactByCustomerId(@PathVariable("customerId") Integer customerId);

    /**
     * 查询客户有效合同
     *
     * @param customerId
     * @return
     */
    @GetMapping("/listContractByCustomerId/{customerId}")
    BaseResponse listByCustomerId(@PathVariable("customerId") Integer customerId);
}
