package com.jaagro.cbs.biz.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jaagro.cbs.api.dto.base.GetCustomerUserDto;
import com.jaagro.cbs.api.dto.order.*;
import com.jaagro.cbs.api.dto.plan.CustomerInfoParamDto;
import com.jaagro.cbs.api.dto.supplychain.PurchaseOrderManageCriteria;
import com.jaagro.cbs.api.dto.supplychain.ReturnPurchaseOrderManageDto;
import com.jaagro.cbs.api.dto.technicianapp.AppPurchaseOrderDto;
import com.jaagro.cbs.api.dto.technicianapp.ToDoQueryParam;
import com.jaagro.cbs.api.enums.PackageUnitEnum;
import com.jaagro.cbs.api.enums.PurchaseOrderStatusEnum;
import com.jaagro.cbs.api.model.BreedingPlan;
import com.jaagro.cbs.api.model.BreedingPlanExample;
import com.jaagro.cbs.api.model.PurchaseOrderItemsExample;
import com.jaagro.cbs.api.service.BreedingFarmerService;
import com.jaagro.cbs.api.service.BreedingPlanService;
import com.jaagro.cbs.api.service.BreedingPurchaseOrderService;
import com.jaagro.cbs.biz.mapper.BreedingPlanMapperExt;
import com.jaagro.cbs.biz.mapper.PurchaseOrderItemsMapperExt;
import com.jaagro.cbs.biz.mapper.PurchaseOrderMapperExt;
import com.jaagro.cbs.biz.service.UserClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: 采购订单相关api
 * @author: @Gao.
 * @create: 2019-03-14 13:29
 **/
@Service
@Slf4j
public class BreedingPurchaseOrderServiceImpl implements BreedingPurchaseOrderService {

    @Autowired
    private BreedingPlanMapperExt breedingPlanMapper;
    @Autowired
    private BreedingPlanService breedingPlanService;
    @Autowired
    private UserClientService userClientService;
    @Autowired
    private BreedingFarmerService breedingFarmerService;
    @Autowired
    private PurchaseOrderMapperExt purchaseOrderMapper;
    @Autowired
    private PurchaseOrderItemsMapperExt purchaseOrderItemsMapperExt;

    /**
     * 采购预置列表
     *
     * @return
     * @author @Gao.
     */
    @Override
    public PageInfo listPurchaseOrderPreset(PurchaseOrderPresetCriteriaDto dto) {
        PageHelper.startPage(dto.getPageNum(), dto.getPageSize());
        if (dto.getCustomerInfo() != null) {
            List<Integer> listCustomerIds = breedingPlanService.listCustomerIdsByKeyword(dto.getCustomerInfo());
            dto.setCustomerIds(listCustomerIds);
        }
        List<ReturnPurchaseOrderPresetDto> returnPurchaseOrderPresetDtos = breedingPlanMapper.listPurchaseOrderPreset(dto);
        if (!CollectionUtils.isEmpty(returnPurchaseOrderPresetDtos)) {
            for (ReturnPurchaseOrderPresetDto returnPurchaseOrderPresetDto : returnPurchaseOrderPresetDtos) {
                if (returnPurchaseOrderPresetDto.getCustomerId() != null) {
                    CustomerInfoParamDto customerInfo = breedingPlanService.getCustomerInfo(returnPurchaseOrderPresetDto.getCustomerId());
                    if (customerInfo.getCustomerName() != null && customerInfo.getCustomerPhone() != null) {
                        returnPurchaseOrderPresetDto
                                .setCustomerPhone(customerInfo.getCustomerPhone())
                                .setCustomerName(customerInfo.getCustomerName());
                    }
                }
                if (returnPurchaseOrderPresetDto.getPurchaseOrderStatus() != null) {
                    returnPurchaseOrderPresetDto
                            .setStrPurchaseOrderStatus(PurchaseOrderStatusEnum.getDescByCode(returnPurchaseOrderPresetDto.getPurchaseOrderStatus()));
                }
                if (returnPurchaseOrderPresetDto.getPurchaseOrderId() != null) {
                    AccumulationPurchaseOrderParamDto paramDto = breedingPlanService.accumulationPurchaseOrderItems(returnPurchaseOrderPresetDto.getPurchaseOrderId());
                    if (paramDto.getUnit() != null) {
                        returnPurchaseOrderPresetDto.setStrUnit(PackageUnitEnum.getDescByCode(paramDto.getUnit()));
                    }
                    if (paramDto.getQuantity() != null) {
                        returnPurchaseOrderPresetDto.setQuantity(paramDto.getQuantity());
                    }
                }
                if (returnPurchaseOrderPresetDto.getSignerId() != null) {
                    GetCustomerUserDto customerUser = userClientService.getCustomerUserById(returnPurchaseOrderPresetDto.getSignerId());
                    if (customerUser != null) {
                        if (customerUser.getName() != null) {
                            returnPurchaseOrderPresetDto.setSignerName(customerUser.getName());
                        }
                        if (customerUser.getPhoneNumber() != null) {
                            returnPurchaseOrderPresetDto.setSignerPhone(customerUser.getPhoneNumber());
                        }
                    }
                }
            }
        }
        return new PageInfo(returnPurchaseOrderPresetDtos);
    }

