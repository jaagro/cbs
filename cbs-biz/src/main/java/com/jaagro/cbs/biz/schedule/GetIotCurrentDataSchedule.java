package com.jaagro.cbs.biz.schedule;

import com.jaagro.cbs.api.service.IotJoinService;
import com.jaagro.cbs.biz.utils.RedisLock;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author tonyZheng
 * @date 2019-04-23 14:03
 */
@Log4j
@Component
public class GetIotCurrentDataSchedule {

    @Autowired
    private IotJoinService iotJoinService;
    @Autowired
    private RedisLock redisLock;

//    @Scheduled(cron = "0 */5 * * * ?")
    public void getFanLongCurrentData() {
        log.info("GetIotCurrentDataSchedule 定时钟开始执行");
        long time = System.currentTimeMillis() + 10 * 1000;
        boolean isLock = redisLock.lock("Scheduled:redisLock:getFanLongCurrentData", String.valueOf(time), 5, TimeUnit.MINUTES);
        if (!isLock) {
            return;
        }
        iotJoinService.createDeviceCurrentDataFromFanLong();
        redisLock.unLock("Scheduled:redisLock:getFanLongCurrentData");
        log.info("GetIotCurrentDataSchedule 定时钟执行结束");
    }
}
