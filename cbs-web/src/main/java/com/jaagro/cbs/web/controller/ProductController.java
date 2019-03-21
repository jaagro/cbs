package com.jaagro.cbs.web.controller;


import com.jaagro.cbs.api.dto.product.ListProductCriteria;

import com.jaagro.cbs.api.service.ProductService;
import com.jaagro.utils.BaseResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * 产品管理
 *
 * @author yj
 * @date 2019/2/27 11:09
 */
@RestController
@Slf4j
@Api(description = "产品管理", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductController {
    @Autowired
    private ProductService productService;


    @ApiOperation("获取产品列表")
    @PostMapping("/listProductByCriteria")
    public BaseResponse listProductByCriteria(@RequestBody @Validated ListProductCriteria criteria) {
        return BaseResponse.successInstance(productService.listByCriteria(criteria));
    }
}
