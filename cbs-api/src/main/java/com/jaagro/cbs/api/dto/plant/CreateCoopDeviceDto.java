package com.jaagro.cbs.api.dto.plant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 新增设备dto
 *
 * @author @Gao.
 * @Date 2019/2/23
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class CreateCoopDeviceDto implements Serializable {
    /**
     * 鸡舍id
     */
    private Integer coopId;

    /**
     * 养殖场id
     */
    private Integer plantId;

    /**
     * 回调url
     */
    private String apiUrl;

    /**
     * IOT设备唯一识别号(设备ID)
     */
    private String deviceCode;

    /**
     * 设备类型(1-温感设备,2-湿感设备)
     */
    private Integer deviceType;

    /**
     * 设备名称
     */
    private String deviceName;

    /**
     * 生产商
     */
    private String producer;

    /**
     * 设备型号（生产商家的型号）
     */
    private String modelNumber;

    /**
     * 设备状态（0-拆除，1-正常 2-故障）
     */
    private Integer coopDeviceStatus;

    /**
     * 当前值
     */
    private BigDecimal currentValue;
}
