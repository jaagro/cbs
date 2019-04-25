package com.jaagro.cbs.api.dto.iot;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author tonyZheng
 * @date 2019-04-24 14:14
 */
@Data
@Accessors(chain = true)
public class FanLongDeviceDataDto implements Serializable {

    private Integer id;
    /**
     * 数据名称
     */
    private String signalName;
    /**
     * 单位
     */
    private String unit;
    /**
     * 数据项当前值
     */
    private String value;
    /**
     * 寄存器地址
     */
    private Integer address;
}
