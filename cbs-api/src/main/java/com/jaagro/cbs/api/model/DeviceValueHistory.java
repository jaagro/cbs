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
 * @date :2019/04/01
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class DeviceValueHistory implements Serializable {
    /**
     * 设备检测值历史表id
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

    private static final long serialVersionUID = 1L;
}