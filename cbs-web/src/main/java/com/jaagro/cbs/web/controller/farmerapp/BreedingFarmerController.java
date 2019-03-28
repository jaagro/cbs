package com.jaagro.cbs.web.controller.farmerapp;

import com.github.pagehelper.PageInfo;
import com.jaagro.cbs.api.dto.base.GetCustomerUserDto;
import com.jaagro.cbs.api.dto.base.ShowCustomerDto;
import com.jaagro.cbs.api.dto.farmer.*;
import com.jaagro.cbs.api.dto.message.MessageCriteriaDto;
import com.jaagro.cbs.api.dto.order.PurchaseOrderListParamDto;
import com.jaagro.cbs.api.dto.order.UpdatePurchaseOrderParamDto;
import com.jaagro.cbs.api.dto.plan.CreateBreedingPlanDto;
import com.jaagro.cbs.api.dto.progress.BreedingRecordDto;
import com.jaagro.cbs.api.enums.EmergencyLevelEnum;
import com.jaagro.cbs.api.enums.UserTypeEnum;
import com.jaagro.cbs.api.model.BreedingPlan;
import com.jaagro.cbs.api.model.Message;
import com.jaagro.cbs.api.model.TechConsultRecord;
import com.jaagro.cbs.api.service.BreedingFarmerService;
import com.jaagro.cbs.api.service.BreedingPlanService;
import com.jaagro.cbs.api.service.BreedingPlantService;
import com.jaagro.cbs.api.service.BreedingProgressService;
import com.jaagro.cbs.biz.service.CustomerClientService;
import com.jaagro.cbs.biz.service.UserClientService;
import com.jaagro.cbs.biz.service.impl.CurrentUserService;
import com.jaagro.cbs.biz.service.impl.MessageServiceImpl;
import com.jaagro.cbs.web.vo.message.FarmerMessageVo;
import com.jaagro.cbs.web.vo.plan.PublishedChickenPlanVo;
import com.jaagro.cbs.web.vo.progress.BreedingProgressParamVo;
import com.jaagro.cbs.web.vo.techconsult.TechnicalInquiriesVo;
import com.jaagro.constant.UserInfo;
import com.jaagro.utils.BaseResponse;
import com.jaagro.utils.ResponseStatusCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author @Gao.
 */
