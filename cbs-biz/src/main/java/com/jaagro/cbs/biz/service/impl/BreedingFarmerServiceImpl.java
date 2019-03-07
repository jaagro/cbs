package com.jaagro.cbs.biz.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jaagro.cbs.api.dto.base.GetCustomerUserDto;
import com.jaagro.cbs.api.dto.base.ShowCustomerDto;
import com.jaagro.cbs.api.dto.farmer.*;
import com.jaagro.cbs.api.dto.order.PurchaseOrderDto;
import com.jaagro.cbs.api.dto.order.PurchaseOrderListParamDto;
import com.jaagro.cbs.api.dto.order.PurchaseOrderParamDto;
import com.jaagro.cbs.api.dto.order.ReturnFarmerPurchaseOrderDetailsDto;
import com.jaagro.cbs.api.enums.PlanStatusEnum;
import com.jaagro.cbs.api.enums.ProductTypeEnum;
import com.jaagro.cbs.api.enums.PurchaseOrderStatusEnum;
import com.jaagro.cbs.api.enums.TechConsultStatusEnum;
import com.jaagro.cbs.api.model.*;
import com.jaagro.cbs.api.service.BreedingFarmerService;
import com.jaagro.cbs.biz.mapper.*;
import com.jaagro.cbs.biz.service.CustomerClientService;
import com.jaagro.cbs.biz.service.UserClientService;
import com.jaagro.constant.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

/**
 * @description: 农户端app 相关api
 * @author: @Gao.
 * @create: 2019-03-04 10:48
 **/
@Slf4j
@Service
public class BreedingFarmerServiceImpl implements BreedingFarmerService {

    @Autowired
    private CurrentUserService currentUserService;
    @Autowired
    private BreedingPlanMapperExt breedingPlanMapper;
    @Autowired
    private BatchInfoMapperExt batchInfoMapper;
    @Autowired
    private DeviceAlarmLogMapperExt deviceAlarmLogMapper;
    @Autowired
    private PurchaseOrderMapperExt purchaseOrderMapper;
    @Autowired
    private TechConsultRecordMapperExt techConsultRecordMapper;
    @Autowired
    private UserClientService userClientService;
    @Autowired
    private CustomerClientService customerClientService;
    @Autowired
    private TechConsultImagesMapperExt techConsultImagesMapper;
    @Autowired
    private ProductMapperExt productMapper;


    /**
     * 农户端app 首页数据统计
     *
     * @return
     * @author @Gao.
     */
    @Override
    public ReturnBreedingFarmerIndexDto breedingFarmerIndexStatistical() {
        BigDecimal totalPlanStock = BigDecimal.ZERO;
        HashSet<Integer> planIds = new HashSet<>();
        UserInfo currentUser = currentUserService.getCurrentUser();
        ReturnBreedingFarmerIndexDto returnBreedingFarmerIndexDto = new ReturnBreedingFarmerIndexDto();
        if (currentUser != null) {
            List<ReturnBreedingBatchDetailsDto> returnBreedingBatchDetailsDtos = breedingPlanMapper.listBreedingPlanByCustomerId(currentUser.getId());
            if (!CollectionUtils.isEmpty(returnBreedingBatchDetailsDtos)) {
                for (ReturnBreedingBatchDetailsDto returnBreedingBatchDetailsDto : returnBreedingBatchDetailsDtos) {
                    planIds.add(returnBreedingBatchDetailsDto.getId());
                    //累计所有上鸡计划所有数量
                    totalPlanStock = totalPlanStock.add(new BigDecimal(returnBreedingBatchDetailsDto.getPlanChickenQuantity()));
                }
                if (!CollectionUtils.isEmpty(planIds)) {
                    //1.累计所有死淘数量
                    BigDecimal accumulativeTotalDeadAmount = batchInfoMapper.accumulativeTotalDeadAmount(planIds);
                    //2.累计所有出栏数量
                    BigDecimal accumulativeTotalSaleAmount = batchInfoMapper.accumulativeTotalSaleAmount(planIds);
                    //3.累计所有喂养饲料
                    BigDecimal accumulativeTotalFeed = batchInfoMapper.accumulativeTotalFeed(planIds);
                    //当前存栏量
                    BigDecimal totalBreedingStock = totalPlanStock.subtract(accumulativeTotalDeadAmount).subtract(accumulativeTotalSaleAmount);
                    //环控异常指数
                    BigDecimal accumulativeTotalAbnormalWarn = deviceAlarmLogMapper.accumulativeTotalAbnormalWarn(planIds);
                    //饲料库存
                    PurchaseOrderParamDto purchaseOrderParamDto = new PurchaseOrderParamDto();
                    purchaseOrderParamDto
                            .setPlanIds(planIds)
                            .setProductType(ProductTypeEnum.FEED.getCode());
                    BigDecimal planFeedWeight = purchaseOrderMapper.calculateTotalPlanFeedWeight(purchaseOrderParamDto);
                    if (planFeedWeight != null) {
                        BigDecimal totalFeedStock = planFeedWeight.subtract(accumulativeTotalFeed);
                        returnBreedingFarmerIndexDto
                                .setTotalFeedStock(totalFeedStock);
                    }
                    returnBreedingFarmerIndexDto
                            .setTotalBreedingStock(totalBreedingStock)
                            .setTotalAbnormalWarn(accumulativeTotalAbnormalWarn);
                }
            }
        }
        return returnBreedingFarmerIndexDto;
    }

