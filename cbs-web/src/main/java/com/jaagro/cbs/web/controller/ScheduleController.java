package com.jaagro.cbs.web.controller;

import com.jaagro.cbs.api.dto.base.BatchInfoCriteriaDto;
import com.jaagro.cbs.api.service.BatchCoopDailyService;
import com.jaagro.cbs.api.service.BatchInfoService;
import com.jaagro.cbs.api.service.BreedingRecordDailyService;
import com.jaagro.cbs.biz.schedule.BreedingPlanScheduleService;
import com.jaagro.utils.BaseResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 定时钟管理
 *
 * @author baiyiran
 * @date 2019/3/16
 */
@RestController
@Slf4j
@Api(description = "定时钟管理", produces = MediaType.APPLICATION_JSON_VALUE)
public class ScheduleController {

    @Autowired
    private BatchInfoService batchInfoService;
    @Autowired
    private BatchCoopDailyService batchCoopDailyService;
    @Autowired
    private BreedingRecordDailyService breedingRecordDailyService;
    @Autowired
    private BreedingPlanScheduleService breedingPlanScheduleService;

    @ApiOperation("鸡舍养殖每日汇总")
    @PostMapping("/batchCoopDaily")
    public BaseResponse batchCoopDaily() {
        batchCoopDailyService.batchCoopDaily();
        return BaseResponse.successInstance("");
    }

    @ApiOperation("批次养殖记录表日汇总")
    @PostMapping("/breedingRecordDaily")
    public BaseResponse breedingRecordDaily(@RequestBody BatchInfoCriteriaDto criteriaDto) {
        breedingRecordDailyService.breedingRecordDaily(criteriaDto);
        return BaseResponse.successInstance("");
    }

    @ApiOperation("批次养殖情况汇总")
    @PostMapping("/batchInfo")
    public BaseResponse batchInfo(@RequestBody BatchInfoCriteriaDto criteriaDto) {
        batchInfoService.batchInfo(criteriaDto);
        return BaseResponse.successInstance("");
    }

    @ApiOperation("养殖计划状态变更")
    @PostMapping("/breedingPlanProcess")
    public BaseResponse breedingPlanProcess() {
        breedingPlanScheduleService.breedingPlanProcess();
        return BaseResponse.successInstance("定时触发成功");
    }
}