@RestController
@Api(description = "农户端养殖管理", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class BreedingFarmerController {
    @Autowired
    private BreedingFarmerService breedingFarmerService;
    @Autowired
    private BreedingPlanService breedingPlanService;
    @Autowired
    private UserClientService userClientService;
    @Autowired
    private CurrentUserService currentUserService;
    @Autowired
    private BreedingPlantService breedingPlantService;
    @Autowired
    private MessageServiceImpl messageService;
    @Autowired
    private BreedingProgressService breedingProgressService;
    @Autowired
    private CustomerClientService customerClientService;


    @GetMapping("/breedingFarmerIndexStatistical")
    @ApiOperation("农户端首页数据统计")
    public BaseResponse breedingFarmerIndexStatistical() {
        return BaseResponse.successInstance(breedingFarmerService.breedingFarmerIndexStatistical());
    }

    @PostMapping("/breedingFarmerIndex")
    @ApiOperation("农户端首页列表")
    public BaseResponse breedingFarmerIndex(@RequestBody BreedingBatchParamDto dto) {
        if (dto.getPageNum() == null) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "起始页不能为空");
        }
        if (dto.getPageSize() == null) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "每页条数不能为空");
        }
        return BaseResponse.successInstance(breedingFarmerService.breedingFarmerIndex(dto));
    }

    @PostMapping("/publishedChickenPlan")
    @ApiOperation("发布上鸡计划")
    public BaseResponse publishedChickenPlan(@RequestBody CreateBreedingPlanDto dto) {
        if (CollectionUtils.isEmpty(dto.getPlantIds())) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "养殖场不能为空");
        }
        if (dto.getCustomerId() == null) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "客户不能为空");
        }
        breedingPlanService.createBreedingPlan(dto);
        return BaseResponse.successInstance(ResponseStatusCode.OPERATION_SUCCESS);
    }

    @PostMapping("/technicalInquiries")
    @ApiOperation("新增技术询问")
    public BaseResponse technicalInquiries(@RequestBody CreateTechnicalInquiriesDto dto) {
        if (dto.getPlanId() == null) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "养殖计划id不能为空");
        }
        if (dto.getPlantId() == null) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "养殖场id不能为空");
        }
        if (dto.getCoopId() == null) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "鸡舍id不能为空");
        }
        breedingFarmerService.technicalInquiries(dto);
        return BaseResponse.successInstance(ResponseStatusCode.OPERATION_SUCCESS);
    }

    @GetMapping("/farmerPersonalCenter")
    @ApiOperation("农户端个人中心")
    public BaseResponse farmerPersonalCenter() {
        return BaseResponse.successInstance(breedingFarmerService.farmerPersonalCenter());
    }

    @PostMapping("/listPurchaseOrder")
    @ApiOperation("商品采购列表")
    public BaseResponse listPurchaseOrder(@RequestBody PurchaseOrderListParamDto dto) {
        return BaseResponse.successInstance(breedingFarmerService.listPurchaseOrder(dto));
    }

    @GetMapping("/purchaseOrderDetails/{purchaseOrderId}")
    @ApiOperation("采购订单详情")
    public BaseResponse purchaseOrderDetails(@PathVariable("purchaseOrderId") Integer purchaseOrderId) {
        if (purchaseOrderId == null) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "采购订单id不能为空");
        }
        return BaseResponse.successInstance(breedingFarmerService.purchaseOrderDetails(purchaseOrderId));
    }

    @PostMapping("/updatePurchaseOrder")
    @ApiOperation("更新采购订单状态")
    public BaseResponse updatePurchaseOrder(@RequestBody UpdatePurchaseOrderParamDto dto) {
        if (dto.getPurchaseOrderId() == null) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "采购订单id详情");
        }
        if (dto.getPurchaseOrderStatus() == null) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "采购订单状态不能为空");
        }
        breedingFarmerService.updatePurchaseOrder(dto);
        return BaseResponse.successInstance(ResponseStatusCode.OPERATION_SUCCESS);
    }

    @PostMapping("/listPublishedChickenPlan")
    @ApiOperation("上鸡计划列表")
    public BaseResponse listPublishedChickenPlan(@RequestBody BreedingBatchParamDto dto) {
        if (dto.getPageNum() == null) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "起始页不能为空");
        }
        if (dto.getPageSize() == null) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "每页条数不能为空");
        }
        PageInfo pageInfo = breedingFarmerService.listPublishedChickenPlan(dto);
        List<PublishedChickenPlanVo> publishedChickenPlanVos = new ArrayList<>();
        if (!CollectionUtils.isEmpty(pageInfo.getList())) {
            List<BreedingPlan> breedingPlans = pageInfo.getList();
            for (BreedingPlan breedingPlan : breedingPlans) {
                PublishedChickenPlanVo publishedChickenPlanVo = new PublishedChickenPlanVo();
                BeanUtils.copyProperties(breedingPlan, publishedChickenPlanVo);
                publishedChickenPlanVos.add(publishedChickenPlanVo);
            }
        }
        pageInfo.setList(publishedChickenPlanVos);
        return BaseResponse.successInstance(pageInfo);
    }

    @PostMapping("/listTechnicalInquiries")
    @ApiOperation("技术询问列表")
    public BaseResponse listTechnicalInquiries(@RequestBody BreedingBatchParamDto dto) {
        if (dto.getPageNum() == null) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "起始页不能为空");
        }
        if (dto.getPageSize() == null) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "每页条数不能为空");
        }
        PageInfo pageInfo = breedingFarmerService.listTechnicalInquiries(dto);
        List<TechnicalInquiriesVo> technicalInquiriesVos = new ArrayList<>();
        if (!CollectionUtils.isEmpty(pageInfo.getList())) {
            List<TechConsultRecord> techConsultRecords = pageInfo.getList();
            for (TechConsultRecord techConsultRecord : techConsultRecords) {
                StringBuilder sb = new StringBuilder();
                TechnicalInquiriesVo technicalInquiriesVo = new TechnicalInquiriesVo();
                BeanUtils.copyProperties(techConsultRecord, technicalInquiriesVo);
                if (techConsultRecord.getHandleUserId() != null) {
                    BaseResponse<UserInfo> globalUser = userClientService.getGlobalUser(techConsultRecord.getHandleUserId());
                    if (globalUser != null && globalUser.getData() != null) {
                        UserInfo userInfo = globalUser.getData();
                        if (userInfo.getPhoneNumber() != null) {
                            technicalInquiriesVo
                                    .setHandlePhone(userInfo.getPhoneNumber());
                        }
                        if (userInfo.getName() != null) {
                            technicalInquiriesVo
                                    .setHandleUser(userInfo.getName());
                        }
                    }
                }
                if (techConsultRecord.getCustomerId() != null) {
                    ShowCustomerDto showCustomer = customerClientService.getShowCustomerById(techConsultRecord.getCustomerId());
                    if (showCustomer != null && showCustomer.getProvince() != null) {
                        sb.append(showCustomer.getProvince());
                    }
                    if (showCustomer != null && showCustomer.getCity() != null) {
                        sb.append(showCustomer.getCity());
                    }
                    if (showCustomer != null && showCustomer.getCounty() != null) {
                        sb.append(showCustomer.getCounty());
                    }
                }
                if (techConsultRecord.getBatchNo() != null) {
                    technicalInquiriesVo
                            .setBatchNo(techConsultRecord.getBatchNo());
                }
                technicalInquiriesVo
                        .setAddress(sb.toString());
                technicalInquiriesVo
                        .setEmergencyLevel(EmergencyLevelEnum.getDescByCode(techConsultRecord.getEmergencyLevel()));
                technicalInquiriesVos.add(technicalInquiriesVo);
            }
        }
        pageInfo.setList(technicalInquiriesVos);
        return BaseResponse.successInstance(pageInfo);
    }

    @PostMapping("/listFarmerMessage")
    @ApiOperation("农户端消息列表")
    public BaseResponse listFarmerMessage(@RequestBody BreedingBatchParamDto dto) {
        if (dto.getPageNum() == null) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "起始页不能为空");
        }
        if (dto.getPageSize() == null) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "每页条数不能为空");
        }
        List<FarmerMessageVo> farmerMessageVos = new ArrayList<>();
        UserInfo currentUser = currentUserService.getCurrentUser();
        MessageCriteriaDto messageCriteriaDto = new MessageCriteriaDto();
        if (currentUser != null && currentUser.getId() != null) {
            GetCustomerUserDto customerUser = userClientService.getCustomerUserById(currentUser.getId());
            if (customerUser != null && customerUser.getRelevanceId() != null) {
                messageCriteriaDto
                        .setPageNum(dto.getPageNum())
                        .setPageSize(dto.getPageSize())
                        .setToUserType(UserTypeEnum.CUSTOMER.getCode())
                        .setToUserId(customerUser.getRelevanceId());
            }
        }
        PageInfo pageInfo = messageService.listMessageByCriteria(messageCriteriaDto);
        if (!CollectionUtils.isEmpty(pageInfo.getList())) {
            List<Message> messages = pageInfo.getList();
            for (Message message : messages) {
                FarmerMessageVo farmerMessageVo = new FarmerMessageVo();
                BeanUtils.copyProperties(message, farmerMessageVo);
                farmerMessageVos.add(farmerMessageVo);
            }
        }
        pageInfo.setList(farmerMessageVos);
        return BaseResponse.successInstance(pageInfo);
    }

    /**
     * 养殖页上鸡计划列表
     *
     * @param dto
     * @return
     * @author yj
     */
    @PostMapping("/listBreedingBatchForFarmer")
    @ApiOperation("养殖页上鸡计划列表")
    public BaseResponse listBreedingBatchForFarmer(@RequestBody @Validated BreedingBatchParamDto dto) {
        log.info("O listBreedingBatchForFarmer params={}", dto);
        return BaseResponse.successInstance(breedingPlanService.listBreedingBatchForFarmer(dto));
    }

    /**
     * 查询鸡舍养殖过程批次参数
     *
     * @param paramVo
     * @return
     * @author yj
     */
    @PostMapping("/getBreedingBatchParamForApp")
    @ApiOperation("查询鸡舍养殖过程批次参数")
    public BaseResponse getBreedingBatchParamForApp(@RequestBody BreedingProgressParamVo paramVo) {
        log.info("O getBreedingBatchParamForApp paramVo={}", paramVo);
        Assert.notNull(paramVo.getPlanId(), "养殖计划id不能为空");
        Assert.notNull(paramVo.getCoopId(), "鸡舍id不能为空");
        Integer planId = paramVo.getPlanId();
        Integer coopId = paramVo.getCoopId();
        Integer dayAge = paramVo.getDayAge();
        String strDate = paramVo.getStrDate();
        try {
            BreedingBatchParamListDto breedingBatchParamListDto = breedingPlanService.getBreedingBatchParamForApp(planId, coopId, dayAge, strDate);
            return BaseResponse.successInstance(breedingBatchParamListDto);
        } catch (Exception ex) {
            log.error("O getBreedingBatchParamForApp error paramVo=" + paramVo, ex);
            return BaseResponse.errorInstance("查询鸡舍养殖过程批次参数异常");
        }
    }

    /**
     * 查询批次养殖记录
     *
     * @param planId
     * @param coopId
     * @return
     * @author yj
     */
    @ApiOperation("查询批次养殖记录")
    @GetMapping("/listBatchBreedingRecord")
    public BaseResponse listBatchBreedingRecord(@RequestParam Integer planId, @RequestParam Integer coopId) {
        log.info("O listBatchBreedingRecord planId={},coopId={}", planId, coopId);
        BreedingRecordDto breedingRecordDto;
        try {
            long dayAge = breedingPlanService.getDayAge(planId);
            breedingRecordDto = breedingProgressService.getBreedingRecordsById(planId, coopId, (int) dayAge);
        } catch (Exception ex) {
            log.error("O listBatchBreedingRecord error planId=" + planId + ",coopId=" + coopId, ex);
            return BaseResponse.errorInstance("查询批次养殖记录异常");
        }
        return BaseResponse.successInstance(breedingRecordDto);
    }

    @ApiOperation("获取农户应喂药列表")
    @GetMapping("/listBreedingRecordDrug")
    public BaseResponse listBreedingRecordDrug(@RequestParam Integer planId, @RequestParam Integer coopId) {
        log.info("O listBreedingRecordDrug planId={},coopId={}", planId, coopId);
        return BaseResponse.successInstance(breedingPlanService.listBreedingRecordDrug(planId, coopId));
    }

    /**
     * 上传养殖记录
     * @param createBreedingRecordDto
     * @return
     * @author yj
     */
    @ApiOperation("上传养殖记录")
    @PostMapping("/uploadBreedingRecord")
    public BaseResponse uploadBreedingRecord(@RequestBody @Validated CreateBreedingRecordDto createBreedingRecordDto) {
        breedingPlanService.uploadBreedingRecord(createBreedingRecordDto);
        return BaseResponse.successInstance("上传成功");
    }

    @GetMapping("/getCustomerInfo")
    @ApiOperation("获取客户基本信息")
    public BaseResponse getCustomerInfo() {
        return BaseResponse.successInstance(breedingFarmerService.getCustomerInfo());
    }
}
