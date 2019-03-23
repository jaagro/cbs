package com.jaagro.cbs.biz.schedule;

import com.jaagro.cbs.api.service.BatchCoopDailyService;
import com.jaagro.cbs.api.service.BatchInfoService;
import com.jaagro.cbs.api.service.BreedingRecordDailyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 批次养殖记录
 *
 * @author baiyiran
 * @Date 2019/2/26
 */
@Service
@Slf4j
public class BreedingRecordService {

    @Autowired
    private BatchInfoService batchInfoService;
    @Autowired
    private BatchCoopDailyService batchCoopDailyService;
    @Autowired
    private BreedingRecordDailyService breedingRecordDailyService;

    /**
     * 鸡舍养殖每日汇总
     */
    @Scheduled(cron = "0 30 23 1/1 * ?")
//    @Scheduled(cron = "0 * * * * ?")
    @Transactional(rollbackFor = Exception.class)
    public void batchCoopDaily() {
        log.info("batchCoopDaily:定时钟执行开始");
        try {
            batchCoopDailyService.batchCoopDaily();
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("鸡舍养殖每日汇总执行错误:" + ex);
        }

        log.info("batchCoopDaily:定时钟执行结束");
    }


    /**
     * 批次养殖记录表日汇总
     */
    @Scheduled(cron = "0 20 23 1/1 * ?")
//    @Scheduled(cron = "0 * * * * ?")
    @Transactional(rollbackFor = Exception.class)
    public void breedingRecordDaily() {
        log.info("breedingRecordDaily:定时钟执行开始");
        try {
            breedingRecordDailyService.breedingRecordDaily();
        } catch (Exception ex) {
            log.error("批次养殖记录表日汇总执行错误:" + ex);
        }
        log.info("breedingRecordDaily:定时钟执行结束");
    }

    /**
     * 批次养殖情况汇总
     */
    @Scheduled(cron = "0 10 23 1/1 * ?")
//    @Scheduled(cron = "0 * * * * ?")
    @Transactional(rollbackFor = Exception.class)
    public void batchInfo() {
        log.info("batchInfo:定时钟执行开始");
        try {
            batchInfoService.batchInfo();
        } catch (Exception ex) {
            log.error("批次养殖情况汇总执行错误:" + ex);
        }
        log.info("batchInfo:定时钟执行结束");
    }

}
