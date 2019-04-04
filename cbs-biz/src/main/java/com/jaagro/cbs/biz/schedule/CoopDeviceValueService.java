package com.jaagro.cbs.biz.schedule;

import com.jaagro.cbs.api.model.*;
import com.jaagro.cbs.biz.mapper.*;
import com.jaagro.cbs.biz.utils.RedisLock;
import com.jaagro.cbs.biz.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;

/**
 * 设备检测值
 *
 * @author baiyiran
 * @Date 2019/2/28
 */
@Service
@Slf4j
public class CoopDeviceValueService {

    @Autowired
    private CoopDeviceMapperExt coopDeviceMapper;
    @Autowired
    private DeviceValueMapperExt deviceValueMapper;
    @Autowired
    private DeviceAlarmLogMapperExt alarmLogMapper;
    @Autowired
    private BatchPlantCoopMapperExt batchPlantCoopMapper;
    @Autowired
    private BreedingBatchParameterMapperExt batchParameterMapper;
    @Autowired
    private DeviceValueHistoryMapperExt deviceValueHistoryMapper;
    @Autowired
    private RedisLock redisLock;
    @Autowired
    private RedisUtil redis;

    /**
     * 批次养殖情况汇总
     */
//    @Scheduled(cron = "0 10 23 1/1 * ?")
//    @Scheduled(cron = "0 * * * * ?")
    @Transactional(rollbackFor = Exception.class)
    public void coopDeviceValue() {
        log.info("coopDeviceValue:定时钟执行开始");
        //加锁
        long time = System.currentTimeMillis() + 10 * 1000;
        boolean success = redisLock.lock("Scheduled:redisLock:coopDeviceValue", String.valueOf(time), null, null);
        if (!success) {
            throw new RuntimeException("请求正在处理中");
        }

        //从breedingRecord统计
        List<DeviceValue> historyList = deviceValueHistoryMapper.listHistoryByParams();
        if (!CollectionUtils.isEmpty(historyList)) {
            for (DeviceValue history : historyList) {
                //直接先删掉表的的原纪录
                deviceValueMapper.deleteByValue(history);
                //检测是否需要警报
                deviceAlarm(history);
            }
            //批量插入
            deviceValueMapper.insertBatch(historyList);

            redisLock.unLock("Scheduled:redisLock:coopDeviceValue");
        }
        log.info("coopDeviceValue:定时钟执行结束");
    }

    /**
     * 检测设备报警
     *
     * @param history
     */
    private void deviceAlarm(DeviceValue history) {
        CoopDevice coopDevice = coopDeviceMapper.getCoopIdByDeviceId(history.getDeviceId());
        if (coopDevice != null) {
            Integer planId = batchPlantCoopMapper.getPlanIdByCoopId(coopDevice.getCoopId());
            if (planId != null) {
                BreedingBatchParameterExample parameterExample = new BreedingBatchParameterExample();
                parameterExample.createCriteria()
                        .andEnableEqualTo(true)
                        .andPlanIdEqualTo(planId);
                List<BreedingBatchParameter> parameterList = batchParameterMapper.selectByExample(parameterExample);
                if (!CollectionUtils.isEmpty(parameterList)) {
                    BreedingBatchParameter parameter = new BreedingBatchParameter();
                    BigDecimal currentValue = history.getCurrentValue();
                    if (parameter.getAlarm().equals(1)) {
                        if (currentValue.compareTo(parameter.getLowerLimit()) == -1 || currentValue.compareTo(parameter.getUpperLimit()) == 1) {
                            StringBuilder sb = new StringBuilder();
                            sb.append(parameter.getLowerLimit()).append("-").append(parameter.getUpperLimit());
                            DeviceAlarmLog alarmLog = new DeviceAlarmLog();
                            alarmLog
                                    .setCoopId(coopDevice.getCoopId())
                                    .setCurrentValue(currentValue)
                                    .setDayAge(parameter.getDayAge())
                                    .setDeviceId(history.getDeviceId())
                                    .setPlanId(parameter.getPlanId())
                                    .setPlantId(coopDevice.getPlantId())
                                    .setParamStandardValue(sb.toString());
                            alarmLogMapper.insertSelective(alarmLog);
                        }
                        /*switch (parameter.getValueType()) {
                            //区间值
                            case 1:
                                if (currentValue.compareTo(parameter.getLowerLimit()) == -1 || currentValue.compareTo(parameter.getUpperLimit()) == 1) {
                                    StringBuilder sb = new StringBuilder();
                                    sb.append(parameter.getLowerLimit()).append("-").append(parameter.getUpperLimit());
                                    alarmLog.setParamStandardValue(sb.toString());
                                    flag = true;
                                }
                                break;
                            //标准值
                            case 2:
                                if (currentValue.compareTo(parameter.getLowerLimit()) != 0) {
                                    alarmLog.setParamStandardValue(parameter.getLowerLimit().toString());
                                    flag = true;
                                }
                                break;
                            //临界值
                            default:
                                if (currentValue.compareTo(parameter.getUpperLimit()) != 0) {
                                    alarmLog.setParamStandardValue(parameter.getUpperLimit().toString());
                                    flag = true;
                                }
                                break;
                        }*/
                    }
                }
            }
        }
    }

}
