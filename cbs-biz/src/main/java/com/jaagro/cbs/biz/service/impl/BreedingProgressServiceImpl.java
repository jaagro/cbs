package com.jaagro.cbs.biz.service.impl;

import com.fasterxml.jackson.databind.util.BeanUtil;
import com.jaagro.cbs.api.dto.base.CustomerContactsReturnDto;
import com.jaagro.cbs.api.dto.progress.*;
import com.jaagro.cbs.api.enums.*;
import com.jaagro.cbs.api.model.*;
import com.jaagro.cbs.api.service.BreedingBrainService;
import com.jaagro.cbs.api.service.BreedingPlanService;
import com.jaagro.cbs.api.service.BreedingProgressService;
import com.jaagro.cbs.biz.bo.FeedingFactoryBo;
import com.jaagro.cbs.biz.mapper.*;
import com.jaagro.cbs.biz.service.CustomerClientService;
import com.jaagro.cbs.biz.utils.DateUtil;
import com.netflix.discovery.converters.Auto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 养殖过程管理
 *
 * @author gavin
 * @date :2019/02/25
 */
@Slf4j
@Service
public class BreedingProgressServiceImpl implements BreedingProgressService {

    @Autowired
    private BreedingPlanMapperExt breedingPlanMapper;
    @Autowired
    private BatchPlantCoopMapperExt batchPlantCoopMapper;
    @Autowired
    private CoopMapperExt coopMapper;
    @Autowired
    private BreedingRecordMapperExt breedingRecordMapper;
    @Autowired
    private BreedingRecordItemsMapperExt breedingRecordItemsMapper;
    @Autowired
    private BreedingBatchParameterMapperExt breedingBatchParameterMapper;
    @Autowired
    private CoopDeviceMapperExt coopDeviceMapper;
    @Autowired
    private DeviceValueMapperExt deviceValueMapper;
    @Autowired
    private DeviceValueHistoryMapperExt deviceValueHistoryMapper;
    @Autowired
    private CustomerClientService customerClientService;
    @Autowired
    private PurchaseOrderMapperExt purchaseOrderMapper;
    @Autowired
    private BatchInfoMapperExt batchInfoMapper;
    @Autowired
    private BreedingBrainService breedingBrainService;
    @Autowired
    private BreedingPlanService breedingPlanService;
    @Autowired
    private ProductMapperExt productMapper;
    @Autowired
    private BatchCoopDailyMapperExt batchCoopDailyMapper;

