package com.jaagro.cbs.web.vo.technicianapp;


import com.jaagro.cbs.api.dto.technicianapp.DeviceAlarmLogDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author gavin
 * @Date 20190408
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class DeviceAlarmLogVo extends DeviceAlarmLogDto implements Serializable {

    /**
     * 设备的最近一次报警值
     */
    private BigDecimal latestValue;


    /**
     * 设备的最近一次报警值时间
     */
    private Date latestAlarmDate;

    /**
     * 客户联系人名称
     */
    private String customerContactName;

    /**
     * 联系人电话
     */
    private String customerContactPhone;

}
