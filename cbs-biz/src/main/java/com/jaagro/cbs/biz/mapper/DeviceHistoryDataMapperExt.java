package com.jaagro.cbs.biz.mapper;

import javax.annotation.Resource;

import com.jaagro.cbs.api.model.DeviceHistoryData;
import com.jaagro.cbs.api.model.DeviceHistoryDataExample;
import com.jaagro.cbs.biz.mapper.base.BaseMapper;


/**
 * DeviceHistoryDataMapperExt接口
 *
 * @author :generator
 * @date :2019/4/22
 */
@Resource
public interface DeviceHistoryDataMapperExt extends BaseMapper<DeviceHistoryData, DeviceHistoryDataExample> {

    /**
     * 根据设备id获取该设备当前数据
     * 临时方法，现阶段数据存入mysql 后期迁移到hBase后需要做迁移
     *
     * @param deviceCode
     * @return
     */
    DeviceHistoryData getDeviceCurrentDataByDeviceCode(String deviceCode);

}