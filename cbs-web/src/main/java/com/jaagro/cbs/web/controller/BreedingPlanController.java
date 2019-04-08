package com.jaagro.cbs.web.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.jaagro.cbs.api.dto.plan.*;
import com.jaagro.cbs.api.dto.plant.ReturnCoopDto;
import com.jaagro.cbs.api.dto.plant.ReturnPlantDto;
import com.jaagro.cbs.api.enums.CoopStatusEnum;
import com.jaagro.cbs.api.enums.PlanStatusEnum;
import com.jaagro.cbs.api.service.BreedingPlanService;
import com.jaagro.cbs.api.service.BreedingPlantService;
import com.jaagro.cbs.web.vo.plan.BreedingPlanVo;
import com.jaagro.cbs.web.vo.plan.coop.CheckCoopVo;
import com.jaagro.utils.BaseResponse;
import com.jaagro.utils.ResponseStatusCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


/**
 * @author @Gao.
 */
@RestController
@Api(description = "养殖计划管理", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class BreedingPlanController {
    @Autowired
    private BreedingPlanService breedingPlanService;
    @Autowired
    private BreedingPlantService breedingPlantService;


    @PostMapping("/createBreedingPlan")
    @ApiOperation("创建养殖计划")
    public BaseResponse createBreedingPlan(@RequestBody CreateBreedingPlanDto dto) {
        log.info("O createBreedingPlan param={}", JSON.toJSONString(dto));
        if (CollectionUtils.isEmpty(dto.getPlantIds())) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "养殖场不能为空");
        }
        if (dto.getCustomerId() == null) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "客户不能为空");
        }
        if (dto.getTechnicianId() == null) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "技术员不能为空");
        }
        breedingPlanService.createBreedingPlan(dto);
        return BaseResponse.successInstance(ResponseStatusCode.OPERATION_SUCCESS);
    }

    @PostMapping("/listBreedingPlan")
    @ApiOperation("养殖计划列表")
    public BaseResponse listBreedingPlan(@RequestBody BreedingPlanParamDto dto) {
        if (dto.getPageNum() == null) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "起始页不能为空");
        }
        if (dto.getPageSize() == null) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "每页条数不能为空");
        }
        PageInfo pageInfo = breedingPlanService.listBreedingPlan(dto);
        List<BreedingPlanVo> breedingPlanVos = new ArrayList<>();
        if (!CollectionUtils.isEmpty(pageInfo.getList())) {
            List<ReturnBreedingPlanDto> returnBreedingPlanDtos = pageInfo.getList();
            for (ReturnBreedingPlanDto returnBreedingPlanDto : returnBreedingPlanDtos) {
                BreedingPlanVo breedingPlanVo = new BreedingPlanVo();
                BeanUtils.copyProperties(returnBreedingPlanDto, breedingPlanVo);
                breedingPlanVo
                        .setPlanStatus(PlanStatusEnum.getDescByCode(returnBreedingPlanDto.getPlanStatus()));
                breedingPlanVos.add(breedingPlanVo);
            }
        }
        pageInfo.setList(breedingPlanVos);
        return BaseResponse.successInstance(pageInfo);
    }

    @PostMapping("/createPlanContract")
    @ApiOperation("录入合同")
    public BaseResponse createPlanContract(@RequestBody @Validated CreatePlanContractDto createPlanContractDto) {
        log.info("O createPlanContract createPlanContractDto={}", createPlanContractDto);
        breedingPlanService.createPlanContract(createPlanContractDto);
        return BaseResponse.successInstance("录入合同成功");
    }

    @PostMapping("/upDateBreedingPlanDetails")
    @ApiOperation("更新养殖计划列表")
    public BaseResponse upDateBreedingPlanDetails(@RequestBody UpdateBreedingPlanDto dto) {
        if (dto.getId() == null) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "养殖计划id不能为空");
        }
        breedingPlanService.upDateBreedingPlanDetails(dto);
        return BaseResponse.successInstance(ResponseStatusCode.OPERATION_SUCCESS);
    }

    @GetMapping("/breedingPlanDetails/{planId}")
    @ApiOperation("养殖计划列表详情")
    public BaseResponse breedingPlanDetails(@PathVariable("planId") Integer planId) {
        if (planId == null) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "养殖计划id不能为空");
        }
        return BaseResponse.successInstance(breedingPlanService.breedingPlanDetails(planId));
    }

    @GetMapping("/chickenSignDetails/{planId}")
    @ApiOperation("待上鸡签收详情")
    public BaseResponse chickenSignDetails(@PathVariable("planId") Integer planId) {
        if (planId == null) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "养殖计划id不能为空");
        }
        return BaseResponse.successInstance(breedingPlanService.chickenSignDetails(planId));
    }

    @GetMapping("/breedingDetails/{planId}")
    @ApiOperation("养殖中详情 确认出栏详情")
    public BaseResponse breedingDetails(@PathVariable("planId") Integer planId) {
        if (planId == null) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "养殖计划id不能为空");
        }
        return BaseResponse.successInstance(breedingPlanService.breedingDetails(planId));
    }

    @GetMapping("/listBreedingPlanCoops/{planId}")
    @ApiOperation("获取养殖计划鸡舍信息")
    public BaseResponse listBreedingPlanCoops(@PathVariable("planId") Integer planId) {
        if (planId == null) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "养殖计划id不能为空");
        }
        return BaseResponse.successInstance(breedingPlanService.listBreedingPlanCoopsForChoose(planId));
    }

    @PostMapping("/breedingPlanParamConfiguration")
    @ApiOperation("养殖计划参数配置")
    public BaseResponse breedingPlanParamConfiguration(@RequestBody @Validated BreedingPlanParamConfigurationDto dto) {
        log.info("O breedingPlanParamConfiguration param={}", dto);
        breedingPlanService.breedingPlanParamConfiguration(dto);
        return BaseResponse.successInstance("参数配置成功");
    }

    @PostMapping("/checkCoop")
    @ApiOperation("鸡舍查看")
    public BaseResponse checkCoop(@RequestBody CheckCoopParamDto dto) {
        if (CollectionUtils.isEmpty(dto.getPlantIds())) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "养殖场id不能为空");
        }
        List<CheckCoopVo> checkCoopVos = new ArrayList<>();
        List<ReturnPlantDto> returnPlantDtos = breedingPlantService.listPlantAndCoopByPlantIds(dto.getPlantIds());
        if (!CollectionUtils.isEmpty(returnPlantDtos)) {
            for (ReturnPlantDto returnPlantDto : returnPlantDtos) {
                List<ReturnCoopDto> returnCoopDtos = returnPlantDto.getReturnCoopDtos();
                if (!CollectionUtils.isEmpty(returnCoopDtos)) {
                    for (ReturnCoopDto returnCoopDto : returnCoopDtos) {
                        CheckCoopVo checkCoopVo = new CheckCoopVo();
                        if (CoopStatusEnum.LEISURE.getCode() != returnCoopDto.getCoopStatus()) {
                            continue;
                        }
                        checkCoopVo
                                .setPlantName(returnPlantDto.getPlantName())
                                .setCapacity(returnCoopDto.getCapacity())
                                .setCoopName(returnCoopDto.getCoopName())
                                .setLastEndDate(returnCoopDto.getLastEndDate())
                                .setLastStartDate(returnCoopDto.getLastStartDate())
                                .setCoopStatus(CoopStatusEnum.getDescByCode(returnCoopDto.getCoopStatus()));
                        checkCoopVos.add(checkCoopVo);
                    }
                }
            }
        }
        return BaseResponse.successInstance(checkCoopVos);
    }
}