    /**
     * 农户端app 首页
     *
     * @return
     * @author: @Gao.
     */
    @Override
    public PageInfo breedingFarmerIndex(BreedingBatchParamDto dto) {
        PageHelper.startPage(dto.getPageNum(), dto.getPageSize());
        List<ReturnBreedingBatchDetailsDto> returnBreedingBatchDetailsDtos = null;
        UserInfo currentUser = currentUserService.getCurrentUser();
        if (currentUser != null) {
            GetCustomerUserDto customerUser = userClientService.getCustomerUserById(currentUser.getId());
            if (customerUser != null && customerUser.getRelevanceId() != null) {
                returnBreedingBatchDetailsDtos = breedingPlanMapper.listBreedingPlanByCustomerId(customerUser.getRelevanceId());
                if (!CollectionUtils.isEmpty(returnBreedingBatchDetailsDtos)) {
                    for (ReturnBreedingBatchDetailsDto returnBreedingBatchDetailsDto : returnBreedingBatchDetailsDtos) {
                        Integer planId = returnBreedingBatchDetailsDto.getId();
                        Integer dayAge = null;
                        try {
                            //获取当前日龄
                            dayAge = getDayAge(returnBreedingBatchDetailsDto.getPlanTime());
                        } catch (Exception e) {
                            log.info("R breedingFarmerIndex getDayAge error", e);
                        }
                        returnBreedingBatchDetailsDto.setDayAge(dayAge);
                        BatchInfoExample batchInfoExample = new BatchInfoExample();
                        if (dayAge != null) {
                            //今日耗料量
                            batchInfoExample
                                    .createCriteria()
                                    .andPlanIdEqualTo(planId)
                                    .andDayAgeEqualTo(dayAge)
                                    .andEnableEqualTo(true);
                            List<BatchInfo> batchInfos = batchInfoMapper.selectByExample(batchInfoExample);
                            if (!CollectionUtils.isEmpty(batchInfos)) {
                                BatchInfo batchInfo = batchInfos.get(0);
                                if (batchInfo != null && batchInfo.getFodderAmount() != null) {
                                    returnBreedingBatchDetailsDto.setFodderAmount(batchInfo.getFodderAmount());
                                }
                            }
                        }
                        //1.累计所有出栏量
                        BigDecimal saleAmount = batchInfoMapper.accumulativeSaleAmount(planId);
                        //2.累计所有死淘数量
                        BigDecimal deadAmount = batchInfoMapper.accumulativeDeadAmount(planId);
                        //计算存栏量
                        if (returnBreedingBatchDetailsDto.getPlanChickenQuantity() != null) {
                            BigDecimal breedingStock = null;
                            BigDecimal totalBreedingStock = null;
                            Integer planChickenQuantity = returnBreedingBatchDetailsDto.getPlanChickenQuantity();
                            if (saleAmount != null) {
                                breedingStock = new BigDecimal(planChickenQuantity).subtract(saleAmount);
                            }
                            if (breedingStock != null && deadAmount != null) {
                                totalBreedingStock = breedingStock.subtract(deadAmount);
                            }
                            returnBreedingBatchDetailsDto
                                    .setBreedingStock(totalBreedingStock)
                                    .setStrPlanStatus(PlanStatusEnum.getDescByCode(returnBreedingBatchDetailsDto.getPlanStatus()));
                        }
                    }
                }
            }
        }
        return new PageInfo(returnBreedingBatchDetailsDtos);
    }

