package com.jaagro.cbs.biz.mapper;

import javax.annotation.Resource;

import com.jaagro.cbs.api.dto.product.ListDrugCriteria;
import com.jaagro.cbs.api.dto.product.ListProductCriteria;
import com.jaagro.cbs.api.model.Product;
import com.jaagro.cbs.api.model.ProductExample;
import com.jaagro.cbs.biz.mapper.base.BaseMapper;

import java.util.List;


/**
 * ProductMapperExt接口
 *
 * @author :generator
 * @date :2019/2/21
 */
@Resource
public interface ProductMapperExt extends BaseMapper<Product, ProductExample> {

    /**
     * 查询产品列表
     *
     * @param criteria
     * @return
     */
    List<Product> listByCriteria(ListProductCriteria criteria);

    /**
     * 药品列表
     *
     * @param criteria
     * @return
     */
    List<Product> listDrugStockByCriteria(ListDrugCriteria criteria);

}