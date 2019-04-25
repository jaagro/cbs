package com.jaagro.cbs.api.dto.iot;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 设备
 *
 * @author :baiyiran
 * @date :2019/04/19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class DeviceIdDto implements Serializable {
    /**
     * 设备id
     */
    private int id;
    /**
     * 设备地址
     */
    private String address;
    /**
     * 报警数量
     */
    private int alarmCount;
    /**
     * 设备绑定模块的SN码
     */
    private String dtuSn;
    /**
     * 设备名称
     */
    private String equipName;
    /**
     * 纬度
     */
    private String latitude;
    /**
     * 经度
     */
    private String longitude;
    /**
     * 备注
     */
    private String remark;
    /**
     * 规则id
     */
    private int ruleId;
    /**
     * 状态：0-离线；1-在线
     */
    private String status;
    /**
     * 视频设备序列号
     */
    private String videoSerial;
    /**
     * 视频设备通道
     */
    private String videoTunnel;
}