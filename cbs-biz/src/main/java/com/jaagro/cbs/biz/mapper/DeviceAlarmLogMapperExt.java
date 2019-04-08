package com.jaagro.cbs.biz.mapper;

import javax.annotation.Resource;

import com.jaagro.cbs.api.dto.techSiteApp.DeviceAlarmLogDto;
import com.jaagro.cbs.api.model.DeviceAlarmLog;
import com.jaagro.cbs.api.model.DeviceAlarmLogExample;
import com.jaagro.cbs.biz.mapper.base.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * DeviceAlarmLogMapperExt接口
 *
 * @author :generator
 * @date :2019/2/28
 */
@Resource
public interface DeviceAlarmLogMapperExt extends BaseMapper<DeviceAlarmLog, DeviceAlarmLogExample> {

    /**
     * 累计所有批次所产生的异常
     *
     * @param planIds
     * @return
     */
    BigDecimal accumulativeTotalAbnormalWarn(@Param("planIds") Set planIds);

    /**
     *
     * @param queryMap
     * @return
     */
    List<DeviceAlarmLogDto> listDeviceAlarmLogApp(Map<String,Integer> queryMap);
}