    /**
     * 新增技术询问
     *
     * @param dto
     * @author: @Gao.
     */
    @Override
    public void technicalInquiries(CreateTechnicalInquiriesDto dto) {
        BatchInfo batchInfo = batchInfoMapper.selectByPrimaryKey(dto.getPlanId());
        if (batchInfo == null) {
            throw new RuntimeException("当前养殖批次不存在");
        }
        //插入技术询问
        TechConsultRecord techConsultRecord = new TechConsultRecord();
        UserInfo currentUser = currentUserService.getCurrentUser();
        BeanUtils.copyProperties(dto, techConsultRecord);
        techConsultRecord
                .setTechConsultStatus(TechConsultStatusEnum.STATUS_PENDING.getCode());
        if (currentUser != null && currentUser.getId() != null) {
            GetCustomerUserDto customerUser = userClientService.getCustomerUserById(currentUser.getId());
            if (customerUser != null && customerUser.getRelevanceId() != null) {
                ShowCustomerDto showCustomer = customerClientService.getShowCustomerById(customerUser.getRelevanceId());
                if (showCustomer != null && showCustomer.getCustomerName() != null) {
                    techConsultRecord
                            .setCustomerName(showCustomer.getCustomerName())
                            .setCreateUserId(currentUser.getId())
                            .setCustomerId(customerUser.getRelevanceId());
                }
            }
        }
        techConsultRecordMapper.insertSelective(techConsultRecord);
        //插入图片
        if (!CollectionUtils.isEmpty(dto.getImageUrl())) {
            List<String> imageUrls = dto.getImageUrl();
            for (String imageUrl : imageUrls) {
                TechConsultImages techConsultImages = new TechConsultImages();
                if (techConsultRecord.getId() != null) {
                    techConsultImages
                            .setImageUrl(imageUrl)
                            .setTechConsultRecordId(techConsultRecord.getId());
                    if (currentUser != null && currentUser.getId() != null) {
                        techConsultImages
                                .setCreateUserId(currentUser.getId());
                    }
                }
                techConsultImagesMapper.insertSelective(techConsultImages);
            }
        }
    }

    /**
     * 农户端个人中心
     *
     * @return
     * @author: @Gao.
     */
    @Override
    public FarmerPersonalCenterDto farmerPersonalCenter() {
        FarmerPersonalCenterDto farmerPersonalCenterDto = new FarmerPersonalCenterDto();
        UserInfo currentUser = currentUserService.getCurrentUser();
        if (currentUser != null && currentUser.getId() != null && currentUser.getLoginName() != null) {
            farmerPersonalCenterDto
                    .setPhoneNumber(currentUser.getPhoneNumber())
                    .setLoginName(currentUser.getLoginName());
            GetCustomerUserDto customerUser = userClientService.getCustomerUserById(currentUser.getId());
            if (customerUser != null && customerUser.getRelevanceId() != null) {
                ShowCustomerDto showCustomer = customerClientService.getShowCustomerById(customerUser.getRelevanceId());
                if (showCustomer != null && showCustomer.getCustomerName() != null) {
                    farmerPersonalCenterDto
                            .setCustomerName(showCustomer.getCustomerName());
                }
            }
        }
        return farmerPersonalCenterDto;
    }

