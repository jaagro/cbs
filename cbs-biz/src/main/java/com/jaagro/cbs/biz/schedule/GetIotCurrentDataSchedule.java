package com.jaagro.cbs.biz.schedule;

import com.jaagro.cbs.api.service.IotJoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author tonyZheng
 * @date 2019-04-23 14:03
 */
@Component
public class GetIotCurrentDataSchedule {

    @Autowired
    private IotJoinService iotJoinService;

//    @Scheduled(cron = "0 */5 * * * ?")
    public void getFanLongCurrentData() {
        iotJoinService.createDeviceCurrentDataFromFanLong();
    }
}