    /**
     * 根据养殖计划Id、养殖厂Id获取养殖过程喂养头信息
     *
     * @param planId
     * @param plantId
     * @return
     */
    @Override
    public BreedingProgressDto getBreedingProgressById(Integer planId, Integer plantId) {
        BreedingPlan breedingPlan = breedingPlanMapper.selectByPrimaryKey(planId);
        Assert.notNull(breedingPlan, "养殖计划不存在");
        log.info("O BreedingProgressServiceImpl.getBreedingProgressById input planId:{},plantId:{}", planId, plantId);
        BreedingProgressDto breedingProgressDto = new BreedingProgressDto();
        try {
            BeanUtils.copyProperties(breedingPlan, breedingProgressDto);
            //该计划上报死掉的鸡
            BreedingRecordExample breedingRecordExample = new BreedingRecordExample();
            breedingRecordExample.createCriteria().andPlanIdEqualTo(planId).andRecordTypeEqualTo(BreedingRecordTypeEnum.DEATH_AMOUNT.getCode()).andEnableEqualTo(true);
            List<BreedingRecord> breedingRecordDos = breedingRecordMapper.selectByExample(breedingRecordExample);
            BigDecimal deadChickenCount = new BigDecimal(0.00);
            for (BreedingRecord breedingRecordDo : breedingRecordDos) {
                deadChickenCount = deadChickenCount.add(breedingRecordDo.getBreedingValue());
            }

            //存栏量：=breedingPlan.getPlanChickenQuantity()减去该养殖场死去的鸡数量
            BigDecimal planQuantity = new BigDecimal(breedingPlan.getPlanChickenQuantity());
            BigDecimal livingQuantity = planQuantity.subtract(deadChickenCount);
            breedingProgressDto.setLivingChickenQuantity(livingQuantity);

            //养殖计划的鸡舍信息
            BatchPlantCoopExample example = new BatchPlantCoopExample();
            example.createCriteria().andPlanIdEqualTo(planId).andPlantIdEqualTo(plantId).andEnableEqualTo(true);
            List<BatchPlantCoop> batchPlantCoopDos = batchPlantCoopMapper.selectByExample(example);
            if (!CollectionUtils.isEmpty(batchPlantCoopDos)) {
                List<Coop> coopDos = new ArrayList<>();
                for (BatchPlantCoop batchPlantCoop : batchPlantCoopDos) {
                    if (!StringUtils.isEmpty(batchPlantCoop.getCoopId())) {
                        Coop coopDo = coopMapper.selectByPrimaryKey(batchPlantCoop.getCoopId());
                        coopDos.add(coopDo);
                    }
                }
                breedingProgressDto.setCoops(coopDos);
            }

            CustomerContactsReturnDto customerDto = customerClientService.getCustomerContactByCustomerId(breedingPlan.getCustomerId());
            if (null != customerDto) {
                breedingProgressDto.setContactPhone(customerDto.getPhone());
                breedingProgressDto.setCustomerName(customerDto.getCustomerName());
                breedingProgressDto.setCustomerAddress(customerDto.getAddress());
            }
            List<Map<Integer, String>> progressDayAges = new ArrayList<>();
            for (int i = 0; i < breedingPlan.getBreedingDays(); i++) {
                Map<Integer, String> map = new HashMap<>();
                map.put(i + 1, DateUtil.accumulateDateByDay(breedingPlan.getPlanTime(), i));
                progressDayAges.add(map);
            }
            breedingProgressDto.setProgressDayAges(progressDayAges);
            //养殖计划剩余饲料总和
            BigDecimal totalLeftFood = this.getLeftFeedFoodByPlanId(planId);
            breedingProgressDto.setLeftFood(totalLeftFood);

            //获取当前日龄
            Long currentDayAgeLong = breedingPlanService.getDayAge(planId);
            //剩余饲料预计还可以使用的天数计算
            int leftUsageDays = 0;
            totalLeftFood = totalLeftFood.multiply(new BigDecimal("1000"));
            for (int k = currentDayAgeLong.intValue(); k < breedingPlan.getBreedingDays(); k++) {
                BigDecimal dayAgeAreaFeedWeight = breedingBrainService.getSumFoodWeightByPlanIdAndDayAgeArea(planId, currentDayAgeLong.intValue(), k);
                BigDecimal dayAgeAreaFeedTotalWeight = livingQuantity.multiply(dayAgeAreaFeedWeight);
                //单位：克
                BigDecimal LeftFoodWight = totalLeftFood.subtract(dayAgeAreaFeedTotalWeight);
                if (LeftFoodWight.compareTo(BigDecimal.ZERO) == -1) {
                    break;
                }
                leftUsageDays++;
            }
            breedingProgressDto.setUsageDays(leftUsageDays);
        } catch (Exception ex) {
            log.error("R BreedingProgressServiceImpl.getBreedingProgressById  error:" + ex);
        }

        return breedingProgressDto;
    }

