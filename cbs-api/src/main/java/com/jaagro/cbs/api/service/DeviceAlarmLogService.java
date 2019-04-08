package com.jaagro.cbs.api.service;


import com.github.pagehelper.PageInfo;
import com.jaagro.cbs.api.dto.technicianapp.AlarmLogDetailDto;
import com.jaagro.cbs.api.dto.technicianapp.DeviceAlarmLogDto;
import com.jaagro.cbs.api.dto.technicianapp.ToDoAlarmParam;
import com.jaagro.cbs.api.dto.technicianapp.UpdateDeviceAlarmLogDto;
import com.jaagro.cbs.api.model.DeviceAlarmLog;

import java.util.List;


/**
 * 设备报警管理
 * @Date 20190408
 * @author @gavin
 */
public interface DeviceAlarmLogService {


    /**
     * 获取技术端app报警列表
     * @param dto
     * @return
     */
    PageInfo listDeviceAlarmLogApp(ToDoAlarmParam dto);



    boolean handleDeviceAlarmLogRecord(UpdateDeviceAlarmLogDto updateDto);

    /**
     * 获取某个计划某个养殖场某个鸡舍某个日龄某个设备的报警详细信息
     * @param queryDto
     * @return
     */
    AlarmLogDetailDto getDeviceAlarmLogDetail(UpdateDeviceAlarmLogDto queryDto);

    /**
     * 获取某个计划某个养殖场某个鸡舍某个日龄某个设备的报警
     * @param updateDto
     * @return
     */
    List<DeviceAlarmLog> getLatestDeviceAlarmLog(UpdateDeviceAlarmLogDto updateDto);



}
