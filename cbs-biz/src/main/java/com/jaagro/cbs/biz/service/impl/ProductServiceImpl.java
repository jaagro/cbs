package com.jaagro.cbs.biz.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jaagro.cbs.api.dto.product.CreateProductDto;
import com.jaagro.cbs.api.dto.product.ListDrugCriteria;
import com.jaagro.cbs.api.dto.product.ListProductCriteria;
import com.jaagro.cbs.api.enums.ProductTypeEnum;
import com.jaagro.cbs.api.model.Product;
import com.jaagro.cbs.api.model.ProductExample;
import com.jaagro.cbs.api.model.TenantDrugStock;
import com.jaagro.cbs.api.service.ProductService;
import com.jaagro.cbs.biz.mapper.ProductMapperExt;
import com.jaagro.cbs.biz.mapper.TenantDrugStockMapperExt;
import com.jaagro.cbs.biz.utils.SequenceCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Arrays;
import java.util.List;

/**
 * @author yj
 * @date 2019/2/27 11:31
 */
@Service
@Slf4j
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductMapperExt productMapper;
    @Autowired
    private SequenceCodeUtils sequenceCodeUtils;
    @Autowired
    private TenantDrugStockMapperExt tenantDrugStockMapper;

    /**
     * 根据条件查询产品
     *
     * @param criteria
     * @return
     */
    @Override
    public List<Product> listByCriteria(ListProductCriteria criteria) {
        return productMapper.listByCriteria(criteria);
    }

    /**
     * 库存管理商品添加
     *
     * @param dto
     * @author @Gao.
     */
    @Override
    public void addProductToStock(CreateProductDto dto) {
        Product product = new Product();
        BeanUtils.copyProperties(dto, product);
        product.setSkuNo(sequenceCodeUtils.genSeqCode("SN"));
        productMapper.insertSelective(product);
    }

    /**
     * 药品库存列表
     *
     * @param criteria
     * @return
     * @author @Gao.
     */
    @Override
    public PageInfo listDrugStock(ListDrugCriteria criteria) {
        PageHelper.startPage(criteria.getPageNum(), criteria.getPageSize());
        ProductExample productExample = new ProductExample();
        ProductExample.Criteria condition = productExample.createCriteria();
        if (criteria.getProductName() != null) {
            condition.andProductNameEqualTo(criteria.getProductName());
        }
        if (criteria.getProductType() != null) {
            condition.andProductTypeEqualTo(criteria.getProductType());
        }
        if (criteria.getSkuNo() != null) {
            condition.andSkuNoEqualTo(criteria.getSkuNo());
        }
        List<Integer> drugType = Arrays.asList(ProductTypeEnum.DRUG.getCode());
        condition.andProductTypeIn(drugType);
        productExample.setOrderByClause("create_time desc");
        List<Product> products = productMapper.selectByExample(productExample);
        return new PageInfo(products);
    }

    /**
     * 获取药品库存
     *
     * @param drugStockId
     * @return
     */
    @Override
    public TenantDrugStock getDrugStock(Integer drugStockId) {
        return tenantDrugStockMapper.selectByPrimaryKey(drugStockId);
    }
}
