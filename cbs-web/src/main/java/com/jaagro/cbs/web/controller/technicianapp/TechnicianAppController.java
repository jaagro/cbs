package com.jaagro.cbs.web.controller.technicianapp;

import com.github.pagehelper.PageInfo;
import com.jaagro.cbs.api.dto.base.CustomerContactsReturnDto;
import com.jaagro.cbs.api.dto.farmer.BreedingBatchParamDto;
import com.jaagro.cbs.api.dto.plan.ReturnBreedingPlanDto;
import com.jaagro.cbs.api.dto.product.ListProductCriteria;
import com.jaagro.cbs.api.dto.technicianapp.AppPurchaseOrderDto;
import com.jaagro.cbs.api.dto.technicianapp.BreedingPlanCriteriaDto;
import com.jaagro.cbs.api.dto.technicianapp.ToDoQueryParam;
import com.jaagro.cbs.api.enums.PackageUnitEnum;
import com.jaagro.cbs.api.enums.ProductTypeEnum;
import com.jaagro.cbs.api.enums.PurchaseOrderStatusEnum;
import com.jaagro.cbs.api.model.Product;
import com.jaagro.cbs.api.model.PurchaseOrderItems;
import com.jaagro.cbs.api.service.BreedingPlanService;
import com.jaagro.cbs.api.service.BreedingPurchaseOrderService;
import com.jaagro.cbs.api.service.ProductService;
import com.jaagro.cbs.biz.service.CustomerClientService;
import com.jaagro.cbs.web.vo.technicianapp.AppPurchaseOrderItemsVo;
import com.jaagro.cbs.web.vo.technicianapp.AppPurchaseOrderVo;
import com.jaagro.cbs.web.vo.technicianapp.UnConfirmChickenPlanVo;
import com.jaagro.utils.BaseResponse;
import com.jaagro.utils.ResponseStatusCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 技术管理app端
 *
 * @author baiyiran
 * @date 2019-04-08
 */
@RestController
@Api(description = "技术管理app端", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class TechnicianAppController {
    @Autowired
    private BreedingPlanService breedingPlanService;
    @Autowired
    private BreedingPurchaseOrderService breedingPurchaseOrderService;
    @Autowired
    private CustomerClientService customerClientService;

    @Autowired
    private ProductService productService;

    /**
     * 养殖
     *
     * @param dto
     * @return
     * @author byr
     */
    @PostMapping("/listBreedingBatchForTechnician")
    @ApiOperation("养殖")
    public BaseResponse listBreedingBatchForTechnician(@RequestBody @Validated BreedingBatchParamDto dto) {
        log.info("O listBreedingBatchForFarmer params={}", dto);
        return BaseResponse.successInstance(breedingPlanService.listBreedingBatchForTechnician(dto));
    }

    @PostMapping("/listPurchaseOrdersApp")
    @ApiOperation("技术员App待办-采购订单列表")
    public BaseResponse<PageInfo> listPurchaseOrdersApp(@RequestBody ToDoQueryParam criteriaDto) {
        if (StringUtils.isEmpty(criteriaDto.getPageNum())) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "pageNum不能为空");
        }
        if (StringUtils.isEmpty(criteriaDto.getPageSize())) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "pageSize不能为空");
        }
        if (StringUtils.isEmpty(criteriaDto.getTechnicianId())) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "技术员ID不能为空");
        }

        PageInfo pageInfo = breedingPurchaseOrderService.listPurchaseOrdersApp(criteriaDto);
        if (pageInfo != null) {
            ListProductCriteria criteria = new ListProductCriteria();
            List<Product> products = productService.listByCriteria(criteria);

            List<AppPurchaseOrderVo> voList = new ArrayList<>();
            List<AppPurchaseOrderDto> doList = pageInfo.getList();
            if (!CollectionUtils.isEmpty(doList)) {
                for (AppPurchaseOrderDto purchaseOrderDto : doList) {
                    AppPurchaseOrderVo resultVo = new AppPurchaseOrderVo();
                    BeanUtils.copyProperties(purchaseOrderDto, resultVo);
                    //客户、客户联系人
                    CustomerContactsReturnDto customerDto = customerClientService.getCustomerContactByCustomerId(purchaseOrderDto.getCustomerId());
                    if (null != customerDto) {
                        resultVo.setCustomerName(customerDto.getCustomerName());
                    }
                    resultVo.setStrPurchaseOrderStatus(PurchaseOrderStatusEnum.getDescByCode(purchaseOrderDto.getPurchaseOrderStatus()));
                    resultVo.setStrProductType(ProductTypeEnum.getDescByCode(purchaseOrderDto.getProductType()));

                    List<AppPurchaseOrderItemsVo> itemsVos = new ArrayList<>();
                    List<PurchaseOrderItems> items = purchaseOrderDto.getOrderItems();
                    if (!CollectionUtils.isEmpty(items)) {
                        for (PurchaseOrderItems item : items) {
                            AppPurchaseOrderItemsVo itemsVo = new AppPurchaseOrderItemsVo();
                            List<Product> productDos = products.stream().filter(c -> c.getId().equals(item.getProductId())).collect(Collectors.toList());
                            if (!CollectionUtils.isEmpty(productDos)) {
                                itemsVo.setProductName(productDos.get(0).getProductName());
                                itemsVo.setQuantity(item.getQuantity());
                                itemsVo.setStrUnit(PackageUnitEnum.getDescByCode(item.getUnit()));
                            }
                            itemsVos.add(itemsVo);
                        }
                        //订单明细
                        resultVo.setItemsVoList(itemsVos);
                    }
                    voList.add(resultVo);
                }
            }
            pageInfo.setList(voList);
        }
        return BaseResponse.successInstance(pageInfo);
    }


    @GetMapping("/purchaseOrderDetailsApp/{purchaseOrderId}")
    @ApiOperation("采购订单详情")
    public BaseResponse purchaseOrderDetailsApp(@PathVariable("purchaseOrderId") Integer purchaseOrderId) {
        if (purchaseOrderId == null) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "采购订单id不能为空");
        }
        return BaseResponse.successInstance(breedingPurchaseOrderService.purchaseOrderPresetDetails(purchaseOrderId));
    }


    /**
     * 确认出栏列表
     *
     * @param dto
     * @return
     */
    @PostMapping("/listUnConfirmChickenPlan")
    @ApiOperation("确认出栏列表")
    public BaseResponse listPublishedChickenPlan(@RequestBody BreedingPlanCriteriaDto dto) {
        if (dto.getPageNum() == null) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "起始页不能为空");
        }
        if (dto.getPageSize() == null) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "每页条数不能为空");
        }
        if (dto.getPlanStatus() == null) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "计划状态不能为空");
        }
        PageInfo pageInfo = breedingPlanService.listBreedingPlanForTechnician(dto);
        List<UnConfirmChickenPlanVo> unConfirmChickenPlanVos = new ArrayList<>();
        if (!CollectionUtils.isEmpty(pageInfo.getList())) {
            List<ReturnBreedingPlanDto> breedingPlans = pageInfo.getList();
            for (ReturnBreedingPlanDto plan : breedingPlans) {
                UnConfirmChickenPlanVo planVo = new UnConfirmChickenPlanVo();
                BeanUtils.copyProperties(plan, planVo);
                unConfirmChickenPlanVos.add(planVo);
            }
        }
        pageInfo.setList(unConfirmChickenPlanVos);
        return BaseResponse.successInstance(pageInfo);
    }

}
