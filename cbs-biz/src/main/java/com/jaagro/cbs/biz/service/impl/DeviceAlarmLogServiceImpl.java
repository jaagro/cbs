package com.jaagro.cbs.biz.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jaagro.cbs.api.dto.base.CustomerContactsReturnDto;
import com.jaagro.cbs.api.dto.technicianapp.AlarmLogDetailDto;
import com.jaagro.cbs.api.dto.technicianapp.DeviceAlarmLogDto;
import com.jaagro.cbs.api.dto.technicianapp.ToDoAlarmParam;
import com.jaagro.cbs.api.dto.technicianapp.UpdateDeviceAlarmLogDto;
import com.jaagro.cbs.api.enums.AlarmLogHandleTypeEnum;
import com.jaagro.cbs.api.enums.TechConsultStatusEnum;
import com.jaagro.cbs.api.model.*;
import com.jaagro.cbs.api.service.DeviceAlarmLogService;
import com.jaagro.cbs.biz.mapper.BatchCoopDailyMapperExt;
import com.jaagro.cbs.biz.mapper.BreedingPlanMapperExt;
import com.jaagro.cbs.biz.mapper.DeviceAlarmLogMapperExt;
import com.jaagro.cbs.biz.service.CustomerClientService;
import com.jaagro.constant.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gavin
 * @date 2019/04/08
 */
@Service
@Slf4j
public class DeviceAlarmLogServiceImpl implements DeviceAlarmLogService {
    @Autowired
    private CurrentUserService currentUserService;

    @Autowired
    private DeviceAlarmLogMapperExt deviceAlarmLogMapperExt;
    @Autowired
    private BreedingPlanMapperExt breedingPlanMapperExt;
    @Autowired
    private BatchCoopDailyMapperExt batchCoopDailyMapperExt;
    @Autowired
    private CustomerClientService customerClientService;



    /**
     * 获取技术员app报警列表
     *
     * @param dto
     * @return
     */
    @Override
    public PageInfo listDeviceAlarmLogApp(ToDoAlarmParam dto) {
        PageHelper.startPage(dto.getPageNum(), dto.getPageSize());
        Map<String, Integer> queryParam = new HashMap<>(10);
        queryParam.put("technicianId", dto.getTechnicianId());
        List<DeviceAlarmLogDto> DeviceAlarmLogDtoList = deviceAlarmLogMapperExt.listDeviceAlarmLogApp(queryParam);

        return new PageInfo(DeviceAlarmLogDtoList);
    }

    /**
     * 处理某个计划某个养殖场某个鸡舍某个日龄某个设备的报警
     *
     * @param updateDto
     * @return
     */
    @Override
    public boolean handleDeviceAlarmLogRecord(UpdateDeviceAlarmLogDto updateDto) {
        try {
            log.info("O DeviceAlarmLogServiceImpl.handleDeviceAlarmLogRecord input updateDto:{}", updateDto);
            UserInfo currentUser = currentUserService.getCurrentUser();
            DeviceAlarmLog deviceAlarmLog = new DeviceAlarmLog();

            deviceAlarmLog.setHandleType(updateDto.getHandleType());
            deviceAlarmLog.setHandleDesc(updateDto.getHandleDesc());
            deviceAlarmLog.setHandleTime(new Date());
            deviceAlarmLog.setHandleStatus(TechConsultStatusEnum.STATUS_SOLVED.getCode());
            deviceAlarmLog.setHandleUserId(currentUser != null ? currentUser.getId() : null);
            deviceAlarmLogMapperExt.updateByPrimaryKeySelective(deviceAlarmLog);

        } catch (Exception e) {
            log.error("R DeviceAlarmLogServiceImpl.handleDeviceAlarmLogRecord error:" + e);
            return false;
        }
        return true;
    }