    /**
     * 获取每天养殖参数详情
     *
     * @param planId
     * @param coopId
     * @param dayAge
     * @param strDate 2019-02-26
     * @return
     */
    @Override
    public List<BreedingBatchParamTrackingDto> getBreedingBatchParamById(Integer planId, Integer coopId, Integer dayAge, String strDate) {
        List<BreedingBatchParamTrackingDto> returnDtos = new ArrayList<>();
        log.info("O BreedingProgressServiceImpl.getBreedingBatchParamById input planId:{},coopId(),dayAge{},strDate{}", planId, coopId, dayAge, strDate);
        try {
            //养殖计划所用的参数
            BreedingBatchParameterExample batchParameterExample = new BreedingBatchParameterExample();
            batchParameterExample.createCriteria().andPlanIdEqualTo(planId).andDayAgeEqualTo(dayAge).andEnableEqualTo(true);
            batchParameterExample.setOrderByClause("display_order asc");
            List<BreedingBatchParameter> breedingBatchParameterDos = breedingBatchParameterMapper.selectByExample(batchParameterExample);

            if (!CollectionUtils.isEmpty(breedingBatchParameterDos)) {
                //鸡舍绑定的设备
                CoopDeviceExample coopDeviceExample = new CoopDeviceExample();
                coopDeviceExample.createCriteria().andCoopIdEqualTo(coopId).andCoopDeviceStatusEqualTo(DeviceStatusEnum.NORMAL.getCode()).andEnableEqualTo(true);
                List<CoopDevice> coopDeviceDos = coopDeviceMapper.selectByExample(coopDeviceExample);
                Set<Integer> deviceIds = new HashSet<>();
                if (!CollectionUtils.isEmpty(coopDeviceDos)) {
                    for (CoopDevice coopDeviceDo : coopDeviceDos) {
                        deviceIds.add(coopDeviceDo.getId());
                    }
                }
                SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date date = sDateFormat.parse(strDate);
                boolean isSameDay = DateUtils.isSameDay(date, new Date());
                List<DeviceValue> deviceValueDos = new ArrayList<>();
                List<DeviceValueHistory> deviceValueHistoryDos = new ArrayList<DeviceValueHistory>();
                if (isSameDay) {
                    //鸡舍绑定的设备采集上来的最新值
                    if (!CollectionUtils.isEmpty(deviceIds)) {
                        DeviceValueExample deviceValueExample = new DeviceValueExample();
                        deviceValueExample.createCriteria().andDeviceIdIn(new ArrayList<>(deviceIds));
                        deviceValueDos = deviceValueMapper.selectByExample(deviceValueExample);
                    }
                } else {
                    //鸡舍绑定的设备历史值
                    if (!CollectionUtils.isEmpty(deviceIds)) {
                        for (Integer deviceId : deviceIds) {
                            DeviceValueHistory deviceValueHistory = deviceValueHistoryMapper.getLimitOneRecordByDeviceId(deviceId, strDate);
                            deviceValueHistoryDos.add(deviceValueHistory);
                        }
                    }
                }
                BreedingRecordDto breedingRecordDto = getBreedingRecordsById(planId, coopId, dayAge);
                for (BreedingBatchParameter breedingBatchParameterDo : breedingBatchParameterDos) {
                    BreedingBatchParamTrackingDto returnDto = new BreedingBatchParamTrackingDto();
                    BeanUtils.copyProperties(breedingBatchParameterDo, returnDto);
                    if (null != breedingRecordDto && BreedingStandardParamEnum.DIE.getCode() == breedingBatchParameterDo.getParamType()) {
                        // 死淘率等于今日死淘除以今日起始数量
                        Integer deathTotal = breedingRecordDto.getDeathTotal();
                        if (deathTotal != null){
                            BatchCoopDailyExample batchCoopDailyExample = new BatchCoopDailyExample();
                            batchCoopDailyExample.createCriteria().andEnableEqualTo(Boolean.TRUE)
                                    .andCoopIdEqualTo(coopId).andPlanIdEqualTo(planId).andDayAgeEqualTo(dayAge);
                            batchCoopDailyExample.setOrderByClause("create_time desc");
                            batchCoopDailyExample.setLimit(1);
                            List<BatchCoopDaily> batchCoopDailyList = batchCoopDailyMapper.selectByExample(batchCoopDailyExample);
                            if (!batchCoopDailyList.isEmpty()){
                                Integer startAmount = batchCoopDailyList.get(0).getStartAmount();
                                returnDto.setActualValue(new BigDecimal(deathTotal).divide(new BigDecimal(startAmount),4, RoundingMode.HALF_UP).multiply(new BigDecimal("100")).setScale(2,BigDecimal.ROUND_HALF_UP).toPlainString());
                            }
                        }
                    }
                    if (null != breedingRecordDto && BreedingStandardParamEnum.FEEDING_WEIGHT.getCode() == breedingBatchParameterDo.getParamType()) {
                        returnDto.setActualValue(breedingRecordDto.getFeedFoodWeight().toString());
                        if (StringUtils.hasText(breedingRecordDto.getFoodUnit())) {
                            returnDto.setUnit(breedingRecordDto.getFoodUnit());
                        }
                    }
                    if (null != breedingRecordDto && BreedingStandardParamEnum.FEEDING_FODDER_NUM.getCode() == breedingBatchParameterDo.getParamType()) {
                        returnDto.setActualValue(breedingRecordDto.getFeedFoodTimes().toString());
                    }
                    if (BreedingStandardParamEnum.WEIGHT.getCode() == breedingBatchParameterDo.getParamType()) {
                        returnDto.setActualValue("--");
                    }
                    //养殖参数对应的设备列表
                    if (!CollectionUtils.isEmpty(coopDeviceDos)) {
                        List<CoopDevice> deviceDos = coopDeviceDos.stream().filter(c -> c.getDeviceType().equals(breedingBatchParameterDo.getParamType())).collect(Collectors.toList());
                        if (!CollectionUtils.isEmpty(deviceDos)) {
                            List<DeviceValueDto> actualResult = new ArrayList<>();
                            if (isSameDay) {
                                for (CoopDevice deviceDo : deviceDos) {
                                    if (!CollectionUtils.isEmpty(deviceValueDos)) {
                                        List<DeviceValue> deviceValues = deviceValueDos.stream().filter(c -> deviceDo.getId().equals(c.getDeviceId())).collect(Collectors.toList());
                                        if (!CollectionUtils.isEmpty(deviceValues)) {
                                            DeviceValueDto deviceValueDto = new DeviceValueDto();
                                            deviceValueDto.setDeviceId(deviceValues.get(0).getDeviceId());
                                            deviceValueDto.setCurrentValue(deviceValues.get(0).getCurrentValue());
                                            deviceValueDto.setValueType(deviceValues.get(0).getValueType());
                                            deviceValueDto.setUnit(breedingBatchParameterDo.getUnit());
                                            actualResult.add(deviceValueDto);
                                        }
                                    }
                                }
                            } else {
                                for (CoopDevice deviceDo : deviceDos) {
                                    if (!CollectionUtils.isEmpty(deviceValueHistoryDos)) {
                                        List<DeviceValueHistory> deviceValueHistorys = deviceValueHistoryDos.stream().filter(c -> deviceDo.getId().equals(c.getDeviceId())).collect(Collectors.toList());
                                        if (!CollectionUtils.isEmpty(deviceValueHistorys)) {
                                            DeviceValueDto deviceValueDto = new DeviceValueDto();
                                            BeanUtils.copyProperties(deviceValueHistorys.get(0), deviceValueDto);
                                            deviceValueDto.setUnit(breedingBatchParameterDo.getUnit());
                                            actualResult.add(deviceValueDto);
                                        }
                                    }
                                }
                            }
                            returnDto.setActualResult(actualResult);
                            //如果养殖参数的值类型是"区间值"
                            if (breedingBatchParameterDo.getValueType().equals(BreedingStandardValueTypeEnum.INTERVAL_VALUE.getCode())) {
                                if (!CollectionUtils.isEmpty(actualResult)) {
                                    for (DeviceValueDto deviceValue : actualResult) {
                                        //如果检测值不在标准区间里面则报警
                                        if (breedingBatchParameterDo.getLowerLimit().compareTo(deviceValue.getCurrentValue()) > 0 || deviceValue.getCurrentValue().compareTo(breedingBatchParameterDo.getUpperLimit()) > 0) {
                                            returnDto.setAlarmMessage("检测值超范围异常，其及时处理！");
                                            break;
                                        }
                                    }
                                }
                            } else if (breedingBatchParameterDo.getValueType().equals(BreedingStandardValueTypeEnum.CRITICAL_VALUE.getCode())) {
                                //如果养殖参数的值类型是"临界值"
                                if (!CollectionUtils.isEmpty(actualResult)) {
                                    for (DeviceValueDto deviceValue : actualResult) {
                                        if (breedingBatchParameterDo.getThresholdDirection().equals(ThresholdDirectionEnum.LESS_THAN.getCode())) {
                                            //如果检测值大于参数临界则报警
                                            BigDecimal paramValue = new BigDecimal(breedingBatchParameterDo.getParamValue());
                                            if (deviceValue.getCurrentValue().compareTo(paramValue) == 1) {
                                                returnDto.setAlarmMessage("检测值大于临界值异常，其及时处理！");
                                                break;
                                            }
                                        }

                                        if (breedingBatchParameterDo.getThresholdDirection().equals(ThresholdDirectionEnum.MORE_THAN.getCode())) {
                                            //如果检测值小于参数临界则报警
                                            BigDecimal paramValue = new BigDecimal(breedingBatchParameterDo.getParamValue());
                                            if (deviceValue.getCurrentValue().compareTo(paramValue) == -1) {
                                                returnDto.setAlarmMessage("检测值小于临界值异常，其及时处理！");
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    returnDtos.add(returnDto);
                }
            }
        } catch (Exception ex) {
            log.error("R BreedingProgressServiceImpl.getBreedingBatchParamById  error:" + ex);
        }
        return returnDtos;
    }

    /**
     * 获取每天养殖喂养情况
     *
     * @param planId
     * @param coopId
     * @param dayAge
     * @return
     */
    @Override
    public BreedingRecordDto getBreedingRecordsById(Integer planId, Integer coopId, Integer dayAge) {
        BreedingRecordDto breedingRecordDto = new BreedingRecordDto();
        log.info("O BreedingProgressServiceImpl.getBreedingBatchParamById input planId:{},coopId(),dayAge{}", planId, coopId, dayAge);
        try {
            //养殖计划的鸡舍在某日龄上的喂料记录
            FeedingFactoryBo feedingFoodBo = feedingRecordFactory(planId, coopId, dayAge, BreedingRecordTypeEnum.FEED_FOOD.getCode());
            breedingRecordDto.setFeedFoodList(feedingFoodBo.getBreedingList());
            breedingRecordDto.setFeedFoodTimes(feedingFoodBo.getFeedingTimes());
            breedingRecordDto.setFeedFoodWeight(feedingFoodBo.getFeedingWeight());
            breedingRecordDto.setFoodUnit(feedingFoodBo.getUnit());

            //养殖计划的鸡舍在某日龄上的喂水记录
            FeedingFactoryBo feedingWaterBo = feedingRecordFactory(planId, coopId, dayAge, BreedingRecordTypeEnum.FEED_WATER.getCode());
            breedingRecordDto.setFeedWaterList(feedingWaterBo.getBreedingList());
            breedingRecordDto.setFeedWaterTimes(feedingWaterBo.getFeedingTimes());
            breedingRecordDto.setWaterUnit(feedingWaterBo.getUnit());

            //养殖计划的鸡舍在某日龄上的喂药记录
            FeedingFactoryBo feedingMedicineBo = feedingRecordFactory(planId, coopId, dayAge, BreedingRecordTypeEnum.FEED_MEDICINE.getCode());
            breedingRecordDto.setFeedMedicineWeight(feedingMedicineBo.getFeedingWeight());
            breedingRecordDto.setFeedMedicineList(feedingMedicineBo.getBreedingList());
            breedingRecordDto.setFeedMedicineTimes(feedingMedicineBo.getFeedingTimes());
            breedingRecordDto.setMedicineUnit(feedingMedicineBo.getUnit());

            //养殖计划的鸡舍在某日龄上的死淘记录
            FeedingFactoryBo deathBo = feedingRecordFactory(planId, coopId, dayAge, BreedingRecordTypeEnum.DEATH_AMOUNT.getCode());
            List<com.jaagro.cbs.api.dto.farmer.BreedingRecordDto> deathAmountList = deathBo.getBreedingList();
            breedingRecordDto.setDeathList(deathAmountList);
            int deathTotal = 0;
            if (!CollectionUtils.isEmpty(deathAmountList)) {
                for (BreedingRecord breedingRecord : deathAmountList) {
                    deathTotal += breedingRecord.getBreedingValue().intValue();
                }
                breedingRecordDto.setDeathTotal(deathTotal);
            } else {
                breedingRecordDto.setDeathTotal(deathTotal);
            }
            breedingRecordDto.setDeathUnit(deathBo.getUnit());
            //养殖计划的鸡舍在某日龄上的应喂料总次数
            Integer shouldFeedFoodTimes = getShouldFeedTime(planId, dayAge, BreedingStandardParamEnum.FEEDING_FODDER_NUM.getCode());
            breedingRecordDto.setShouldFeedFoodTimes(shouldFeedFoodTimes);

            //养殖计划的鸡舍在某日龄上的应喂水总次数
            Integer shouldFeedWaterTimes = getShouldFeedTime(planId, dayAge, BreedingStandardParamEnum.FEEDING_WATER_NUM.getCode());
            breedingRecordDto.setShouldFeedWaterTimes(shouldFeedWaterTimes);
        } catch (Exception ex) {
            log.error("R BreedingProgressServiceImpl.getBreedingRecordsById error:" + ex);
        }
        return breedingRecordDto;
    }

    private Integer getShouldFeedTime(Integer planId, Integer dayAge, Integer paramType) {
        BreedingBatchParameterExample parameterExample = new BreedingBatchParameterExample();
        parameterExample.createCriteria().andPlanIdEqualTo(planId).andDayAgeEqualTo(dayAge).andParamTypeEqualTo(paramType).andEnableEqualTo(true);
        List<BreedingBatchParameter> batchParameterList = breedingBatchParameterMapper.selectByExample(parameterExample);
        if (!CollectionUtils.isEmpty(batchParameterList)) {
            String paramValue = batchParameterList.get(0).getParamValue();
            if (StringUtils.hasText(paramValue)) {
                return Integer.parseInt(paramValue);
            }
        }
        return null;
    }


    private FeedingFactoryBo feedingRecordFactory(Integer planId, Integer coopId, Integer dayAge, Integer feedingType) {
        BreedingRecordExample breedingRecordExample = new BreedingRecordExample();
        BreedingRecordExample.Criteria criteria = breedingRecordExample.createCriteria();
        criteria.andPlanIdEqualTo(planId).andEnableEqualTo(true);
        if (null != coopId) {
            criteria.andCoopIdEqualTo(coopId);
        }
        if (null != dayAge) {
            criteria.andDayAgeEqualTo(dayAge);
        }
        if (null != feedingType) {
            criteria.andRecordTypeEqualTo(feedingType);
        }

        List<BreedingRecord> breedingList = breedingRecordMapper.selectByExample(breedingRecordExample);
        List<com.jaagro.cbs.api.dto.farmer.BreedingRecordDto> breedingRecordDtoList = new ArrayList<>();
        int feedingTimes = breedingList.size();
        BigDecimal feedingWeight = new BigDecimal(0.00);
        String feedUnit = "";
        if (!CollectionUtils.isEmpty(breedingList)) {
            for (BreedingRecord breedingRecordDo : breedingList) {
                com.jaagro.cbs.api.dto.farmer.BreedingRecordDto breedingRecordDto = new com.jaagro.cbs.api.dto.farmer.BreedingRecordDto();
                BeanUtils.copyProperties(breedingRecordDo, breedingRecordDto);
                //某个日龄喂养药品数量和单位
                BreedingRecordItemsExample itemsExample = new BreedingRecordItemsExample();
                itemsExample.createCriteria().andEnableEqualTo(true).andBreedingRecordIdEqualTo(breedingRecordDo.getId());
                itemsExample.setOrderByClause("breeding_time");
                List<BreedingRecordItems> items = breedingRecordItemsMapper.selectByExample(itemsExample);
                List<BreedingRecordItemsDto> itemDtoList = new ArrayList<>();
                if (!CollectionUtils.isEmpty(items)) {
                    for (BreedingRecordItems item : items) {
                        BreedingRecordItemsDto itemsDto = new BreedingRecordItemsDto();
                        BeanUtils.copyProperties(item, itemsDto);
                        Product product = productMapper.selectByPrimaryKey(item.getProductId());
                        if (null != product) {
                            itemsDto.setCapacityUnit(product.getCapacityUnit() == null ? "" : CapacityUnitEnum.getTypeByCode(product.getCapacityUnit()));
                            itemsDto.setProductName(product.getProductName());
                        }

                        itemDtoList.add(itemsDto);
                    }
                }

                breedingRecordDto.setBreedingRecordItemsList(itemDtoList);

                breedingRecordDtoList.add(breedingRecordDto);

                //计算某个日龄喂养饲料的总和
                feedingWeight = feedingWeight.add(breedingRecordDo.getBreedingValue());
                feedUnit = breedingRecordDo.getUnit();
            }
        }
        FeedingFactoryBo feedingFactoryBo = new FeedingFactoryBo();
        feedingFactoryBo
                .setBreedingList(breedingRecordDtoList)
                .setFeedingTimes(feedingTimes)
                .setUnit(feedUnit)
                .setFeedingWeight(feedingWeight);

        return feedingFactoryBo;
    }

    /**
     * 计算养殖计划剩余饲料 单位：千克
     *
     * @param planId
     * @return
     */
    private BigDecimal getLeftFeedFoodByPlanId(int planId) {
        //获取已签收的饲料总和 千克
        BigDecimal totalSignedFood = purchaseOrderMapper.getTotalSignedFoodByPlanId(planId);
        if (null == totalSignedFood) {
            totalSignedFood = BigDecimal.ZERO;
        }
        //已喂养饲料总和 千克
        BigDecimal totalFedFood = batchInfoMapper.accumulativeFeed(planId);
        if (null == totalFedFood) {
            totalFedFood = BigDecimal.ZERO;
        }

        if (totalFedFood.compareTo(BigDecimal.ZERO) == 0) {
            FeedingFactoryBo feedingFoodBo = feedingRecordFactory(planId, null, null, BreedingRecordTypeEnum.FEED_FOOD.getCode());
            totalFedFood = feedingFoodBo.getFeedingWeight();
        }
        //剩余饲料 = 已签收的饲料总和 - 已喂养饲料总和
        BigDecimal leftFeedFood = totalSignedFood.subtract(totalFedFood);
        if (null == leftFeedFood) {
            leftFeedFood = BigDecimal.ZERO;
        }
        return leftFeedFood;

    }

}
