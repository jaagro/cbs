package com.jaagro.cbs.biz.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jaagro.cbs.api.constant.CertificateStatus;
import com.jaagro.cbs.api.constant.ContractStatus;
import com.jaagro.cbs.api.dto.base.CustomerContactsReturnDto;
import com.jaagro.cbs.api.dto.base.GetCustomerUserDto;
import com.jaagro.cbs.api.dto.base.ListEmployeeDto;
import com.jaagro.cbs.api.dto.farmer.*;
import com.jaagro.cbs.api.dto.farmer.BatchCoopDto;
import com.jaagro.cbs.api.dto.farmer.BatchPlantDto;
import com.jaagro.cbs.api.dto.farmer.BreedingPlanDetailDto;
import com.jaagro.cbs.api.dto.plan.*;
import com.jaagro.cbs.api.dto.progress.BreedingBatchParamTrackingDto;
import com.jaagro.cbs.api.dto.standard.BreedingParameterDto;
import com.jaagro.cbs.api.enums.*;
import com.jaagro.cbs.api.model.*;
import com.jaagro.cbs.api.service.BreedingPlanService;
import com.jaagro.cbs.api.service.BreedingProgressService;
import com.jaagro.cbs.biz.bo.BatchPlantCoopBo;
import com.jaagro.cbs.biz.mapper.*;
import com.jaagro.cbs.biz.service.CustomerClientService;
import com.jaagro.cbs.biz.service.UserClientService;
import com.jaagro.cbs.biz.utils.DateUtil;
import com.jaagro.cbs.biz.utils.MathUtil;
import com.jaagro.cbs.biz.utils.SequenceCodeUtils;
import com.jaagro.constant.UserInfo;
import com.jaagro.utils.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 养殖计划管理
 *
 * @author @Gao.
 */
@Slf4j
@Service
public class BreedingPlanServiceImpl implements BreedingPlanService {

    @Autowired
    private SequenceCodeUtils sequenceCodeUtils;
    @Autowired
    private BreedingPlanMapperExt breedingPlanMapper;
    @Autowired
    private BatchPlantCoopMapperExt batchPlantCoopMapper;
    @Autowired
    private CurrentUserService currentUserService;
    @Autowired
    private BatchContractMapperExt batchContractMapper;
    @Autowired
    private ContractSourceMapperExt contractSourceMapper;
    @Autowired
    private ContractPriceSectionMapperExt contractPriceSectionMapper;
    @Autowired
    private CustomerClientService customerClientService;
    @Autowired
    private BreedingBatchParameterMapperExt breedingBatchParameterMapper;
    @Autowired
    private UserClientService userClientService;
    @Autowired
    private BreedingPlantServiceImpl breedingPlantService;
    @Autowired
    private PurchaseOrderMapperExt purchaseOrderMapper;
    @Autowired
    private BatchInfoMapperExt batchInfoMapper;
    @Autowired
    private MathUtil mathUtil;
    @Autowired
    private CoopMapperExt coopMapper;
    @Autowired
    private DeviceAlarmLogMapperExt deviceAlarmLogMapper;
    @Autowired
    private TechConsultRecordMapperExt techConsultRecordMapper;
    @Autowired
    private BreedingStandardMapperExt breedingStandardMapper;
    @Autowired
    private BatchCoopDailyMapperExt batchCoopDailyMapper;
    @Autowired
    private BreedingProgressService breedingProgressService;
    @Autowired
    private BreedingBatchDrugMapperExt breedingBatchDrugMapper;
    @Autowired
    private BreedingRecordMapperExt breedingRecordMapper;
    @Autowired
    private ProductMapperExt productMapper;
    @Autowired
    private BreedingRecordItemsMapperExt breedingRecordItemsMapper;
    @Autowired
    private PurchaseOrderItemsMapperExt purchaseOrderItemsMapper;

