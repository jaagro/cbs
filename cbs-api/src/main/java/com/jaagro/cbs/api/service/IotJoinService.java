package com.jaagro.cbs.api.service;

import com.jaagro.cbs.api.dto.iot.FanLongDeviceDataDto;

/**
 * @author tonyZheng
 * @date 2019-04-22 11:32
 */
public interface IotJoinService {

    /**
     * 取【梵龙】环控设备的授权令牌
     * 如果您只需要获取某个棚舍的授权令牌则loginType可以传入非1的任何参数或不传
     * 如果您需要获取root账号的授权令牌则loginType请传入1，也只能传入一个参数值为1，loginName和password可传入任意值
     *
     * @param loginName 用户名 需要【梵龙】开通
     * @param password 密码
     * @param loginType 登录类型：1-root账号登录 其他-普通账号登录
     * @return
     */
    String getTokenFromFanLong(String loginName, String password, Integer... loginType);

    /**
     * 获取【梵龙】设备列表
     * 如果您只需要获取某个棚舍的设备列表那么loginType则可以传入非1的任何参数或不传
     * 如果您需要获取root账号下的所有设备列表那么coopId传入任意int类型参数，loginType请传入1。也只能传入一个参数值为1
     *
     * @param loginType 登录类型：1-root账号登录 其他-普通账号登录
     * @param coopId    棚舍id
     * @return 当前养殖场下所有的环控设备列表（将获取的json直接存入map）
     */
    String getDeviceListFromFanLong(Integer coopId, Integer... loginType);

    /**
     * 获取单个【梵龙】设备当前数据
     * 从咱们自己数据库获取
     * @param deviceCode 设备id
     * @return dto
     */
    FanLongDeviceDataDto getCurrentDataByFanLongDeviceCode(String deviceCode);

    /**
     * 获取【梵龙】设备当前数据并且放入mq
     * 整个环控设备下所有传感器的当前数据，也是直接将json存入map
     */
    void createDeviceCurrentDataFromFanLong();
}
