package com.jaagro.cbs.api.service;

import java.util.List;
import java.util.Map;

/**
 * @author tonyZheng
 * @date 2019-04-22 11:32
 */
public interface IotJoinService {

    /**
     * 获取【梵龙】环控设备的授权令牌
     *
     * @return
     */
    String getTokenFromFanLong();

    /**
     * 获取【梵龙】账号下所有我司设备接口
     *
     * @return 当前养殖场下所有的环控设备列表（将获取的json直接存入map）
     */
    List<Map<String, String>> getDeviceListFromFanLong();

    /**
     * 获取【梵龙】单台设备当前数据
     *
     * @param deviceCode 环控设备code
     * @return 整个环控设备下所有传感器的当前数据，也是直接将json存入map
     */
    Map<String, String> getDeviceCurrentDataByDeviceCodeFromFanLong(Integer deviceCode);
}