    /**
     * 创建养殖计划
     *
     * @param dto
     * @author @Gao.
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createBreedingPlan(CreateBreedingPlanDto dto) {
        BreedingPlan breedingPlan = new BreedingPlan();
        UserInfo currentUser = currentUserService.getCurrentUser();
        if (currentUser != null && currentUser.getId() != null && currentUser.getTenantId() != null &&
                currentUser.getName() != null) {
            breedingPlan
                    .setCreateUserId(currentUser.getId())
                    .setCreateUserName(currentUser.getName())
                    .setTenantId(currentUser.getTenantId());
        }
        String batchNo = sequenceCodeUtils.genSeqCode("AT");
        breedingPlan
                .setBatchNo(batchNo)
                .setPlanStatus(PlanStatusEnum.ENTER_CONTRACT.getCode());
        BeanUtils.copyProperties(dto, breedingPlan);
        //插入养殖计划
        breedingPlanMapper.insertSelective(breedingPlan);
        //插入养殖关联表
        if (!CollectionUtils.isEmpty(dto.getPlantIds())) {
            List<BatchPlantCoop> batchPlantCoops = new ArrayList<>();
            List<Integer> plantIds = dto.getPlantIds();
            for (Integer plantId : plantIds) {
                BatchPlantCoop batchPlantCoop = new BatchPlantCoop();
                batchPlantCoop
                        .setCreateTime(new Date())
                        .setPlanId(breedingPlan.getId())
                        .setCreateUserId(currentUser.getId())
                        .setPlantId(plantId)
                        .setEnable(Boolean.TRUE);
                batchPlantCoops.add(batchPlantCoop);
            }
            if (!CollectionUtils.isEmpty(batchPlantCoops)) {
                batchPlantCoopMapper.insertBatch(batchPlantCoops);
            }
        }
    }

    /**
     * 养殖计划列表 批次列表 养殖列表（复用）
     * (返回数据类型相同但计划状态不同 用flag区分
     * flag=1表示养殖计划列表数据
     * flag=2 表示批次列表数据）
     *
     * @param dto
     * @return
     * @author @Gao.
     */
    @Override
    public PageInfo listBreedingPlan(BreedingPlanParamDto dto) {
        PageHelper.startPage(dto.getPageNum(), dto.getPageSize());
//        dto.setTenantId();    先注释,等待从userInfo获取租户id
        if (dto.getCustomerInfo() != null) {
            BaseResponse<List<Integer>> listBaseResponse = customerClientService.listCustomerIdByKeyWord(dto.getCustomerInfo());
            if (!CollectionUtils.isEmpty(listBaseResponse.getData())) {
                List<Integer> customerIds = listBaseResponse.getData();
                dto.setCustomerIds(customerIds);
            }
        }
        List<ReturnBreedingPlanDto> planDtoList = breedingPlanMapper.listBreedingPlan(dto);
        for (ReturnBreedingPlanDto returnBreedingPlanDto : planDtoList) {
            //填充养殖户信息
            CustomerContactsReturnDto contactsReturnDto = customerClientService.getCustomerContactByCustomerId(returnBreedingPlanDto.getCustomerId());
            if (contactsReturnDto != null) {
                returnBreedingPlanDto
                        .setCustomerName(contactsReturnDto.getContact())
                        .setCustomerPhone(contactsReturnDto.getPhone());
            }
            //填充养殖场信息
            List<Plant> plants = breedingPlantService.listPlantInfoByPlanId(returnBreedingPlanDto.getId());
            returnBreedingPlanDto.setPlants(plants);
            //填充进度
            BreedingBatchParameterExample parameterExample = new BreedingBatchParameterExample();
            parameterExample.createCriteria()
                    .andEnableEqualTo(true)
                    .andPlanIdEqualTo(returnBreedingPlanDto.getId())
                    .andBatchNoEqualTo(returnBreedingPlanDto.getBatchNo());
            List<BreedingBatchParameter> breedingBatchParameters = breedingBatchParameterMapper.selectByExample(parameterExample);
            if (!CollectionUtils.isEmpty(breedingBatchParameters)) {
                BreedingBatchParameter parameter = breedingBatchParameters.get(0);
                returnBreedingPlanDto.setAllDayAge(parameter.getDayAge());
                try {
                    long day = getDayAge(returnBreedingPlanDto.getId());
                    returnBreedingPlanDto.setAlreadyDayAge((int) day);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
        return new PageInfo(planDtoList);
    }

    /**
     * 根据计划id获取当前日龄
     *
     * @param planId
     * @return
     */
    @Override
    public long getDayAge(Integer planId) throws Exception {
        long day = 0;
        BreedingPlan breedingPlan = breedingPlanMapper.selectByPrimaryKey(planId);
        if (breedingPlan != null) {
            Date beginDate = breedingPlan.getPlanTime();
            Date endDate = new Date();
            if (beginDate == null && endDate == null) {
                return day;
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            beginDate = sdf.parse(sdf.format(beginDate));
            endDate = sdf.parse(sdf.format(endDate));
            day = (endDate.getTime() - beginDate.getTime()) / (24 * 60 * 60 * 1000);
        } else {
            throw new NullPointerException("计划不存在");
        }
        return day + 1;
    }

    /**
     * 批次详情(农户app)
     *
     * @return
     * @author yj
     */
    private void generateBatchDetail(BreedingPlanDetailDto breedingPlanDetailDto) {
        Integer planId = breedingPlanDetailDto.getId();
        try {
            breedingPlanDetailDto.setStrPlanStatus(PlanStatusEnum.getDescByCode(breedingPlanDetailDto.getPlanStatus()));
            // 存栏量,今日耗料量
            BatchInfo theLatestBatchInfo = batchInfoMapper.getTheLatestBatchInfo(planId);

            // 日龄
            Integer dayAge = null;
            if (theLatestBatchInfo != null) {
                breedingPlanDetailDto.setBreedingStock(new BigDecimal(theLatestBatchInfo.getCurrentAmount()))
                        .setFodderAmount(theLatestBatchInfo.getFodderAmount());
                dayAge = theLatestBatchInfo.getDayAge();
            } else {
                breedingPlanDetailDto.setBreedingStock(new BigDecimal(breedingPlanDetailDto.getPlanChickenQuantity()));
                breedingPlanDetailDto.setFodderAmount(new BigDecimal("0"));
            }
            if (dayAge == null) {
                dayAge = (int) getDayAge(planId);
            }
            breedingPlanDetailDto.setDayAge(dayAge);
            // 计划养殖场鸡舍信息
            List<BatchPlantCoopBo> batchPlantCoopBoList = batchPlantCoopMapper.listPlantCoopInfoByPlanId(planId);
            if (!CollectionUtils.isEmpty(batchPlantCoopBoList)) {
                Set<Integer> plantIdSet = new HashSet<>();
                List<BatchPlantDto> batchPlantDtoList = new ArrayList<>();
                batchPlantCoopBoList.forEach(batchPlantCoopBo -> plantIdSet.add(batchPlantCoopBo.getPlantId()));
                for (Integer plantId : plantIdSet) {
                    BatchPlantDto batchPlantDto = new BatchPlantDto();
                    batchPlantDto.setId(plantId);
                    List<BatchCoopDto> batchCoopDtoList = new ArrayList<>();
                    batchPlantDto.setBatchCoopDtoList(batchCoopDtoList);
                    batchPlantDtoList.add(batchPlantDto);
                }
                for (BatchPlantCoopBo batchPlantCoopBo : batchPlantCoopBoList) {
                    for (BatchPlantDto batchPlantDto : batchPlantDtoList) {
                        if (batchPlantCoopBo.getPlantId().equals(batchPlantDto.getId())) {
                            batchPlantDto.setPlantName(batchPlantCoopBo.getPlantName());
                            List<BatchCoopDto> batchCoopDtoList = batchPlantDto.getBatchCoopDtoList();
                            BatchCoopDto batchCoopDto = new BatchCoopDto();
                            batchCoopDto.setId(batchPlantCoopBo.getCoopId())
                                    .setCoopName(batchPlantCoopBo.getCoopName())
                                    .setCoopNo(batchPlantCoopBo.getCoopNo())
                                    .setAlarm(false);
                            // 异常提示
                            DeviceAlarmLogExample deviceAlarmLogExample = new DeviceAlarmLogExample();
                            deviceAlarmLogExample.createCriteria().andCoopIdEqualTo(batchPlantCoopBo.getCoopId())
                                    .andPlanIdEqualTo(planId).andDayAgeEqualTo(dayAge);
                            List<DeviceAlarmLog> deviceAlarmLogList = deviceAlarmLogMapper.selectByExample(deviceAlarmLogExample);
                            if (!CollectionUtils.isEmpty(deviceAlarmLogList)) {
                                batchCoopDto.setAlarm(true);
                            }
                            // 鸡舍存栏量
                            BatchCoopDailyExample batchCoopDailyExample = new BatchCoopDailyExample();
                            batchCoopDailyExample.createCriteria().andPlanIdEqualTo(planId)
                                    .andCoopIdEqualTo(batchPlantCoopBo.getCoopId())
                                    .andDayAgeEqualTo(dayAge);
                            List<BatchCoopDaily> batchCoopDailyList = batchCoopDailyMapper.selectByExample(batchCoopDailyExample);
                            if (!CollectionUtils.isEmpty(batchCoopDailyList)) {
                                batchCoopDto.setBreedingStock(batchCoopDailyList.get(0).getCurrentAmount());
                            } else {
                                batchCoopDto.setBreedingStock(batchPlantCoopBo.getBreedingValue());
                            }
                            batchCoopDtoList.add(batchCoopDto);
                        }
                    }
                }
                breedingPlanDetailDto.setBatchPlantDtoList(batchPlantDtoList);
            }
        } catch (Exception ex) {
            log.info("O getBatchDetail error planId=" + planId, ex);
        }
    }

    /**
     * 养殖过程批次参数
     *
     * @param planId
     * @param coopId
     * @param dayAge
     * @param strDate
     * @return
     */
    @Override
    public BreedingBatchParamListDto getBreedingBatchParamForApp(Integer planId, Integer coopId, Integer dayAge, String strDate) throws Exception {
        BreedingBatchParamListDto breedingBatchParamListDto = new BreedingBatchParamListDto();
        SimpleDateFormat dateFormatDay = new SimpleDateFormat("MM-dd");
        Date now = new Date();
        if (dayAge == null) {
            dayAge = (int) getDayAge(planId);
        }
        if (strDate == null) {
            strDate = dateFormatDay.format(now);
        }
        // 日龄数据
        List<DayAgeDto> dayAgeDtoList = getDayDtoList(dayAge);
        breedingBatchParamListDto.setDayAgeDtoList(dayAgeDtoList);
        // 养殖日期追加年份
        strDate = strDateAppendYear(strDate);
        List<BreedingBatchParamTrackingDto> breedingBatchParam = breedingProgressService.getBreedingBatchParamById(planId, coopId, dayAge, strDate);
        breedingBatchParamListDto.setBreedingBatchParamTrackingDtoList(breedingBatchParam);
        return breedingBatchParamListDto;
    }

    /**
     * 获取农户某个计划某个鸡舍每日应喂药列表
     *
     * @param planId
     * @param coopId
     * @return
     */
    @Override
    public List<BreedingRecordItemsDto> listBreedingRecordDrug(Integer planId, Integer coopId) {
        List<BreedingRecordItemsDto> recordItemsDtoList = new ArrayList<>();
        try {
            int dayAge = (int) getDayAge(planId);
            // 查询当日喂药记录
            BreedingRecord params = new BreedingRecord();
            params.setPlanId(planId)
                    .setCoopId(coopId)
                    .setDayAge(dayAge)
                    .setRecordType(BreedingRecordTypeEnum.FEED_MEDICINE.getCode());
            List<BreedingRecordDto> breedingRecordDtoList = breedingRecordMapper.listByParams(params);
            if (!CollectionUtils.isEmpty(breedingRecordDtoList)) {
                for (BreedingRecordDto breedingRecordDto : breedingRecordDtoList) {
                    List<BreedingRecordItems> breedingRecordItemsList = breedingRecordDto.getBreedingRecordItemsList();
                    if (!CollectionUtils.isEmpty(breedingRecordItemsList)) {
                        for (BreedingRecordItems breedingRecordItems : breedingRecordItemsList) {
                            BreedingRecordItemsDto breedingRecordItemsDto = new BreedingRecordItemsDto();
                            breedingRecordItemsDto.setBreedingTime(breedingRecordItems.getBreedingTime())
                                    .setBreedingValue(breedingRecordItems.getBreedingValue());
                            Product product = productMapper.selectByPrimaryKey(breedingRecordItems.getProductId());
                            if (product != null) {
                                breedingRecordItemsDto.setCapacityUnit(product.getCapacityUnit() == null ? "" : CapacityUnitEnum.getTypeByCode(product.getCapacityUnit()));
                                breedingRecordItemsDto.setProductId(breedingRecordItems.getProductId());
                                breedingRecordItemsDto.setProductName(product.getProductName());
                            }
                        }
                    }
                }
            }
            // 查询当前日龄应喂药品
            BreedingBatchDrugExample batchDrugExample = new BreedingBatchDrugExample();
            batchDrugExample.createCriteria().andPlanIdEqualTo(planId).andStopDrugFlagEqualTo(Boolean.FALSE).andDayAgeStartLessThanOrEqualTo(dayAge).andDayAgeEndGreaterThan(dayAge).andEnableEqualTo(Boolean.TRUE);
            List<BreedingBatchDrug> breedingBatchDrugList = breedingBatchDrugMapper.selectByExample(batchDrugExample);
            if (!CollectionUtils.isEmpty(breedingBatchDrugList)) {
                if (!CollectionUtils.isEmpty(recordItemsDtoList)) {
                    filterBreedingBatchDrug(breedingBatchDrugList, recordItemsDtoList);
                }
                if (!CollectionUtils.isEmpty(breedingBatchDrugList)) {
                    BreedingPlan breedingPlan = breedingPlanMapper.selectByPrimaryKey(planId);
                    Integer coopQuantityStock;
                    Coop coop = coopMapper.selectByPrimaryKey(coopId);
                    // 当前鸡舍存栏量
                    BatchCoopDailyExample batchCoopDailyExample = new BatchCoopDailyExample();
                    batchCoopDailyExample.createCriteria().andDayAgeEqualTo(dayAge).andPlanIdEqualTo(planId).andCoopIdEqualTo(coopId);
                    List<BatchCoopDaily> batchCoopDailyList = batchCoopDailyMapper.selectByExample(batchCoopDailyExample);
                    if (!CollectionUtils.isEmpty(batchCoopDailyList)) {
                        coopQuantityStock = batchCoopDailyList.get(0).getCurrentAmount();
                    } else {
                        coopQuantityStock = coop.getBreedingValue();
                    }
                    // 当前计划存栏量
                    Integer batchQuantityStock;
                    BatchInfo theLatestBatchInfo = batchInfoMapper.getTheLatestBatchInfo(planId);
                    if (theLatestBatchInfo != null) {
                        batchQuantityStock = theLatestBatchInfo.getCurrentAmount();
                    } else {
                        batchQuantityStock = breedingPlan.getPlanChickenQuantity();
                    }
                    for (BreedingBatchDrug breedingBatchDrug : breedingBatchDrugList) {
                        BreedingRecordItemsDto recordItemsDto = new BreedingRecordItemsDto();
                        Product product = productMapper.selectByPrimaryKey(breedingBatchDrug.getProductId());
                        if (product != null) {
                            recordItemsDto.setProductId(product.getId())
                                    .setProductName(product.getProductName());
                            if (product.getCapacityUnit() != null) {
                                recordItemsDto.setCapacityUnit(CapacityUnitEnum.getTypeByCode(product.getCapacityUnit()));
                            }
                            if (coopQuantityStock != null && batchQuantityStock != null && breedingBatchDrug.getFeedVolume() != null) {
                                recordItemsDto.setBreedingValue(new BigDecimal(coopQuantityStock).divide(new BigDecimal(batchQuantityStock), 6, BigDecimal.ROUND_HALF_UP).multiply(breedingBatchDrug.getFeedVolume()).setScale(0, BigDecimal.ROUND_UP));
                            }
                            recordItemsDtoList.add(recordItemsDto);
                        }
                    }
                }
            }
        } catch (Exception ex) {
            log.error("O listBreedingRecordDrug planId=" + planId + ",coopId=" + coopId, ex);
        }
        return recordItemsDtoList;
    }

    /**
     * 农户端上传养殖记录
     *
     * @param createBreedingRecordDto
     * @author yj
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void uploadBreedingRecord(CreateBreedingRecordDto createBreedingRecordDto) {
        Integer planId = createBreedingRecordDto.getPlanId();
        BreedingPlan breedingPlan = breedingPlanMapper.selectByPrimaryKey(planId);
        if (breedingPlan == null) {
            throw new RuntimeException("计划id=" + planId + "不存在");
        }
        BreedingRecord breedingRecord = new BreedingRecord();
        BeanUtils.copyProperties(createBreedingRecordDto, breedingRecord);
        UserInfo userInfo = currentUserService.getCurrentUser();
        breedingRecord.setCreateTime(new Date())
                .setCreateUserId(userInfo == null ? null : userInfo.getId())
                .setEnable(Boolean.TRUE)
                .setBatchNo(breedingPlan.getBatchNo());
        breedingRecordMapper.insertSelective(breedingRecord);
        if (!CollectionUtils.isEmpty(createBreedingRecordDto.getBreedingRecordItemsDtoList())) {
            List<BreedingRecordItems> breedingRecordItemsList = new ArrayList<>();
            for (BreedingRecordItemsDto itemsDto : createBreedingRecordDto.getBreedingRecordItemsDtoList()) {
                BreedingRecordItems breedingRecordItems = new BreedingRecordItems();
                BeanUtils.copyProperties(itemsDto, breedingRecordItems);
                breedingRecordItems.setBreedingRecordId(breedingRecord.getId())
                        .setCreateTime(new Date())
                        .setEnable(Boolean.TRUE)
                        .setCreateUserId(userInfo == null ? null : userInfo.getId());
                breedingRecordItemsList.add(breedingRecordItems);
            }
            if (!CollectionUtils.isEmpty(breedingRecordItemsList)) {
                breedingRecordItemsMapper.batchInsert(breedingRecordItemsList);
            }
        }
    }

    /**
     * 养殖页上鸡计划列表查询
     *
     * @param dto
     * @return
     */
    @Override
    public PageInfo<BreedingPlanDetailDto> listBreedingBatchForFarmer(BreedingBatchParamDto dto) {
        PageHelper.startPage(dto.getPageNum(), dto.getPageSize());
        UserInfo currentUser = currentUserService.getCurrentUser();
        Integer currentUserId = currentUser == null ? null : currentUser.getId();
        if (currentUserId == null) {
            throw new RuntimeException("获取当前登录用户信息失败");
        }
        GetCustomerUserDto customerUser = userClientService.getCustomerUserById(currentUser.getId());
        Integer customerId = customerUser == null ? null : customerUser.getRelevanceId();
        if (customerId == null) {
            throw new RuntimeException("当前登录用户对应客户信息为空");
        }
        List<BreedingPlanDetailDto> breedingPlanDetailDtoList = breedingPlanMapper.listByCustomerId(customerId);
        if (!CollectionUtils.isEmpty(breedingPlanDetailDtoList)) {
            breedingPlanDetailDtoList.forEach(breedingPlanDetailDto -> generateBatchDetail(breedingPlanDetailDto));
        }
        return new PageInfo<>(breedingPlanDetailDtoList);
    }

    private void filterBreedingBatchDrug(List<BreedingBatchDrug> breedingBatchDrugList, List<BreedingRecordItemsDto> recordItemsDtoList) {
        if (!CollectionUtils.isEmpty(breedingBatchDrugList) && !CollectionUtils.isEmpty(recordItemsDtoList)) {
            Iterator<BreedingBatchDrug> iterator = breedingBatchDrugList.iterator();
            while (iterator.hasNext()) {
                BreedingBatchDrug breedingBatchDrug = iterator.next();
                for (BreedingRecordItemsDto breedingRecordItemsDto : recordItemsDtoList) {
                    if (breedingRecordItemsDto.getProductId().equals(breedingBatchDrug.getProductId())) {
                        iterator.remove();
                    }
                }
            }
        }
    }

    /**
     * 获取近三天日龄
     *
     * @param dayAge
     * @return
     */
    private List<DayAgeDto> getDayDtoList(Integer dayAge) {
        List<DayAgeDto> dayAgeDtoList = new ArrayList<>();
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
        int showForApp = 3;
        if (dayAge != null && dayAge >= 1) {
            if (dayAge <= showForApp) {
                for (int i = 1; i <= dayAge; i++) {
                    DayAgeDto dayAgeDto = new DayAgeDto();
                    dayAgeDto.setDayAge(i);
                    dayAgeDto.setStrDate(sdf.format(DateUtils.addDays(now, i - dayAge)));
                    dayAgeDtoList.add(dayAgeDto);
                }
            } else {
                for (int i = dayAge - (showForApp - 1); i <= dayAge; i++) {
                    DayAgeDto dayAgeDto = new DayAgeDto();
                    dayAgeDto.setDayAge(i);
                    dayAgeDto.setStrDate(sdf.format(DateUtils.addDays(now, i - dayAge)));
                    dayAgeDtoList.add(dayAgeDto);
                }
            }
        }
        return dayAgeDtoList;
    }

    /**
     * 日期追加年份
     *
     * @param strDate
     * @return
     */
    private String strDateAppendYear(String strDate) {
        // 判断日期是否跨年
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
        Date now = new Date();
        String strNow = sdf.format(now);
        Integer monthLength = 2;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        if (Integer.parseInt(strNow.substring(0, monthLength)) < Integer.parseInt(strDate.substring(0, monthLength))) {
            calendar.add(Calendar.YEAR, -1);
        }
        strDate = calendar.get(Calendar.YEAR) + "-" + strDate;
        return strDate;
    }

    /**
     * 录入合同
     *
     * @param createPlanContractDto
     * @author yj
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createPlanContract(CreatePlanContractDto createPlanContractDto) {
        Integer planId = createPlanContractDto.getPlanId();
        BreedingPlan breedingPlan = breedingPlanMapper.selectByPrimaryKey(planId);
        if (breedingPlan == null) {
            throw new RuntimeException("养殖计划id=" + planId + "不存在");
        }
        UserInfo currentUser = currentUserService.getCurrentUser();
        Integer currentUserId = currentUser == null ? null : currentUser.getId();
        // 插入计划合同
        BatchContract batchContract = new BatchContract();
        BeanUtils.copyProperties(createPlanContractDto, batchContract);
        batchContract.setContractNumber(sequenceCodeUtils.genSeqCode("HT"))
                .setContractDate(new Date())
                .setCreateTime(new Date())
                .setContractStatus(ContractStatus.APPROVE)
                .setCreateUserId(currentUserId)
                .setCustomerId(breedingPlan.getCustomerId())
                .setEnable(true);
        batchContractMapper.insertSelective(batchContract);
        // 插入计划合同图片
        if (!CollectionUtils.isEmpty(createPlanContractDto.getImageUrlList())) {
            List<ContractSource> contractSourceList = new ArrayList<>();
            for (String imageUrl : createPlanContractDto.getImageUrlList()) {
                ContractSource contractSource = new ContractSource();
                contractSource.setSourceUrl(imageUrl)
                        .setSourceStatus(CertificateStatus.NORMAL)
                        .setCreateTime(new Date())
                        .setCreateUserId(currentUserId)
                        .setSourceType(SourceTypeEnum.IMAGE.getCode())
                        .setEnable(true)
                        .setPlanId(createPlanContractDto.getPlanId())
                        .setPlanContractId(batchContract.getId());
                contractSourceList.add(contractSource);
            }
            contractSourceMapper.batchInsert(contractSourceList);
        }
        // 插入回收价格区间
        List<ContractPriceSectionDto> contractPriceSectionDtoList = createPlanContractDto.getContractPriceSectionDtoList();
        if (!CollectionUtils.isEmpty(contractPriceSectionDtoList)) {
            List<ContractPriceSection> contractPriceSectionList = new ArrayList<>();
            for (ContractPriceSectionDto dto : contractPriceSectionDtoList) {
                ContractPriceSection contractPriceSection = new ContractPriceSection();
                BeanUtils.copyProperties(dto, contractPriceSection);
                contractPriceSection.setContractId(batchContract.getId())
                        .setCreateTime(new Date())
                        .setCreateUserId(currentUserId)
                        .setEnable(true)
                        .setPlanId(createPlanContractDto.getPlanId());
                if (contractPriceSection.getMarketPriceFlag() == null) {
                    contractPriceSection.setMarketPriceFlag(Boolean.FALSE);
                }
                contractPriceSectionList.add(contractPriceSection);
            }
            contractPriceSectionMapper.batchInsert(contractPriceSectionList);
        }
        // 更新养殖计划状态
        breedingPlan.setModifyTime(new Date())
                .setPlanTime(createPlanContractDto.getStartDate())
                .setPlanChickenQuantity(createPlanContractDto.getBabychickQuantity())
                .setModifyUserId(currentUserId)
                .setPlanStatus(PlanStatusEnum.PARAM_CORRECT.getCode());
        breedingPlanMapper.updateByPrimaryKeySelective(breedingPlan);
    }

    /**
     * 更新养殖计划
     *
     * @param dto
     * @author @Gao.
     */
    @Override
    public void upDateBreedingPlanDetails(UpdateBreedingPlanDto dto) {
        BreedingPlan breedingPlan = new BreedingPlan();
        BeanUtils.copyProperties(dto, breedingPlan);
        breedingPlanMapper.updateByPrimaryKeySelective(breedingPlan);
    }

    /**
     * 养殖计划列表详情
     *
     * @param planId
     * @return
     * @author @Gao.
     */
    @Override
    public ReturnBreedingPlanDetailsDto breedingPlanDetails(Integer planId) {

        ReturnBreedingPlanDetailsDto returnBreedingPlanDto = new ReturnBreedingPlanDetailsDto();
        //养殖计划信息
        BreedingPlan breedingPlan = breedingPlanMapper.selectByPrimaryKey(planId);
        if (breedingPlan == null) {
            throw new RuntimeException("当前养殖计划 " + planId + " 不存在");
        }
        BeanUtils.copyProperties(breedingPlan, returnBreedingPlanDto);
        //累计所有死淘数量
        BigDecimal accumulativeDeadAmount = batchInfoMapper.accumulativeDeadAmount(planId);
        //累计所有的出栏量
        BigDecimal accumulativeSaleAmount = batchInfoMapper.accumulativeSaleAmount(planId);
        BigDecimal breedingStock = null;
        if (breedingPlan.getPlanChickenQuantity() != null) {
            if (accumulativeDeadAmount != null) {
                breedingStock = BigDecimal.valueOf(breedingPlan.getPlanChickenQuantity()).subtract(accumulativeDeadAmount);
            } else {
                breedingStock = BigDecimal.valueOf(breedingPlan.getPlanChickenQuantity());
            }
        }
        if (breedingStock != null) {
            if (accumulativeSaleAmount != null) {
                breedingStock = breedingStock.subtract(accumulativeSaleAmount);
            }
        }
        if (breedingStock != null) {
            returnBreedingPlanDto.setBreedingStock(breedingStock.intValue());
        }
        if (breedingPlan.getPlanChickenQuantity() != null) {
            returnBreedingPlanDto.setPlanChickenQuantity(breedingPlan.getPlanChickenQuantity());
        }
        //养殖场信息
        List<Plant> plants = breedingPlantService.listPlantInfoByPlanId(breedingPlan.getId());
        returnBreedingPlanDto.setPlants(plants);
        //养殖户信息
        CustomerContactsReturnDto contactsReturnDto = customerClientService.getCustomerContactByCustomerId(breedingPlan.getCustomerId());
        if (contactsReturnDto != null) {
            returnBreedingPlanDto
                    .setCustomerName(contactsReturnDto.getContact())
                    .setCustomerPhone(contactsReturnDto.getPhone());
        }
        //技术员信息
        BaseResponse<List<ListEmployeeDto>> empByDeptId = userClientService.listTechnician();
        if (!CollectionUtils.isEmpty(empByDeptId.getData())) {
            List<ListEmployeeDto> listEmployeeDtos = empByDeptId.getData();
            returnBreedingPlanDto.setTechnicianList(listEmployeeDtos);
        }
        return returnBreedingPlanDto;
    }

    /**
     * 待上鸡签收详情
     *
     * @param plantId
     * @return
     * @author @Gao.
     */
    @Override
    public ReturnChickenSignDetailsDto chickenSignDetails(Integer plantId) {
        ReturnChickenSignDetailsDto returnChickenSignDetailsDto = new ReturnChickenSignDetailsDto();
        //养殖计划详情信息
        ReturnBreedingPlanDetailsDto returnBreedingPlanDetailsDto = breedingPlanDetails(plantId);
        returnChickenSignDetailsDto.setReturnBreedingPlanDetails(returnBreedingPlanDetailsDto);
        PurchaseOrderExample purchaseOrderExample = new PurchaseOrderExample();
        purchaseOrderExample.createCriteria().andPlanIdEqualTo(plantId);
        //商品采购列表信息
        List<ReturnPurchaseOrderDto> returnPurchaseOrderDtos = new ArrayList<>();
        List<PurchaseOrder> purchaseOrders = purchaseOrderMapper.selectByExample(purchaseOrderExample);
        if (!CollectionUtils.isEmpty(purchaseOrders)) {
            for (PurchaseOrder purchaseOrder : purchaseOrders) {
                BigDecimal totalPlanFeedStatistics = new BigDecimal(0);
                ReturnPurchaseOrderDto returnPurchaseOrderDto = new ReturnPurchaseOrderDto();
                BeanUtils.copyProperties(purchaseOrder, returnPurchaseOrderDto);
                if (purchaseOrder.getId() != null) {
                    PurchaseOrderItemsExample purchaseOrderItemsExample = new PurchaseOrderItemsExample();
                    purchaseOrderItemsExample
                            .createCriteria()
                            .andEnableEqualTo(true)
                            .andPurchaseOrderIdEqualTo(purchaseOrder.getId());
                    List<PurchaseOrderItems> purchaseOrderItemsList = purchaseOrderItemsMapper.selectByExample(purchaseOrderItemsExample);
                    if (!CollectionUtils.isEmpty(purchaseOrderItemsList)) {
                        PurchaseOrderItems purchase = purchaseOrderItemsList.get(0);
                        for (PurchaseOrderItems purchaseOrderItems : purchaseOrderItemsList) {
                            if (purchaseOrderItems.getQuantity() != null) {
                                totalPlanFeedStatistics = totalPlanFeedStatistics.add(purchaseOrderItems.getQuantity());
                            }
                        }
                        if (purchase.getUnit() != null) {
                            returnPurchaseOrderDto
                                    .setUnit(PackageUnitEnum.getDescByCode(purchase.getUnit()));
                        }
                    }
                }
                returnPurchaseOrderDto
                        .setProductType(ProductTypeEnum.getDescByCode(purchaseOrder.getProductType()))
                        .setPurchaseOrderStatus(PurchaseOrderStatusEnum.getDescByCode(purchaseOrder.getPurchaseOrderStatus()));
                //签收人信息
                BaseResponse<UserInfo> globalUser = userClientService.getGlobalUser(purchaseOrder.getSignerId());
                if (globalUser != null && globalUser.getData() != null) {
                    UserInfo userInfo = globalUser.getData();
                    if (userInfo != null && userInfo.getName() != null) {
                        returnPurchaseOrderDto
                                .setSignerName(userInfo.getName());
                    }
                    if (userInfo != null && userInfo.getPhoneNumber() != null) {
                        returnPurchaseOrderDto
                                .setSignerPhone(userInfo.getPhoneNumber());
                    }
                    returnPurchaseOrderDtos.add(returnPurchaseOrderDto);
                }
            }
        }
        returnChickenSignDetailsDto.setReturnPurchaseOrderDtos(returnPurchaseOrderDtos);
        return returnChickenSignDetailsDto;
    }

    /**
     * 获取养殖计划鸡舍信息
     *
     * @param planId
     * @return
     * @author yj
     */
    @Override
    public List<BreedingPlanCoopDto> listBreedingPlanCoopsForChoose(Integer planId) {
        return batchPlantCoopMapper.listBreedingPlanCoopsForChoose(planId);
    }

    /**
     * 养殖计划参数配置
     *
     * @param dto
     * @author yj
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void breedingPlanParamConfiguration(BreedingPlanParamConfigurationDto dto) {
        List<BreedingPlanCoopDto> breedingPlanCoopDtoList = dto.getBreedingPlanCoopDtoList();
        UserInfo currentUser = currentUserService.getCurrentUser();
        Integer currentUserId = currentUser == null ? null : currentUser.getId();
        // 插入养殖计划和鸡舍关系更新鸡舍在养数量和状态
        BreedingPlan breedingPlan = breedingPlanMapper.selectByPrimaryKey(dto.getPlanId());
        if (breedingPlan == null) {
            throw new RuntimeException("养殖计划id=" + dto.getPlanId() + "不存在");
        }
        // 插入养殖计划参数
        List<BreedingParameterDto> breedingParameterDtoList = dto.getBreedingParameterDtoList();
        Integer standardId = null;
        if (!CollectionUtils.isEmpty(breedingParameterDtoList)) {
            List<BreedingBatchParameter> batchParameterList = new ArrayList<>();
            for (BreedingParameterDto parameterDto : breedingParameterDtoList) {
                List<BreedingStandardParameter> breedingStandardParameterList = parameterDto.getBreedingStandardParameterList();
                if (!CollectionUtils.isEmpty(breedingStandardParameterList)) {
                    if (standardId == null) {
                        standardId = breedingStandardParameterList.get(0).getStandardId();
                    }
                    for (BreedingStandardParameter standardParameter : breedingStandardParameterList) {
                        BreedingBatchParameter batchParameter = new BreedingBatchParameter();
                        BeanUtils.copyProperties(standardParameter, batchParameter);
                        batchParameter.setParamId(standardParameter.getId())
                                .setPlanId(dto.getPlanId())
                                .setBatchNo(dto.getBatchNo())
                                .setCreateTime(new Date())
                                .setCreateUserId(currentUserId)
                                .setCustomerId(breedingPlan.getCustomerId())
                                .setStatus(ParameterStatusEnum.USEFUL.getCode())
                                .setEnable(Boolean.TRUE);
                        batchParameterList.add(batchParameter);
                    }
                }
            }
            if (!CollectionUtils.isEmpty(batchParameterList)) {
                breedingBatchParameterMapper.batchInsert(batchParameterList);
            }
        }
        BreedingStandard breedingStandard = breedingStandardMapper.selectByPrimaryKey(standardId);
        if (!CollectionUtils.isEmpty(breedingPlanCoopDtoList)) {
            List<BatchPlantCoop> batchPlantCoopList = new ArrayList<>();
            List<Coop> coopList = new ArrayList<>();
            for (BreedingPlanCoopDto coopDto : breedingPlanCoopDtoList) {
                if (coopDto.getBreedingValue() != null && coopDto.getBreedingValue() > 0) {
                    BatchPlantCoop batchPlantCoop = new BatchPlantCoop();
                    batchPlantCoop.setCoopId(coopDto.getId())
                            .setCreateTime(new Date())
                            .setCreateUserId(currentUserId)
                            .setEnable(Boolean.TRUE)
                            .setPlanId(dto.getPlanId())
                            .setPlantId(coopDto.getPlantId());
                    batchPlantCoopList.add(batchPlantCoop);
                    Coop coop = new Coop();
                    coop.setId(coopDto.getId())
                            .setBreedingValue(coopDto.getBreedingValue())
                            .setCoopStatus(CoopStatusEnum.BREEDING.getCode())
                            .setLastStartDate(breedingPlan.getPlanTime())
                            .setLastEndDate(DateUtils.addDays(breedingPlan.getPlanTime(), breedingStandard.getBreedingDays()))
                            .setModifyTime(new Date())
                            .setModifyUserId(currentUserId);
                    coopList.add(coop);
                }
            }
            if (!CollectionUtils.isEmpty(batchPlantCoopList)) {
                batchPlantCoopMapper.insertBatch(batchPlantCoopList);
            }
            if (!CollectionUtils.isEmpty(coopList)) {
                coopMapper.batchUpdateByPrimaryKeySelective(coopList);
            }
        }
        // 更新养殖计划
        breedingPlan.setPlanStatus(PlanStatusEnum.SIGN_CHICKEN.getCode())
                .setModifyTime(new Date())
                .setModifyUserId(currentUserId)
                .setBreedingDays(breedingStandard == null ? null : breedingStandard.getBreedingDays());
        breedingPlanMapper.updateByPrimaryKeySelective(breedingPlan);
    }

    /**
     * 养殖中详情 确认出栏详情
     *
     * @param planId
     * @author @Gao.
     */
    @Override
    public ReturnBreedingDetailsDto breedingDetails(Integer planId) {
        HashMap<Integer, BigDecimal> calculatePlanFeedWeightMap = new HashMap<>(16);
        ReturnBreedingDetailsDto returnBreedingDetailsDto = new ReturnBreedingDetailsDto();
        //送料情况信息
        BreedingPlan breedingPlan = breedingPlanMapper.selectByPrimaryKey(planId);
        if (breedingPlan == null) {
            throw new RuntimeException("当前养殖计划" + planId + "不存在");
        }
        BatchInfo batchInfo = batchInfoMapper.getTheLatestBatchInfo(planId);
        if (batchInfo != null) {
            //累计所有死淘数量
            BigDecimal accumulativeDeadAmount = batchInfoMapper.accumulativeDeadAmount(planId);
            //累计所有的出栏量
            BigDecimal accumulativeSaleAmount = batchInfoMapper.accumulativeSaleAmount(planId);
            //累计所有饲料喂养量
            BigDecimal accumulativeFeed = batchInfoMapper.accumulativeFeed(planId);
            //计算存活数
            BigDecimal survivalStock = new BigDecimal(breedingPlan.getPlanChickenQuantity()).subtract(accumulativeDeadAmount).abs();
            //计算存栏量
            BigDecimal breedingStock = new BigDecimal(breedingPlan.getPlanChickenQuantity()).subtract(accumulativeDeadAmount).subtract(accumulativeSaleAmount).abs();
            //计算成活率
            String percentage = null;
            if (survivalStock != null && breedingPlan.getPlanChickenQuantity() != null && breedingPlan.getPlanChickenQuantity() > 0) {
                percentage = mathUtil.percentage(survivalStock.intValue(), breedingPlan.getPlanChickenQuantity());
            }
            //计算预计出栏时间
            String expectSuchTime = null;
            if (breedingPlan.getPlanTime() != null && breedingPlan.getBreedingDays() != null) {
                expectSuchTime = DateUtil.accumulateDateByDay(breedingPlan.getPlanTime(), breedingPlan.getBreedingDays());
            }
            //计算异常次数
            DeviceAlarmLogExample deviceAlarmLogExample = new DeviceAlarmLogExample();
            deviceAlarmLogExample.createCriteria().andPlanIdEqualTo(planId);
            int abnormalWarn = deviceAlarmLogMapper.countByExample(deviceAlarmLogExample);
            //计算询问总次数
            TechConsultRecordExample techConsultRecordExample = new TechConsultRecordExample();
            techConsultRecordExample.createCriteria().andPlanIdEqualTo(planId);
            int askQues = techConsultRecordMapper.countByExample(techConsultRecordExample);
            //计算询问解决次数
            techConsultRecordExample.createCriteria().andTechConsultStatusEqualTo(TechConsultStatusEnum.STATUS_SOLVED.getCode());
            int solveQuestions = techConsultRecordMapper.countByExample(techConsultRecordExample);
            //计算计划采购 数量 重量
            List<CalculatePurchaseOrderDto> calculatePurchaseOrderDtos = new ArrayList<>();
            for (ProductTypeEnum productTypeEnum : ProductTypeEnum.values()) {
                BigDecimal planPurchaseValue = null;
                BigDecimal deliverPurchaseValue = null;
                int productType = productTypeEnum.getCode();
                CalculatePurchaseOrderDto calculatePurchaseOrderDto = new CalculatePurchaseOrderDto();
                HashMap<Integer, BigDecimal> calculatePurchaseOrderAllMap = calculatePurchaseOrder(planId, productType, null);
                if (calculatePurchaseOrderAllMap != null && calculatePurchaseOrderAllMap.get(productType) != null) {
                    planPurchaseValue = calculatePurchaseOrderAllMap.get(productType);
                    if (ProductTypeEnum.FEED.getCode() == productType) {
                        calculatePlanFeedWeightMap.put(productType, planPurchaseValue);
                    }
                }
                HashMap<Integer, BigDecimal> calculatePurchaseOrderMap = calculatePurchaseOrder(planId, productType, PurchaseOrderStatusEnum.ALREADY_SIGNED.getCode());
                if (calculatePurchaseOrderMap.get(productType) != null) {
                    deliverPurchaseValue = calculatePurchaseOrderMap.get(productType);
                }
                calculatePurchaseOrderDto
                        .setProductType(productType)
                        .setPlanPurchaseValue(planPurchaseValue)
                        .setDeliverPurchaseValue(deliverPurchaseValue);
                calculatePurchaseOrderDtos.add(calculatePurchaseOrderDto);
            }
            //计算计划饲料 剩余饲料
            if (calculatePlanFeedWeightMap.get(ProductTypeEnum.FEED.getCode()) != null) {
                BigDecimal totalPlanFeedWeight = calculatePlanFeedWeightMap.get(ProductTypeEnum.FEED.getCode());
                if (totalPlanFeedWeight != null) {
                    returnBreedingDetailsDto
                            .setPlanFeed(totalPlanFeedWeight)
                            .setRemainFeed(totalPlanFeedWeight.subtract(accumulativeFeed));
                }
            }
            //计算理论体重值
            BreedingBatchParameterExample breedingBatchParameterExample = new BreedingBatchParameterExample();
            breedingBatchParameterExample
                    .createCriteria()
                    .andPlanIdEqualTo(planId)
                    .andEnableEqualTo(true)
                    .andDayAgeEqualTo(batchInfo.getDayAge())
                    .andStatusEqualTo(BreedingStandardStatusEnum.ENABLE.getCode())
                    .andParamTypeEqualTo(BreedingStandardParamEnum.WEIGHT.getCode())
                    .andValueTypeEqualTo(BreedingStandardValueTypeEnum.STANDARD_VALUE.getCode());
            List<BreedingBatchParameter> breedingBatchParameters = breedingBatchParameterMapper.selectByExample(breedingBatchParameterExample);
            if (!CollectionUtils.isEmpty(breedingBatchParameters)) {
                BreedingBatchParameter breedingBatchParameter = breedingBatchParameters.get(0);
                if (MathUtil.isNum(breedingBatchParameter.getParamValue())) {
                    returnBreedingDetailsDto.setTheoryWeight(breedingBatchParameter.getParamValue());
                }
                //计算理论料肉比
                if (breedingBatchParameter.getParamValue() != null && MathUtil.isNum(breedingBatchParameter.getParamValue())) {
                    BigDecimal paramValue = new BigDecimal(breedingBatchParameter.getParamValue());
                    BigDecimal meat = null;
                    if (paramValue != null && breedingStock != null) {
                        meat = paramValue.multiply(breedingStock);
                    }
                    if (meat != null && accumulativeFeed != null) {
                        BigDecimal feedMeatRate = meat.divide(accumulativeFeed);
                        returnBreedingDetailsDto.setFeedMeatRate(feedMeatRate);
                    }
                }
            }
            //养殖场信息
            List<Plant> plants = breedingPlantService.listPlantInfoByPlanId(planId);
            returnBreedingDetailsDto
                    .setPlants(plants)
                    .setAskQuestions(askQues)
                    .setSurvivalRate(percentage)
                    .setAbnormalWarn(abnormalWarn)
                    .setDayAge(batchInfo.getDayAge())
                    .setExpectSuchTime(expectSuchTime)
                    .setSolveQuestions(solveQuestions)
                    .setCalculatePurchaseOrderDtos(calculatePurchaseOrderDtos);
            if (breedingStock != null) {
                returnBreedingDetailsDto
                        .setBreedingStock(breedingStock.intValue());
            }
            if (breedingPlan.getBreedingDays() != null) {
                returnBreedingDetailsDto
                        .setBreedingDays(breedingPlan.getBreedingDays());
            }
        }
        //养殖计划详情信息
        ReturnBreedingPlanDetailsDto returnBreedingPlanDetailsDto = breedingPlanDetails(planId);
        returnBreedingDetailsDto
                .setReturnBreedingPlanDetails(returnBreedingPlanDetailsDto);
        return returnBreedingDetailsDto;
    }

    /**
     * 根据不同商品类型 统计不同类型值
     *
     * @param planId
     * @param productType
     * @param purchaseOrderStatus
     * @author @Gao.
     */
    private HashMap<Integer, BigDecimal> calculatePurchaseOrder(Integer planId, Integer productType, Integer purchaseOrderStatus) {
        BigDecimal totalPlanFeedStatistics = BigDecimal.ZERO;
        HashMap<Integer, BigDecimal> calculatePurchaseOrderMap = new HashMap<>(16);
        //查询计划用料
        PurchaseOrderExample purchaseOrderExample = new PurchaseOrderExample();
        purchaseOrderExample
                .createCriteria()
                .andEnableEqualTo(true)
                .andPlanIdEqualTo(planId)
                .andProductTypeEqualTo(productType);
        if (purchaseOrderStatus != null) {
            purchaseOrderExample
                    .createCriteria()
                    .andPurchaseOrderStatusEqualTo(purchaseOrderStatus);
        }
        List<PurchaseOrder> purchaseOrders = purchaseOrderMapper.selectByExample(purchaseOrderExample);
        for (PurchaseOrder purchaseOrder : purchaseOrders) {
            PurchaseOrderItemsExample purchaseOrderItemsExample = new PurchaseOrderItemsExample();
            if (purchaseOrder.getId() != null) {
                purchaseOrderItemsExample
                        .createCriteria()
                        .andEnableEqualTo(true)
                        .andPurchaseOrderIdEqualTo(purchaseOrder.getId());
                List<PurchaseOrderItems> purchaseOrderItems = purchaseOrderItemsMapper.selectByExample(purchaseOrderItemsExample);
                if (!CollectionUtils.isEmpty(purchaseOrderItems)) {
                    for (PurchaseOrderItems purchaseOrderItem : purchaseOrderItems) {
                        if (purchaseOrderItem.getQuantity() != null) {
                            totalPlanFeedStatistics = totalPlanFeedStatistics.add((purchaseOrderItem.getQuantity()));
                        }
                    }
                }
            }
        }
        calculatePurchaseOrderMap.put(productType, totalPlanFeedStatistics);
        return calculatePurchaseOrderMap;
    }
}