    /**
     * 商品采购列表
     *
     * @return
     * @author: @Gao.
     */
    @Override
    public List<PurchaseOrderDto> listPurchaseOrder(PurchaseOrderListParamDto dto) {
        List<Integer> purchaseOrderStatus = new ArrayList<>();
        if (dto != null && dto.getPurchaseOrderStatus() == null) {
            for (PurchaseOrderStatusEnum type : PurchaseOrderStatusEnum.values()) {
                purchaseOrderStatus.add(type.getCode());
            }
        } else {
            purchaseOrderStatus.add(dto.getPurchaseOrderStatus());
        }
        List<PurchaseOrderDto> purchaseOrderDtos = new ArrayList<>();
        UserInfo currentUser = currentUserService.getCurrentUser();
        if (currentUser != null && currentUser.getId() != null) {
            GetCustomerUserDto customerUser = userClientService.getCustomerUserById(currentUser.getId());
            if (customerUser != null && customerUser.getRelevanceId() != null) {
                PurchaseOrderExample purchaseOrderExample = new PurchaseOrderExample();
                purchaseOrderExample
                        .createCriteria()
                        .andPurchaseOrderStatusIn(purchaseOrderStatus)
                        .andEnableEqualTo(true)
                        .andCustomerIdEqualTo(customerUser.getRelevanceId());
                purchaseOrderExample.setOrderByClause("create_time DESC");
                List<PurchaseOrder> purchaseOrderList = purchaseOrderMapper.selectByExample(purchaseOrderExample);
                for (PurchaseOrder purchaseOrder : purchaseOrderList) {
                    PurchaseOrderDto purchaseOrderDto = new PurchaseOrderDto();
                    BeanUtils.copyProperties(purchaseOrder, purchaseOrderDto);
                    purchaseOrderDtos.add(purchaseOrderDto);
                }
            }
        }
        return purchaseOrderDtos;
    }

    /**
     * 农户端采购订单详情
     *
     * @param purchaseOrderId
     * @return
     * @author @Gao.
     */
    @Override
    public ReturnFarmerPurchaseOrderDetailsDto purchaseOrderDetails(Integer purchaseOrderId) {
        ReturnFarmerPurchaseOrderDetailsDto returnFarmerPurchaseOrderDetailsDto = new ReturnFarmerPurchaseOrderDetailsDto();
        PurchaseOrderExample purchaseOrderExample = new PurchaseOrderExample();
        purchaseOrderExample
                .createCriteria()
                .andIdEqualTo(purchaseOrderId)
                .andEnableEqualTo(true);
        List<PurchaseOrder> purchaseOrderList = purchaseOrderMapper.selectByExample(purchaseOrderExample);
        if (!CollectionUtils.isEmpty(purchaseOrderList)) {
            //采购信息
            PurchaseOrder purchaseOrder = purchaseOrderList.get(0);
            BeanUtils.copyProperties(purchaseOrder, returnFarmerPurchaseOrderDetailsDto);
            ProductExample productExample = new ProductExample();
            productExample
                    .createCriteria()
                    .andIdEqualTo(purchaseOrder.getProductId());
            //商品信息
            List<Product> products = productMapper.selectByExample(productExample);
            if (!CollectionUtils.isEmpty(products)) {
                Product product = products.get(0);
                if (product.getProductName() != null) {
                    returnFarmerPurchaseOrderDetailsDto
                            .setProductName(product.getProductName());
                }
            }
        }
        return null;
    }

    /**
     * 更新采购订单状态
     *
     * @param dto
     */
    @Override
    public void updatePurchaseOrder(PurchaseOrderParamDto dto) {

    }

    /**
     * 根据上鸡计划时间获取当前日龄
     *
     * @param beginDate
     * @return
     * @author: @Gao.
     */
    private Integer getDayAge(Date beginDate) throws Exception {
        Integer day = 0;
        Date endDate = new Date();
        if (beginDate == null && endDate == null) {
            return day;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        beginDate = sdf.parse(sdf.format(beginDate));
        endDate = sdf.parse(sdf.format(endDate));
        day = (int) (endDate.getTime() - beginDate.getTime()) / (24 * 60 * 60 * 1000);
        return day + 1;
    }
}
