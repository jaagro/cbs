package com.jaagro.cbs.api.service;


import com.jaagro.cbs.api.dto.plant.CreateCoopDeviceDto;
import com.jaagro.cbs.api.dto.plant.ReturnCoopDeviceDto;
import com.jaagro.cbs.api.model.CoopDevice;

import java.util.List;
import java.util.Map;

/**
 * 养殖鸡舍设备服务相关api
 *
 * @author baiyiran
 */
public interface BreedingCoopDeviceService {

    /**
     * 新增鸡舍设备
     *
     * @param dto
     */
    void bindDeviceToCoop(CreateCoopDeviceDto dto);

    /**
     * 根据养殖场id 查询鸡舍与设备信息
     *
     * @param plantId
     * @return
     */
    List<ReturnCoopDeviceDto> listBreedingCoopDevice(Integer plantId);

    /**
     * 根据鸡舍id查询设备列表
     *
     * @param coopId
     * @return
     */
    List<CoopDevice> listCoopDeviceByCoopId(Integer coopId);

    List<Map<String, String>> listDeviceIdList(Integer coopId);
}