    /**
     * 采购预置详情
     *
     * @param purchaseOrderId
     * @return
     */
    @Override
    public ReturnPurchaseOrderPresetDetailsDto purchaseOrderPresetDetails(Integer purchaseOrderId) {
        ReturnFarmerPurchaseOrderDetailsDto returnFarmerPurchaseOrderDetailsDto = breedingFarmerService.purchaseOrderDetails(purchaseOrderId);
        ReturnPurchaseOrderPresetDetailsDto returnPurchaseOrderPresetDetailsDto = new ReturnPurchaseOrderPresetDetailsDto();
        //采购信息
        if (returnFarmerPurchaseOrderDetailsDto != null) {
            BeanUtils.copyProperties(returnFarmerPurchaseOrderDetailsDto, returnPurchaseOrderPresetDetailsDto);
            if (returnFarmerPurchaseOrderDetailsDto.getReturnProductDtos() != null) {
                returnPurchaseOrderPresetDetailsDto.setReturnProductDtos(returnFarmerPurchaseOrderDetailsDto.getReturnProductDtos());
            }
            //批次信息
            if (returnFarmerPurchaseOrderDetailsDto.getPlanId() != null) {
                BreedingPlanExample breedingPlanExample = new BreedingPlanExample();
                breedingPlanExample
                        .createCriteria()
                        .andEnableEqualTo(true)
                        .andIdEqualTo(returnFarmerPurchaseOrderDetailsDto.getPlanId());
                List<BreedingPlan> breedingPlans = breedingPlanMapper.selectByExample(breedingPlanExample);
                if (!CollectionUtils.isEmpty(breedingPlans)) {
                    BreedingPlan breedingPlan = breedingPlans.get(0);
                    if (breedingPlan.getPlanChickenQuantity() != null) {
                        returnPurchaseOrderPresetDetailsDto.setPlanChickenQuantity(breedingPlan.getPlanChickenQuantity());
                    }
                    if (breedingPlan.getPlanTime() != null) {
                        returnPurchaseOrderPresetDetailsDto.setPlanTime(breedingPlan.getPlanTime());
                    }
                    //客户信息
                    if (breedingPlan.getCustomerId() != null) {
                        CustomerInfoParamDto customerInfo = breedingPlanService.getCustomerInfo(breedingPlan.getCustomerId());
                        if (customerInfo != null) {
                            if (customerInfo.getCustomerName() != null) {
                                returnPurchaseOrderPresetDetailsDto
                                        .setCustomerName(customerInfo.getCustomerName());
                            }
                            if (customerInfo.getCustomerPhone() != null) {
                                returnPurchaseOrderPresetDetailsDto
                                        .setCustomerPhone(customerInfo.getCustomerPhone());
                            }
                            if (customerInfo.getCustomerAddress() != null) {
                                returnPurchaseOrderPresetDetailsDto.setCustomerAddress(customerInfo.getCustomerAddress());
                            }
                        }
                    }
                }
            }
        }
        return returnPurchaseOrderPresetDetailsDto;
    }

    /**
     * 供应链采购订单列表
     *
     * @param criteria
     * @return
     * @author @Gao.
     */
    @Override
    public List<ReturnPurchaseOrderManageDto> listPurchasingManagement(PurchaseOrderManageCriteria criteria) {
        PageHelper.startPage(criteria.getPageNum(), criteria.getPageSize());
        if (criteria.getCustomerInfo() != null) {
            List<Integer> listCustomerIds = breedingPlanService.listCustomerIdsByKeyword(criteria.getCustomerInfo());
            criteria.setCustomerIds(listCustomerIds);
        }
        List<ReturnPurchaseOrderManageDto> returnPurchaseOrderManageDtos = purchaseOrderMapper.listPurchasingManagement(criteria);
        //to do
        return null;
    }

    /**
     * 获取技术员app待办-采购订单列表
     *
     * @param criteriaDto
     * @return
     */
    @Override
    public PageInfo listPurchaseOrdersApp(ToDoQueryParam criteriaDto) {

        PageHelper.startPage(criteriaDto.getPageNum(), criteriaDto.getPageSize());
        Map<String, Integer> queryParam = new HashMap<>();
        queryParam.put("technicianId", criteriaDto.getTechnicianId());
        List<AppPurchaseOrderDto> dtoList = purchaseOrderMapper.listPurchaseOrdersApp(queryParam);
        for (AppPurchaseOrderDto purchaseOrderDto : dtoList) {
            PurchaseOrderItemsExample example = new PurchaseOrderItemsExample();
            example.createCriteria()
                    .andPurchaseOrderIdEqualTo(purchaseOrderDto.getId())
                    .andEnableEqualTo(true);

            purchaseOrderDto.setOrderItems(purchaseOrderItemsMapperExt.selectByExample(example));
        }

        return new PageInfo(dtoList);
    }


}
