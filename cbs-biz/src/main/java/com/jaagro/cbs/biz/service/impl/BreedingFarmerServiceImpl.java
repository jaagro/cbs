package com.jaagro.cbs.biz.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jaagro.cbs.api.dto.base.GetCustomerUserDto;
import com.jaagro.cbs.api.dto.base.ShowCustomerDto;
import com.jaagro.cbs.api.dto.farmer.*;
import com.jaagro.cbs.api.dto.order.*;
import com.jaagro.cbs.api.dto.plan.ReturnCustomerInfoDto;
import com.jaagro.cbs.api.enums.*;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;


import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

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
    @Autowired
    private PurchaseOrderItemsMapperExt purchaseOrderItemsMapper;


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
        if (currentUser != null && currentUser.getId() != null) {
            GetCustomerUserDto customerUser = userClientService.getCustomerUserById(currentUser.getId());
            if (customerUser != null && customerUser.getRelevanceId() != null) {
                List<ReturnBreedingBatchDetailsDto> returnBreedingBatchDetailsDtos = breedingPlanMapper.listAllBreedingPlanByCustomerId(customerUser.getRelevanceId());
                if (!CollectionUtils.isEmpty(returnBreedingBatchDetailsDtos)) {
                    for (ReturnBreedingBatchDetailsDto returnBreedingBatchDetailsDto : returnBreedingBatchDetailsDtos) {
                        planIds.add(returnBreedingBatchDetailsDto.getId());
                        //累计所有上鸡计划所有数量
                        if (returnBreedingBatchDetailsDto.getPlanChickenQuantity() != null) {
                            totalPlanStock = totalPlanStock.add(new BigDecimal(returnBreedingBatchDetailsDto.getPlanChickenQuantity()));
                        }
                    }
                    if (!CollectionUtils.isEmpty(planIds)) {
                        //1.累计所有死淘数量
                        BigDecimal accumulativeTotalDeadAmount = batchInfoMapper.accumulativeTotalDeadAmount(planIds);
                        //2.累计所有出栏数量
                        BigDecimal accumulativeTotalSaleAmount = batchInfoMapper.accumulativeTotalSaleAmount(planIds);
                        //3.累计所有喂养饲料
                        BigDecimal accumulativeTotalFeed = batchInfoMapper.accumulativeTotalFeed(planIds);
                        //当前存栏量
                        BigDecimal totalBreedingStock;
                        if (accumulativeTotalDeadAmount != null) {
                            totalBreedingStock = totalPlanStock.subtract(accumulativeTotalDeadAmount);
                        } else {
                            totalBreedingStock = totalPlanStock;
                        }
                        if (totalBreedingStock != null && accumulativeTotalSaleAmount != null) {
                            totalBreedingStock.subtract(accumulativeTotalSaleAmount);
                        }
                        //环控异常指数
                        BigDecimal accumulativeTotalAbnormalWarn = deviceAlarmLogMapper.accumulativeTotalAbnormalWarn(planIds);
                        //饲料库存
                        PurchaseOrderItemsParamDto purchaseOrderItemsParamDto = new PurchaseOrderItemsParamDto();
                        purchaseOrderItemsParamDto
                                .setPlanIds(planIds)
                                .setProductType(ProductTypeEnum.FEED.getCode());
                        BigDecimal planFeedWeight = purchaseOrderItemsMapper.calculateTotalPlanFeedWeight(purchaseOrderItemsParamDto);
                        if (planFeedWeight != null && accumulativeTotalFeed != null) {
                            BigDecimal totalFeedStock = planFeedWeight.subtract(accumulativeTotalFeed);
                            if (totalFeedStock != null && BigDecimal.ZERO.compareTo(totalFeedStock) != -1) {
                                returnBreedingFarmerIndexDto.setTotalFeedStock(BigDecimal.ZERO);
                            } else {
                                returnBreedingFarmerIndexDto
                                        .setTotalFeedStock(totalFeedStock);
                            }
                        }
                        returnBreedingFarmerIndexDto
                                .setTotalBreedingStock(totalBreedingStock)
                                .setTotalAbnormalWarn(accumulativeTotalAbnormalWarn);
                    }
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
        if (currentUser != null && currentUser.getId() != null) {
            GetCustomerUserDto customerUser = userClientService.getCustomerUserById(currentUser.getId());
            if (customerUser != null && customerUser.getRelevanceId() != null) {
                returnBreedingBatchDetailsDtos = breedingPlanMapper.listBreedingPlanByCustomerId(customerUser.getRelevanceId());
                if (!CollectionUtils.isEmpty(returnBreedingBatchDetailsDtos)) {
                    for (ReturnBreedingBatchDetailsDto returnBreedingBatchDetailsDto : returnBreedingBatchDetailsDtos) {
                        Integer planId = returnBreedingBatchDetailsDto.getId();
                        int dayAge = 0;
                        try {
                            //获取当前日龄
                            if (returnBreedingBatchDetailsDto.getPlanTime() != null) {
                                dayAge = getDayAge(returnBreedingBatchDetailsDto.getPlanTime());
                            }
                        } catch (Exception e) {
                            log.info("R breedingFarmerIndex getDayAge error", e);
                        }
                        dayAge = dayAge < 0 ? 0 : dayAge;
                        returnBreedingBatchDetailsDto
                                .setDayAge(dayAge);
                        BatchInfoExample batchInfoExample = new BatchInfoExample();
                        if (dayAge > 0) {
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
                            BigDecimal breedingStock;
                            Integer planChickenQuantity = returnBreedingBatchDetailsDto.getPlanChickenQuantity();
                            if (saleAmount != null) {
                                breedingStock = new BigDecimal(planChickenQuantity).subtract(saleAmount);
                            } else {
                                breedingStock = new BigDecimal(planChickenQuantity);
                            }
                            if (breedingStock != null && deadAmount != null) {
                                breedingStock = breedingStock.subtract(deadAmount);
                            }
                            returnBreedingBatchDetailsDto
                                    .setBreedingStock(breedingStock);
                            if (returnBreedingBatchDetailsDto.getPlanStatus() != null) {
                                returnBreedingBatchDetailsDto
                                        .setStrPlanStatus(PlanStatusEnum.getDescByCode(returnBreedingBatchDetailsDto.getPlanStatus()));
                            }
                        }
                    }
                }
            }
        }
        return new PageInfo(returnBreedingBatchDetailsDtos);
    }

    /**
     * 农户端技术询问
     *
     * @param dto
     * @author: @Gao.
     */
    @Override
    public void technicalInquiries(CreateTechnicalInquiriesDto dto) {
        BreedingPlan breedingPlan = breedingPlanMapper.selectByPrimaryKey(dto.getPlanId());
        if (breedingPlan == null) {
            throw new RuntimeException("当前养殖批次不存在");
        }
        //插入技术询问
        TechConsultRecord techConsultRecord = new TechConsultRecord();
        try {
            //获取当前日龄
            if (breedingPlan.getPlanTime() != null) {
                Integer dayAge = getDayAge(breedingPlan.getPlanTime());
                techConsultRecord.setDayAge(dayAge);
            }
        } catch (Exception e) {
            log.info("R breedingFarmerIndex getDayAge error", e);
        }
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
                            .setBatchNo(breedingPlan.getBatchNo())
                            .setCustomerPhoneNumber(currentUser.getPhoneNumber())
                            .setCustomerName(showCustomer.getCustomerName())
                            .setCreateUserId(currentUser.getId())
                            .setCustomerId(customerUser.getRelevanceId());
                    if (breedingPlan.getTechnicianId() != null) {
                        techConsultRecord
                                .setTechnicianId(breedingPlan.getTechnicianId());
                    }
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
                    .setLoginName(currentUser.getName());
            GetCustomerUserDto customerUser = userClientService.getCustomerUserById(currentUser.getId());
            if (customerUser != null && customerUser.getRelevanceId() != null) {
                farmerPersonalCenterDto
                        .setCustomerId(customerUser.getRelevanceId());
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
    public PageInfo listPurchaseOrder(PurchaseOrderListParamDto dto) {
        List<PurchaseOrderDto> purchaseOrderDtos = new ArrayList<>();
        PageHelper.startPage(dto.getPageNum(), dto.getPageSize());
        List<Integer> purchaseOrderStatus = new ArrayList<>();
        if (dto.getPurchaseOrderStatus() == null) {
            for (PurchaseOrderStatusEnum type : PurchaseOrderStatusEnum.values()) {
                if (PurchaseOrderStatusEnum.ORDER_PLACED.getCode() == type.getCode()) {
                    continue;
                }
                purchaseOrderStatus.add(type.getCode());
            }
        } else {
            purchaseOrderStatus.add(dto.getPurchaseOrderStatus());
        }
        UserInfo currentUser = currentUserService.getCurrentUser();
        if (currentUser != null && currentUser.getId() != null) {
            GetCustomerUserDto customerUser = userClientService.getCustomerUserById(currentUser.getId());
            if (customerUser != null && customerUser.getRelevanceId() != null) {
                PurchaseOrderCriteriaDto purchaseOrderCriteriaDto = new PurchaseOrderCriteriaDto();
                purchaseOrderCriteriaDto
                        .setCustomerId(customerUser.getRelevanceId())
                        .setPurchaseOrderStatus(purchaseOrderStatus);
                purchaseOrderDtos = purchaseOrderMapper.listFarmerPurchaseOrder(purchaseOrderCriteriaDto);
                for (PurchaseOrderDto purchaseOrderDto : purchaseOrderDtos) {
                    BigDecimal totalPurchaseStatistics = new BigDecimal(0);
                    if (ProductTypeEnum.SPROUT.getCode() == purchaseOrderDto.getProductType()) {
                        purchaseOrderDto.setStrOrderPhase("全部鸡苗配送");
                    }
                    if (ProductTypeEnum.FEED.getCode() == purchaseOrderDto.getProductType()) {
                        purchaseOrderDto.setStrOrderPhase("第" + PurchaseOrderPhaseEnum.getDescByCode(purchaseOrderDto.getOrderPhase()) + "批饲料配送");
                    }

                    if (ProductTypeEnum.DRUG.getCode() == purchaseOrderDto.getProductType()) {
                        purchaseOrderDto.setStrOrderPhase("第" + PurchaseOrderPhaseEnum.getDescByCode(purchaseOrderDto.getOrderPhase()) + "次药品配送");
                    }
                    if (purchaseOrderDto.getPurchaseOrderStatus() != null) {
                        purchaseOrderDto
                                .setStrPurchaseOrderStatus(PurchaseOrderStatusEnum.getDescByCode(purchaseOrderDto.getPurchaseOrderStatus()));
                    }
                    if (purchaseOrderDto.getId() != null) {
                        PurchaseOrderItemsExample purchaseOrderItemsExample = new PurchaseOrderItemsExample();
                        purchaseOrderItemsExample
                                .createCriteria()
                                .andEnableEqualTo(true)
                                .andPurchaseOrderIdEqualTo(purchaseOrderDto.getId());
                        List<PurchaseOrderItems> purchaseOrderItems = purchaseOrderItemsMapper.selectByExample(purchaseOrderItemsExample);
                        if (!CollectionUtils.isEmpty(purchaseOrderItems)) {
                            PurchaseOrderItems purchase = purchaseOrderItems.get(0);
                            for (PurchaseOrderItems purchaseOrderItem : purchaseOrderItems) {
                                if (purchaseOrderItem.getQuantity() != null) {
                                    totalPurchaseStatistics = totalPurchaseStatistics.add(purchaseOrderItem.getQuantity());
                                }
                            }
                            if (purchase.getUnit() != null) {
                                purchaseOrderDto.setStrUnit(PackageUnitEnum.getDescByCode(purchase.getUnit()));
                            }
                        }
                        purchaseOrderDto.setQuantity(totalPurchaseStatistics);
                    }
                    purchaseOrderDto.setStrProductType(ProductTypeEnum.getDescByCode(purchaseOrderDto.getProductType()) + "任务");
                }
            }
        }
        return new PageInfo(purchaseOrderDtos);
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
            returnFarmerPurchaseOrderDetailsDto
                    .setPurchaseOrderStatus(PurchaseOrderStatusEnum.getDescByCode(purchaseOrder.getPurchaseOrderStatus()));
            if (ProductTypeEnum.SPROUT.getCode() == purchaseOrder.getProductType()) {
                returnFarmerPurchaseOrderDetailsDto.setOrderPhase("全部鸡苗配送");
            }
            if (ProductTypeEnum.FEED.getCode() == purchaseOrder.getProductType()) {
                returnFarmerPurchaseOrderDetailsDto.setOrderPhase("第" + PurchaseOrderPhaseEnum.getDescByCode(purchaseOrder.getOrderPhase()) + "批饲料配送");
            }
            if (ProductTypeEnum.DRUG.getCode() == purchaseOrder.getProductType()) {
                returnFarmerPurchaseOrderDetailsDto.setOrderPhase("第" + PurchaseOrderPhaseEnum.getDescByCode(purchaseOrder.getOrderPhase()) + "次药品配送");
            }
            if (purchaseOrder.getId() != null) {
                PurchaseOrderItemsExample purchaseOrderItemsExample = new PurchaseOrderItemsExample();
                purchaseOrderItemsExample
                        .createCriteria()
                        .andEnableEqualTo(true)
                        .andPurchaseOrderIdEqualTo(purchaseOrder.getId());
                List<PurchaseOrderItems> purchaseOrderItems = purchaseOrderItemsMapper.selectByExample(purchaseOrderItemsExample);
                List<ReturnProductDto> returnProductDtos = new ArrayList<>();
                if (!CollectionUtils.isEmpty(purchaseOrderItems)) {
                    for (PurchaseOrderItems purchaseOrderItem : purchaseOrderItems) {
                        ReturnProductDto returnProductDto = new ReturnProductDto();
                        if (purchaseOrderItem.getProductId() != null) {
                            ProductExample productExample = new ProductExample();
                            productExample
                                    .createCriteria()
                                    .andIdEqualTo(purchaseOrderItem.getProductId())
                                    .andEnableEqualTo(true);
                            List<Product> products = productMapper.selectByExample(productExample);
                            if (!CollectionUtils.isEmpty(products)) {
                                StringBuilder sb = new StringBuilder();
                                Product product = products.get(0);
                                if (product.getProductName() != null) {
                                    returnProductDto
                                            .setProductName(product.getProductName());
                                }
                                if (ProductTypeEnum.DRUG.getCode() == product.getProductType()
                                        || ProductTypeEnum.SPROUT.getCode() == product.getProductType()) {
                                    if (product.getProductCapacity() != null) {
                                        sb.append(product.getProductCapacity());
                                    }
                                    if (product.getCapacityUnit() != null) {
                                        sb.append(CapacityUnitEnum.getDescByCode(product.getCapacityUnit()));
                                    }
                                    if (product.getPackageUnit() != null) {
                                        sb.append("/").append(PackageUnitEnum.getDescByCode(product.getPackageUnit()));
                                    }
                                    returnProductDto.setSpecification(sb.toString());
                                } else {
                                    returnProductDto.setSpecification("");
                                }
                            }
                        }
                        if (purchaseOrderItem.getUnit() != null) {
                            returnProductDto
                                    .setUnit(PackageUnitEnum.getDescByCode(purchaseOrderItem.getUnit()));
                        }
                        if (purchaseOrderItem.getQuantity() != null) {
                            returnProductDto.setQuantity(purchaseOrderItem.getQuantity());

                        }
                        returnProductDtos.add(returnProductDto);
                    }
                    returnFarmerPurchaseOrderDetailsDto.setReturnProductDtos(returnProductDtos);
                }
            }
        }
        return returnFarmerPurchaseOrderDetailsDto;
    }

    /**
     * 农户端上鸡计划列表
     *
     * @param dto
     * @return
     * @author: @Gao.
     */
    @Override
    public PageInfo listPublishedChickenPlan(BreedingBatchParamDto dto) {
        List<BreedingPlan> breedingPlans = null;
        PageHelper.startPage(dto.getPageNum(), dto.getPageSize());
        UserInfo currentUser = currentUserService.getCurrentUser();
        BreedingPlanExample breedingPlanExample = new BreedingPlanExample();
        if (currentUser != null && currentUser.getId() != null) {
            breedingPlanExample
                    .createCriteria()
                    .andCreateUserIdEqualTo(currentUser.getId())
                    .andEnableEqualTo(true);
            breedingPlanExample.setOrderByClause("plan_time desc");
            breedingPlans = breedingPlanMapper.selectByExample(breedingPlanExample);
        }
        return new PageInfo(breedingPlans);
    }

    /**
     * 更新采购订单状态
     *
     * @param dto
     * @author @Gao.
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePurchaseOrder(UpdatePurchaseOrderParamDto dto) {
        final int countVal = 3;
        UserInfo currentUser = currentUserService.getCurrentUser();
        PurchaseOrderExample purchaseOrderExample = new PurchaseOrderExample();
        PurchaseOrderExample.Criteria criteria = purchaseOrderExample.createCriteria();
        criteria.andEnableEqualTo(true)
                .andIdEqualTo(dto.getPurchaseOrderId());
        List<PurchaseOrder> purchaseOrderList = purchaseOrderMapper.selectByExample(purchaseOrderExample);
        if (CollectionUtils.isEmpty(purchaseOrderList)) {
            throw new RuntimeException("当前采购订单不存在");
        }
        //更新采购单状态
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder
                .setId(dto.getPurchaseOrderId());
        if (dto.getPurchaseOrderStatus() != null) {
            if (PurchaseOrderStatusEnum.PENDING_ARRIVED.getCode() == dto.getPurchaseOrderStatus()) {
                purchaseOrder
                        .setDeliveryTime(new Date());
            }
            if (PurchaseOrderStatusEnum.ALREADY_SIGNED.getCode() == dto.getPurchaseOrderStatus()) {
                purchaseOrder.setSignerTime(new Date());
                if (currentUser != null && currentUser.getId() != null) {
                    purchaseOrder.setSignerId(currentUser.getId());
                }
            }
            purchaseOrder
                    .setPurchaseOrderStatus(dto.getPurchaseOrderStatus());
            purchaseOrderMapper.updateByPrimaryKeySelective(purchaseOrder);
        }
        if (PurchaseOrderStatusEnum.ALREADY_SIGNED.getCode() == dto.getPurchaseOrderStatus()) {
            purchaseOrder = purchaseOrderList.get(0);
            //当前第一批采购订单全部签收 更改养殖计划状态为养殖中
            if (purchaseOrder.getPlanId() != null) {
                BreedingPlan breedingPlan = breedingPlanMapper.selectByPrimaryKey(purchaseOrder.getPlanId());
                if (breedingPlan != null && PlanStatusEnum.SIGN_CHICKEN.getCode() == breedingPlan.getPlanStatus()) {
                    PurchaseOrderExample example = new PurchaseOrderExample();
                    int count = 0;
                    example
                            .createCriteria()
                            .andEnableEqualTo(true)
                            .andOrderPhaseEqualTo(PurchaseOrderPhaseEnum.PHASE_ONE.getCode())
                            .andPlanIdEqualTo(purchaseOrder.getPlanId());
                    purchaseOrderList = purchaseOrderMapper.selectByExample(example);
                    for (PurchaseOrder order : purchaseOrderList) {
                        if (PurchaseOrderStatusEnum.ALREADY_SIGNED.getCode() == order.getPurchaseOrderStatus()) {
                            count++;
                        }
                    }
                    if (count == purchaseOrderList.size() && count == countVal) {
                        breedingPlan
                                .setPlanStatus(PlanStatusEnum.BREEDING.getCode())
                                .setId(purchaseOrder.getPlanId());
                        breedingPlanMapper.updateByPrimaryKeySelective(breedingPlan);
                    }
                }
            }
        }
    }

    /**
     * 技术询问列表
     *
     * @param dto
     * @return
     */
    @Override
    public PageInfo listTechnicalInquiries(BreedingBatchParamDto dto) {
        List<TechConsultRecord> techConsultRecords = new ArrayList<>();
        PageHelper.startPage(dto.getPageNum(), dto.getPageSize());
        UserInfo currentUser = currentUserService.getCurrentUser();
        TechConsultRecordExample techConsultRecordExample = new TechConsultRecordExample();
        if (currentUser != null && currentUser.getId() != null) {
            techConsultRecordExample
                    .createCriteria()
                    .andEnableEqualTo(true)
                    .andCreateUserIdEqualTo(currentUser.getId());
            techConsultRecordExample
                    .setOrderByClause("create_time desc");
            techConsultRecords = techConsultRecordMapper.selectByExample(techConsultRecordExample);
        }
        return new PageInfo(techConsultRecords);
    }

    /**
     * 获取客户名称与id
     *
     * @return
     * @author: @Gao.
     */
    @Override
    public ReturnCustomerInfoDto getCustomerInfo() {
        ReturnCustomerInfoDto customerInfoDto = new ReturnCustomerInfoDto();
        UserInfo currentUser = currentUserService.getCurrentUser();
        if (currentUser != null && currentUser.getId() != null) {
            GetCustomerUserDto customerUser = userClientService.getCustomerUserById(currentUser.getId());
            if (customerUser != null && customerUser.getRelevanceId() != null) {
                customerInfoDto
                        .setCustomerId(customerUser.getRelevanceId());
                ShowCustomerDto showCustomer = customerClientService.getShowCustomerById(customerUser.getRelevanceId());
                boolean flag = showCustomer != null && showCustomer.getCustomerName() != null;
                if (flag) {
                    customerInfoDto
                            .setCustomerName(showCustomer.getCustomerName());
                }
            }
        }
        return customerInfoDto;
    }

    /**
     * 根据上鸡计划时间获取当前日龄
     *
     * @param beginDate
     * @return
     * @author: @Gao.
     */
    @Override
    public int getDayAge(Date beginDate) throws Exception {
        int day = 0;
        Date endDate = new Date();
        if (beginDate == null) {
            return day;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        beginDate = sdf.parse(sdf.format(beginDate));
        endDate = sdf.parse(sdf.format(endDate));
        Calendar cal = Calendar.getInstance();
        cal.setTime(beginDate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(endDate);
        long time2 = cal.getTimeInMillis();
        return Integer.parseInt(String.valueOf((time2 - time1) / (1000 * 3600 * 24))) + 1;
    }
}
