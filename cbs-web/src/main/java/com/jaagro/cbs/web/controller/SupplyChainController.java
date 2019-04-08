package com.jaagro.cbs.web.controller;

import com.github.pagehelper.PageInfo;
import com.jaagro.cbs.api.dto.product.CreateProductDto;
import com.jaagro.cbs.api.dto.product.ListDrugCriteria;
import com.jaagro.cbs.api.dto.supplychain.PurchaseOrderManageCriteria;
import com.jaagro.cbs.api.enums.CapacityUnitEnum;
import com.jaagro.cbs.api.enums.PackageUnitEnum;
import com.jaagro.cbs.api.enums.ProductTypeEnum;
import com.jaagro.cbs.api.model.Product;
import com.jaagro.cbs.api.model.TenantDrugStock;
import com.jaagro.cbs.api.service.BreedingPurchaseOrderService;
import com.jaagro.cbs.api.service.ProductService;
import com.jaagro.cbs.biz.service.OssSignUrlClientService;
import com.jaagro.cbs.web.vo.product.DrugStockVo;
import com.jaagro.utils.BaseResponse;
import com.jaagro.utils.ResponseStatusCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @description: 供应链管理
 * @author: @Gao.
 * @create: 2019-03-15 14:58
 **/
@RestController
@Slf4j
@Api(description = "供应链管理", produces = MediaType.APPLICATION_JSON_VALUE)
public class SupplyChainController {
    @Autowired
    private ProductService productService;
    @Autowired
    private OssSignUrlClientService ossSignUrlClientService;
    @Autowired
    private BreedingPurchaseOrderService breedingPurchaseOrderService;

    @PostMapping("/addProductToStock")
    @ApiOperation("库存管理商品添加")
    public BaseResponse addProductToStock(@RequestBody CreateProductDto dto) {
        productService.addProductToStock(dto);
        return BaseResponse.successInstance(ResponseStatusCode.OPERATION_SUCCESS);
    }

    @PostMapping("/listDrugStock")
    @ApiOperation("库存管理药品列表")
    public BaseResponse listDrugStock(@RequestBody ListDrugCriteria criteria) {
        if (criteria.getPageNum() == null) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "起始页不能为空");
        }
        if (criteria.getPageSize() == null) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "每页条数不能为空");
        }
        PageInfo pageInfo = productService.listDrugStock(criteria);
        List<DrugStockVo> drugStockVos = new ArrayList<>();
        if (!CollectionUtils.isEmpty(pageInfo.getList())) {
            List<Product> products = pageInfo.getList();
            for (Product product : products) {
                StringBuilder sb = new StringBuilder();
                DrugStockVo drugStockVo = new DrugStockVo();
                BeanUtils.copyProperties(product, drugStockVo);
                if (product.getProductType() != null) {
                    drugStockVo
                            .setStrProductType(ProductTypeEnum.getDescByCode(product.getProductType()));
                }
                if (product.getProductCapacity() != null) {
                    sb.append(product.getProductCapacity());
                }
                if (product.getCapacityUnit() != null) {
                    sb.append(CapacityUnitEnum.getDescByCode(product.getCapacityUnit()));
                }
                if (product.getPackageUnit() != null) {
                    sb.append("/").append(PackageUnitEnum.getDescByCode(product.getPackageUnit()));
                }
                drugStockVo.setSpecification(sb.toString());
                String[] strArray = {product.getImageUrl()};
                List<URL> urls = ossSignUrlClientService.listSignedUrl(strArray);
                if (!CollectionUtils.isEmpty(urls)) {
                    drugStockVo.setImageUrl(urls.get(0).toString());
                }
                if (product.getId() != null) {
                    TenantDrugStock drugStock = productService.getDrugStock(product.getId());
                    if (drugStock != null && drugStock.getStockQuantity() != null) {
                        drugStockVo.setStockQuantity(drugStock.getStockQuantity());
                    }
                }
                drugStockVos.add(drugStockVo);
            }
        }
        pageInfo.setList(drugStockVos);
        return BaseResponse.successInstance(pageInfo);
    }

    @PostMapping("/listPurchasingManagement")
    @ApiOperation("采购管理商品添加")
    public BaseResponse listPurchasingManagement(@RequestBody PurchaseOrderManageCriteria criteria) {
        return BaseResponse.successInstance(ResponseStatusCode.OPERATION_SUCCESS);
    }

    @GetMapping("/deleteDrugStock/{productId}")
    @ApiOperation("药品库存删除")
    public BaseResponse deleteDrugStock(@PathVariable("productId") Integer productId) {
        if (productId == null) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "药品id不能为空");
        }
        productService.deleteDrugStock(productId);
        return BaseResponse.successInstance(ResponseStatusCode.OPERATION_SUCCESS);
    }
}
