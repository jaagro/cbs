package com.jaagro.cbs.web.controller;

import com.github.pagehelper.PageInfo;
import com.jaagro.cbs.api.dto.base.CustomerContactsReturnDto;
import com.jaagro.cbs.api.dto.techSiteApp.DeviceAlarmLogDto;
import com.jaagro.cbs.api.dto.techSiteApp.ToDoAlarmParam;
import com.jaagro.cbs.api.dto.techSiteApp.UpdateDeviceAlarmLogDto;
import com.jaagro.cbs.api.dto.techconsult.TechConsultParamDto;
import com.jaagro.cbs.api.enums.EmergencyLevelEnum;
import com.jaagro.cbs.api.enums.TechConsultStatusEnum;
import com.jaagro.cbs.api.model.BreedingPlan;
import com.jaagro.cbs.api.model.DeviceAlarmLog;
import com.jaagro.cbs.api.model.TechConsultRecord;
import com.jaagro.cbs.api.service.BreedingPlanService;
import com.jaagro.cbs.api.service.DeviceAlarmLogService;
import com.jaagro.cbs.api.service.TechConsultService;
import com.jaagro.cbs.biz.service.CustomerClientService;
import com.jaagro.cbs.web.vo.techApp.DeviceAlarmLogVo;
import com.jaagro.cbs.web.vo.techconsult.TechConsultVo;
import com.jaagro.utils.BaseResponse;
import com.jaagro.utils.ResponseStatusCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 技术APP-待办：报警信息
 * @Date 20190403
 * @author gavin
 */
@RestController
@Slf4j
@Api(description = "技术APP-待办报警信息", produces = MediaType.APPLICATION_JSON_VALUE)
public class DeviceAlarmController {

    @Autowired
    private BreedingPlanService breedingPlanService;
    @Autowired
    private DeviceAlarmLogService deviceAlarmLogService;
    @Autowired
    private CustomerClientService customerClientService;
    /**
     * 获取待办报警信息列表
     *
     * @author gavin
     * @date 2019/04/03
     */
    @ApiOperation("待办报警信息列表-技术员APP")
    @PostMapping("/listDeviceAlarmLogApp")
    public BaseResponse<PageInfo> listDeviceAlarmLogApp(@RequestBody ToDoAlarmParam criteriaDto) {

        if (StringUtils.isEmpty(criteriaDto.getPageNum())) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "pageNum不能为空");
        }
        if (StringUtils.isEmpty(criteriaDto.getPageSize())) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "pageSize不能为空");
        }
        if (StringUtils.isEmpty(criteriaDto.getTechnicianId())) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "技术员ID不能为空");
        }
        PageInfo pageInfo = deviceAlarmLogService.listDeviceAlarmLogApp(criteriaDto);
        if (pageInfo != null) {
            List<DeviceAlarmLogVo> voList = new ArrayList<>();
            List<DeviceAlarmLogDto> doList = pageInfo.getList();
            if (!CollectionUtils.isEmpty(doList)) {
                for (DeviceAlarmLogDto deviceAlarmLogDto : doList) {
                    DeviceAlarmLogVo resultVo = new DeviceAlarmLogVo();
                    BeanUtils.copyProperties(deviceAlarmLogDto, resultVo);
                    //客户、客户联系人
                    CustomerContactsReturnDto customerDto = customerClientService.getCustomerContactByCustomerId(deviceAlarmLogDto.getCustomerId());
                    if (null != customerDto) {
                        resultVo.setCustomerContactPhone(customerDto.getPhone());
                        resultVo.setCustomerName(customerDto.getCustomerName());
                        resultVo.setCustomerContactName(customerDto.getContact());
                    }
                    //最近一次报警信息
                    UpdateDeviceAlarmLogDto queryDto = new UpdateDeviceAlarmLogDto();
                    queryDto.setPlanId(deviceAlarmLogDto.getPlanId())
                            .setPlantId(deviceAlarmLogDto.getPlantId())
                            .setCoopId(deviceAlarmLogDto.getCoopId())
                            .setDayAge(deviceAlarmLogDto.getDayAge())
                            .setDeviceId(deviceAlarmLogDto.getDeviceId());
                    List<DeviceAlarmLog> alarmLogs= deviceAlarmLogService.getLatestDeviceAlarmLog(queryDto);
                    if(!CollectionUtils.isEmpty(alarmLogs)){
                        resultVo.setLatestValue(alarmLogs.get(0).getCurrentValue());
                        resultVo.setLatestAlarmDate(alarmLogs.get(0).getCreateTime());
                    }

                    voList.add(resultVo);
                }
            }
            pageInfo.setList(voList);
        }
        return BaseResponse.successInstance(pageInfo);
    }
}