    /**
     * 获取某个计划某个养殖场某个鸡舍某个日龄某个设备的报警详细信息
     *
     * @param queryDto
     * @return
     */
    @Override
    public AlarmLogDetailDto getDeviceAlarmLogDetail(UpdateDeviceAlarmLogDto queryDto) {
        AlarmLogDetailDto alarmLogDetailDto = new AlarmLogDetailDto();
        Integer technicianId = this.getCurrentUserId();
        Map<String, Integer> queryParam = new HashMap<>(10);
        queryParam.put("technicianId", 3116);
        queryParam.put("planId", queryDto.getPlanId());
        queryParam.put("plantId", queryDto.getPlantId());
        queryParam.put("coopId", queryDto.getCoopId());
        queryParam.put("deviceId", queryDto.getDeviceId());
        queryParam.put("dayAge", queryDto.getDayAge());

        List<DeviceAlarmLogDto> deviceAlarmLogDtoList = deviceAlarmLogMapperExt.listDeviceAlarmLogApp(queryParam);
        if (!CollectionUtils.isEmpty(deviceAlarmLogDtoList)) {
            DeviceAlarmLogDto deviceAlarmLogDto = deviceAlarmLogDtoList.get(0);
            BeanUtils.copyProperties(deviceAlarmLogDto, alarmLogDetailDto);
            //鸡舍存栏量
            int livingAmount = 0;
            BatchCoopDailyExample example = new BatchCoopDailyExample();
            example.createCriteria().andPlanIdEqualTo(queryDto.getPlanId()).andCoopIdEqualTo(queryDto.getCoopId()).andDayAgeEqualTo(queryDto.getDayAge()).andEnableEqualTo(true);
            List<BatchCoopDaily> batchCoopDailyList = batchCoopDailyMapperExt.selectByExample(example);
            if (!CollectionUtils.isEmpty(batchCoopDailyList)) {
                livingAmount = batchCoopDailyList.get(0).getCurrentAmount();
            }
            alarmLogDetailDto.setLivingAmount(livingAmount);

            //客户、客户联系人
            CustomerContactsReturnDto customerDto = customerClientService.getCustomerContactByCustomerId(deviceAlarmLogDto.getCustomerId());
            if (null != customerDto) {
                alarmLogDetailDto.setCustomerContactPhone(customerDto.getPhone());
                alarmLogDetailDto.setCustomerName(customerDto.getCustomerName());
                alarmLogDetailDto.setCustomerContactName(customerDto.getContact());
            }
            //最近一次报警信息
            UpdateDeviceAlarmLogDto alarmLogDto = new UpdateDeviceAlarmLogDto();
            alarmLogDto.setPlanId(deviceAlarmLogDto.getPlanId())
                    .setPlantId(deviceAlarmLogDto.getPlantId())
                    .setCoopId(deviceAlarmLogDto.getCoopId())
                    .setDayAge(deviceAlarmLogDto.getDayAge())
                    .setDeviceId(deviceAlarmLogDto.getDeviceId());
            List<DeviceAlarmLog> alarmLogs = this.getLatestDeviceAlarmLog(alarmLogDto);
            if (!org.apache.commons.collections.CollectionUtils.isEmpty(alarmLogs)) {
                DeviceAlarmLog deviceAlarmLog = alarmLogs.get(0);
                alarmLogDetailDto.setLatestValue(deviceAlarmLog.getCurrentValue());
                alarmLogDetailDto.setLatestAlarmDate(deviceAlarmLog.getCreateTime());
                alarmLogDetailDto.setHandleTypeStr(AlarmLogHandleTypeEnum.getTypeByCode(deviceAlarmLog.getHandleType()));
                alarmLogDetailDto.setHandleDesc(deviceAlarmLog.getHandleDesc());
            }
        }
        return alarmLogDetailDto;
    }

    /**
     * 获取某个计划某个养殖场某个鸡舍某个日龄某个设备的最细报警记录
     *
     * @param queryDto
     * @return
     */
    @Override
    public List<DeviceAlarmLog> getLatestDeviceAlarmLog(UpdateDeviceAlarmLogDto queryDto) {
        DeviceAlarmLogExample example = new DeviceAlarmLogExample();
        example.createCriteria().andPlanIdEqualTo(queryDto.getPlanId())
                .andPlantIdEqualTo(queryDto.getPlantId())
                .andCoopIdEqualTo(queryDto.getCoopId())
                .andDayAgeEqualTo(queryDto.getDayAge())
                .andDeviceIdEqualTo(queryDto.getDeviceId());
        example.setOrderByClause("create_time desc");

        return deviceAlarmLogMapperExt.selectByExample(example);
    }

    private Integer getCurrentUserId() {
        UserInfo userInfo = currentUserService.getCurrentUser();
        return userInfo == null ? null : userInfo.getId();
    }

}