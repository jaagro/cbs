package com.jaagro.cbs.api.service;

import com.github.pagehelper.PageInfo;
import com.jaagro.cbs.api.dto.product.CreateProductDto;
import com.jaagro.cbs.api.dto.product.ListDrugCriteria;
import com.jaagro.cbs.api.dto.product.ListProductCriteria;
import com.jaagro.cbs.api.model.Product;
import com.jaagro.cbs.api.model.TenantDrugStock;

import java.util.List;

/**
 * 产品管理
 *
 * @author yj
 * @date 2019/2/27 11:11
 */
public interface ProductService {
    /**
     * 根据条件查询产品
     *
     * @param criteria
     * @return
     */
    List<Product> listByCriteria(ListProductCriteria criteria);

    /**
     * 库存管理商品添加
     *
     * @param dto
     */
    void addProductToStock(CreateProductDto dto);

    /**
     * 商品库存列表
     *
     * @param criteria
     * @return
     */
    PageInfo listDrugStock(ListDrugCriteria criteria);

    /**
     * 获取药品库存
     *
     * @param drugStockId
     * @return
     */
    TenantDrugStock getDrugStock(Integer drugStockId);

    /**
     *
     * @param productId
     */
    void deleteDrugStock(Integer productId);

}
