package com.jaagro.cbs.biz.bo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author tonyZheng
 * @date 2019-04-23 17:41
 */
@Data
@Accessors(chain = true)
public class FanLongIotDeviceBo implements Serializable {

    private Integer id;
    private String equipName;
    private String address;
    private Integer alarmCount;
    private String dtuSn;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private Integer ruleId;
    private Integer status;
    private String videoSerial;
    private String remark;
}
