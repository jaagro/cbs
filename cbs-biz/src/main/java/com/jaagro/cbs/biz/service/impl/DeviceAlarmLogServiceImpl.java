package com.jaagro.cbs.biz.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jaagro.cbs.api.dto.techSiteApp.DeviceAlarmLogDto;
import com.jaagro.cbs.api.dto.techSiteApp.ToDoAlarmParam;
import com.jaagro.cbs.api.dto.techSiteApp.UpdateDeviceAlarmLogDto;
import com.jaagro.cbs.api.dto.techconsult.ReturnTechConsultRecordDto;
import com.jaagro.cbs.api.dto.techconsult.TechConsultParamDto;
import com.jaagro.cbs.api.dto.techconsult.UpdateTechConsultDto;
import com.jaagro.cbs.api.enums.EmergencyLevelEnum;
import com.jaagro.cbs.api.enums.TechConsultHandleTypeEnum;
import com.jaagro.cbs.api.enums.TechConsultStatusEnum;
import com.jaagro.cbs.api.model.*;
import com.jaagro.cbs.api.service.DeviceAlarmLogService;
import com.jaagro.cbs.api.service.TechConsultService;
import com.jaagro.cbs.biz.mapper.*;
import com.jaagro.cbs.biz.utils.UrlPathUtil;
import com.jaagro.constant.UserInfo;
import com.oracle.tools.packager.mac.MacAppBundler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gavin
 * @date 2019/04/08
 */
@Service
@Slf4j
public class DeviceAlarmLogServiceImpl implements DeviceAlarmLogService {
    @Autowired
    private CurrentUserService currentUserService;

    @Autowired
    private DeviceAlarmLogMapperExt deviceAlarmLogMapperExt;
    /**
     * 技术询问列表-技术员APP
     *
     * @param dto
     * @return
     */
    @Override
    public PageInfo listDeviceAlarmLogApp(ToDoAlarmParam dto) {
        PageHelper.startPage(dto.getPageNum(), dto.getPageSize());
        Map<String,Integer> queryParam = new HashMap<>();
        queryParam.put("technicianId",dto.getTechnicianId());
        List<DeviceAlarmLogDto> DeviceAlarmLogDtoList = deviceAlarmLogMapperExt.listDeviceAlarmLogApp(queryParam);

        return new PageInfo(DeviceAlarmLogDtoList);
    }

    @Override
    public DeviceAlarmLogDto getDeviceAlarmLogDetail() {
        return null;
    }

    /**
     * 获取某个计划某个养殖场某个鸡舍某个日龄某个设备的报警
     *
     * @param queryDto
     * @return
     */
    @Override
    public List<DeviceAlarmLog> getLatestDeviceAlarmLog(UpdateDeviceAlarmLogDto queryDto) {
        DeviceAlarmLogExample example = new DeviceAlarmLogExample();
        example.createCriteria().andPlanIdEqualTo(queryDto.getPlanId())
                                .andPlantIdEqualTo(queryDto.getPlantId())
                                .andCoopIdEqualTo(queryDto.getCoopId())
                                .andDayAgeEqualTo(queryDto.getDayAge())
                                .andDeviceIdEqualTo(queryDto.getDeviceId());
        example.setOrderByClause("create_time desc");

        return deviceAlarmLogMapperExt.selectByExample(example);
    }


    /**
     * 处理某个计划某个养殖场某个鸡舍某个日龄某个设备的报警
     *
     * @param updateDto
     * @return
     */
    @Override
    public boolean handleDeviceAlarmLogRecord(UpdateDeviceAlarmLogDto updateDto) {
        try {
            log.info("O DeviceAlarmLogServiceImpl.handleDeviceAlarmLogRecord input updateDto:{}", updateDto);
            UserInfo currentUser = currentUserService.getCurrentUser();
           /*
            techConsultRecordDo.setTechConsultStatus(TechConsultStatusEnum.STATUS_SOLVED.getCode());
            techConsultRecordDo.setModifyUserId(currentUser != null ? currentUser.getId() : null);
            techConsultRecordDo.setHandleUserId(currentUser != null ? currentUser.getId() : null);
            techConsultRecordMapper.updateByPrimaryKeySelective(techConsultRecordDo);
            */
        } catch (Exception e) {
            log.error("R DeviceAlarmLogServiceImpl.handleDeviceAlarmLogRecord error:" + e);
            return false;
        }
        return true;
    }
}
