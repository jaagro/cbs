package com.jaagro.cbs.api.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author :gaoxin
 * @date :2019/04/03
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class DeviceValue implements Serializable {
    /**
     * 设备当前数值id
     */
    private Integer id;

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
     * 创建时间
     */
    private Date createTime;

    /**
     * 是否有效（1-有效 0 无效）
     */
    private Boolean enable;

    private static final long serialVersionUID = 1L;
}