package com.jaagro.cbs.api.dto.progress;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author :Gavin
 * @date :2019/02/26
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class DeviceValueDto implements Serializable {

    private static final long serialVersionUID = -7533894644856917703L;
    /**
     * 设备id
     */
    private Integer deviceId;

    /**
     * 数值类型(1-温度,2-湿度)
     */
    private Integer valueType;

    /**
     * 当前值
     */
    private BigDecimal currentValue;

    /**
     * 设备的计量单位
     */
    private String unit;
}
