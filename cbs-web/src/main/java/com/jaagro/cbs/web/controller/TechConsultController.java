package com.jaagro.cbs.web.controller;

import com.github.pagehelper.PageInfo;
import com.jaagro.cbs.api.dto.techconsult.ReturnTechConsultRecordDto;
import com.jaagro.cbs.api.dto.techconsult.TechConsultParamDto;
import com.jaagro.cbs.api.dto.techconsult.UpdateTechConsultDto;
import com.jaagro.cbs.api.enums.EmergencyLevelEnum;
import com.jaagro.cbs.api.enums.TechConsultStatusEnum;
import com.jaagro.cbs.api.model.TechConsultRecord;
import com.jaagro.cbs.api.service.BreedingPlanService;
import com.jaagro.cbs.api.service.TechConsultService;
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
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 技术询问管理
 * @author gavin
 */
@RestController
@Slf4j
@Api(description = "技术询问管理", produces = MediaType.APPLICATION_JSON_VALUE)
public class TechConsultController {

    @Autowired
    private TechConsultService techConsultService;
    @Autowired
    private BreedingPlanService breedingPlanService;

    /**
     * 获取技术询问列表
     * @author gavin
     * @date 2019/2/28
     */
    @ApiOperation("获取技术询问列表")
    @PostMapping("/listTechConsultRecords")
    public BaseResponse<PageInfo> listTechConsultRecords(@RequestBody TechConsultParamDto criteriaDto) {
        if (StringUtils.isEmpty(criteriaDto.getPageNum())) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "pageNum不能为空");
        }
        if (StringUtils.isEmpty(criteriaDto.getPageSize())) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "pageSize不能为空");
        }
        PageInfo pageInfo = techConsultService.listTechConsultRecords(criteriaDto);
        if (pageInfo != null) {
            List<TechConsultVo> voList = new ArrayList<>();
            List<TechConsultRecord> doList = pageInfo.getList();
            if (!CollectionUtils.isEmpty(doList)) {
                for (TechConsultRecord techConsultRecord : doList) {
                    TechConsultVo techConsultVo = new TechConsultVo();
                    BeanUtils.copyProperties(techConsultRecord, techConsultVo);

                    techConsultVo.setStrEmergencyLevel(EmergencyLevelEnum.getDescByCode(techConsultRecord.getEmergencyLevel()));
                    techConsultVo.setStrTechConsultStatus(TechConsultStatusEnum.getDescByCode(techConsultRecord.getTechConsultStatus()));
                    voList.add(techConsultVo);
                }
            }
            pageInfo.setList(voList);
        }
        return BaseResponse.successInstance(pageInfo);
    }

    /**
     * 获取技术询问详情
     * @author gavin
     * @date 2019/03/01
     */
    @ApiOperation("获取技术询问列表")
    @GetMapping("/getDetailTechConsultDtoById/{id}")
    public BaseResponse getDetailTechConsultDtoById(@PathVariable("id") Integer id) {

        ReturnTechConsultRecordDto returnDto = techConsultService.getDetailTechConsultDtoById(id);
        if (returnDto != null) {
            return BaseResponse.successInstance(returnDto);
        }else {
            return BaseResponse.errorInstance("获取技术询问详情失败");
        }
    }
    /**
     * 处理技术申请
     * @author gavin
     * @date 2019/03/01
     */
    @ApiOperation("获取技术询问列表")
    @PostMapping("/handleTechConsultRecord")
    public BaseResponse handleTechConsultRecord(@RequestBody UpdateTechConsultDto updateDto) {

        boolean flag = techConsultService.handleTechConsultRecord(updateDto);
        if (flag) {
            return BaseResponse.successInstance("处理技术申请成功!");
        }else {
            return BaseResponse.errorInstance("处理技术申请失败");
        }
    }
    /**
     * 获取技术询问列表
     * @author gavin
     * @date 2019/04/03
     */
    @ApiOperation("获取技术询问列表-技术员APP")
    @PostMapping("/listTechConsultRecordsApp")
    public BaseResponse<PageInfo> listTechConsultRecordsApp(@RequestBody TechConsultParamDto criteriaDto) {

        if (StringUtils.isEmpty(criteriaDto.getPageNum())) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "pageNum不能为空");
        }
        if (StringUtils.isEmpty(criteriaDto.getPageSize())) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "pageSize不能为空");
        }
        breedingPlanService.listBreedingPlanByTechnicianId()
        PageInfo pageInfo == techConsultService.listTechConsultRecordsApp(criteriaDto);
        if (pageInfo != null) {
            List<TechConsultVo> voList = new ArrayList<>();
            List<TechConsultRecord> doList = pageInfo.getList();
            if (!CollectionUtils.isEmpty(doList)) {
                for (TechConsultRecord techConsultRecord : doList) {
                    TechConsultVo techConsultVo = new TechConsultVo();
                    BeanUtils.copyProperties(techConsultRecord, techConsultVo);

                    techConsultVo.setStrEmergencyLevel(EmergencyLevelEnum.getDescByCode(techConsultRecord.getEmergencyLevel()));
                    techConsultVo.setStrTechConsultStatus(TechConsultStatusEnum.getDescByCode(techConsultRecord.getTechConsultStatus()));
                    voList.add(techConsultVo);
                }
            }
            pageInfo.setList(voList);
        }
        return BaseResponse.successInstance(pageInfo);
    }
}
