package com.jaagro.cbs.api.service;

import java.util.List;
import java.util.Map;

/**
 * @author tonyZheng
 * @date 2019-04-22 11:32
 */
public interface IotJoinService {

    /**
     * 取【梵龙】环控设备的授权令牌
     *
     * @param loginName
     * @param password
     * @param loginType 登录类型：1-root账号登录 其他-普通账号登录
     * @return
     */
    String getTokenFromFanLong(String loginName, String password, Integer... loginType);

    /**
     * 获取【梵龙】账号下所有我司设备接口
     *
     * @param loginType 登录类型：1-root账号登录 2-普通账号登录
     * @param coopId
     * @return 当前养殖场下所有的环控设备列表（将获取的json直接存入map）
     */
    String getDeviceListFromFanLong(Integer coopId, Integer... loginType);

    /**
     * 获取【梵龙】设备当前数据
     *
     * @return 整个环控设备下所有传感器的当前数据，也是直接将json存入map
     */
    void createDeviceCurrentDataFromFanLong();
}
