package com.jaagro.cbs.web.controller;

import com.jaagro.cbs.api.dto.ValidList;
import com.jaagro.cbs.api.dto.standard.*;
import com.jaagro.cbs.api.enums.CapacityUnitEnum;
import com.jaagro.cbs.api.model.BreedingStandard;
import com.jaagro.cbs.api.model.BreedingStandardParameter;
import com.jaagro.cbs.api.service.BreedingStandardService;
import com.jaagro.cbs.web.vo.standard.BreedingStandardBaseVo;
import com.jaagro.cbs.web.vo.standard.BreedingStandardDrugItemVo;
import com.jaagro.cbs.web.vo.standard.BreedingStandardDrugListVo;
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

import java.util.*;


/**
 * @author yj
 * @Date 20190222
 */
@RestController
@Slf4j
@Api(description = "养殖标准模板管理", produces = MediaType.APPLICATION_JSON_VALUE)
public class BreedingStandardController {

    @Autowired
    private BreedingStandardService breedingStandardService;


    /**
     * 新增养殖标准模板
     *
     * @param dto
     * @return
     */
    @ApiOperation("新增、修改养殖标准模板")
    @PostMapping("/breedingStandard")
    public BaseResponse breedingStandard(@RequestBody @Validated CreateBreedingStandardDto dto) {
        log.info("O saveOrUpdate breedingStandard param={}", dto);
        Integer id;
        try {
            if (null == dto.getId() || dto.getId() == 0) {
                id = breedingStandardService.createBreedingTemplate(dto);
            } else {
                id = breedingStandardService.updateBreedingTemplate(dto);
            }
        } catch (Exception ex) {
            return BaseResponse.errorInstance("保存失败：" + ex.getMessage());
        }
        return BaseResponse.successInstance(id);
    }

    @ApiOperation("删除养殖标准模板")
    @DeleteMapping("/breedingStandard/{standardId}")
    public BaseResponse breedingStandard(@PathVariable("standardId") Integer standardId) {
        log.info("O delete breedingStandard standardId={}", standardId);
        breedingStandardService.delBreedingStandard(standardId);
        return BaseResponse.successInstance("删除养殖标准模板成功");
    }

    @ApiOperation("查看养殖模板基本信息")
    @GetMapping("/breedingStandard/{id}")
    public BaseResponse getBreedingStandard(@PathVariable Integer id) {
        log.info("O getBreedingStandard id={}", id);
        BreedingStandard standard = breedingStandardService.getStandardBaseInfoById(id);
        BreedingStandardBaseVo vo = new BreedingStandardBaseVo();
        if (standard != null) {
            BeanUtils.copyProperties(standard, vo);
        }
        return BaseResponse.successInstance(vo);
    }

    @ApiOperation("根据养殖模板id获取养殖参数模块列表")
    @GetMapping("/listParameterNameByStandardId/{standardId}")
    public BaseResponse listParameterNameByStandardId(@PathVariable("standardId") Integer standardId) {
        log.info("O listParameterNameByStandardId standardId={}", standardId);
        return BaseResponse.successInstance(breedingStandardService.listParameterNameByStandardId(standardId));
    }

    @ApiOperation("根据养殖模板id参数名称获取参数列表")
    @GetMapping("/listParameterListByName")
    public BaseResponse listParameterListByName(@RequestParam Integer standardId, @RequestParam String paramName, @RequestParam Integer paramType) {
        log.info("O listParameterListByName standardId={},paramName={},paramType", standardId, paramName, paramType);
        return BaseResponse.successInstance(breedingStandardService.listParameterListByName(standardId, paramName, paramType));
    }

    @ApiOperation("新增或者修改养殖参数")
    @PostMapping("/breedingStandardParameter")
    public BaseResponse breedingStandardParameter(@RequestBody @Validated BreedingParameterListDto dto) {
        log.info("O breedingStandardParameter param={}", dto);
        breedingStandardService.saveOrUpdateParameter(dto);
        return BaseResponse.successInstance("保存成功");
    }

    @ApiOperation("排序变更")
    @PostMapping("/changeParameterDisplayOrder")
    public BaseResponse changeParameterDisplayOrder(@RequestBody @Validated ChangeParameterDisplayOrderDto dto) {
        log.info("O changeParameterDisplayOrder param={}", dto);
        return BaseResponse.successInstance(breedingStandardService.changeParameterDisplayOrder(dto));
    }

    @ApiOperation("删除养殖参数配置信息")
    @DeleteMapping("/delBreedingStandardParam")
    public BaseResponse delBreedingStandardParam(@RequestBody @Validated DelBreedingStandardParamDto dto) {
        log.info("O delBreedingStandardParam dto={}", dto);
        breedingStandardService.delBreedingStandardParam(dto);
        return BaseResponse.successInstance("删除成功");
    }


    @ApiOperation("查询养殖模板药品配置信息")
    @GetMapping("/listBreedingStandardDrugs/{standardId}")
    public BaseResponse listBreedingStandardDrugs(@PathVariable("standardId") Integer standardId) {
        log.info("O listBreedingStandardDrugs standardId={}", standardId);
        List<BreedingStandardDrugListVo> breedingStandardDrugListVoList = generateStandardDrugs(breedingStandardService.listBreedingStandardDrugs(standardId));
        return BaseResponse.successInstance(breedingStandardDrugListVoList);
    }

    @ApiOperation("养殖参数模板药品配置")
    @PostMapping("/breedingStandardDrugs")
    public BaseResponse breedingStandardDrugs(@RequestBody @Validated ValidList<BreedingStandardDrugListDto> drugList) {
        log.info("O breedingStandardDrugs drugList={}", drugList);
        if (CollectionUtils.isEmpty(drugList)) {
            return BaseResponse.errorInstance("参数为空");
        }
        breedingStandardService.configurationDrugs(drugList);
        return BaseResponse.successInstance("配置成功");
    }

    @ApiOperation("查询所有的养殖模板")
    @GetMapping("/listAllBreedingStandard")
    public BaseResponse listAllBreedingStandard() {
        log.info("O listAllBreedingStandard");
        return BaseResponse.successInstance(breedingStandardService.listAllBreedingStandard());
    }

    @ApiOperation("养殖计划参数配置可选择养殖模板列表")
    @GetMapping("/listBreedingStandardForParamConfiguration")
    public BaseResponse listBreedingStandardForParamConfiguration(){
        log.info("O listBreedingStandardForParamConfiguration");
        return BaseResponse.successInstance(breedingStandardService.listBreedingStandardForParamConfiguration());
    }
    @ApiOperation("查询单个养殖模板详情按日龄分组")
    @GetMapping("/getBreedingStandardDetail/{id}")
    public BaseResponse getBreedingStandardDetail(@PathVariable("id") Integer id) {
        BreedingStandardDto breedingStandardDto = breedingStandardService.getBreedingStandardById(id);
        // 将养殖参数按照日龄分组
        BreedingStandardDetailDto detailDto = groupBreedingStandard(breedingStandardDto);
        return BaseResponse.successInstance(detailDto);
    }

    /**
     * 将养殖模板配置信息按日龄分组
     *
     * @param breedingStandardDrugListDtoList
     * @return
     */
    private List<BreedingStandardDrugListVo> generateStandardDrugs(List<BreedingStandardDrugDto> breedingStandardDrugListDtoList) {
        List<BreedingStandardDrugListVo> listVoList = new ArrayList<>();
        Set<Integer> dayAgeStart = new HashSet<>();
        if (!CollectionUtils.isEmpty(breedingStandardDrugListDtoList)) {
            breedingStandardDrugListDtoList.forEach(dto -> {
                if (dto.getDayAgeStart() != null) {
                    dayAgeStart.add(dto.getDayAgeStart());
                }
            });
            for (Integer startDayAge : dayAgeStart) {
                BreedingStandardDrugListVo drugListVo = new BreedingStandardDrugListVo();
                drugListVo.setDayAgeStart(startDayAge);
                List<BreedingStandardDrugItemVo> breedingStandardDrugItemVoList = new ArrayList<>();
                drugListVo.setBreedingStandardDrugItemVoList(breedingStandardDrugItemVoList);
                listVoList.add(drugListVo);
            }
            for (BreedingStandardDrugDto drugDto : breedingStandardDrugListDtoList) {
                for (BreedingStandardDrugListVo drugListVo : listVoList) {
                    if (drugDto.getDayAgeStart() != null && drugDto.getDayAgeStart().equals(drugListVo.getDayAgeStart())) {
                        drugListVo.setDayAgeEnd(drugDto.getDayAgeEnd())
                                .setStandardId(drugDto.getBreedingStandardId())
                                .setStopDrugFlag(drugDto.getStopDrugFlag());
                        if (!drugDto.getStopDrugFlag()) {
                            List<BreedingStandardDrugItemVo> drugItemVoList = drugListVo.getBreedingStandardDrugItemVoList();
                            BreedingStandardDrugItemVo drugItemVo = new BreedingStandardDrugItemVo();
                            drugItemVo.setCapacityUnit(CapacityUnitEnum.getTypeByCode(drugDto.getCapacityUnit()))
                                    .setFeedVolume(drugDto.getFeedVolume())
                                    .setId(drugDto.getId())
                                    .setProductId(drugDto.getProductId())
                                    .setSkuNo(drugDto.getSkuNo())
                                    .setProductName(drugDto.getProductName());
                            drugItemVoList.add(drugItemVo);
                        }
                    }
                }
            }
        }
        listVoList.sort(Comparator.comparingInt(BreedingStandardDrugListVo::getDayAgeStart));
        return listVoList;
    }

    private BreedingStandardDetailDto groupBreedingStandard(BreedingStandardDto breedingStandardDto) {
        BreedingStandardDetailDto detailDto = new BreedingStandardDetailDto();
        List<BreedingStandardParameter> standardParameterDos = breedingStandardDto.getStandardParameterDos();
        if (!CollectionUtils.isEmpty(standardParameterDos)) {
            Set<Integer> dayAgeSet = new HashSet<>();
            List<BreedingParameterDto> breedingParameterDtoList = new ArrayList<>();
            standardParameterDos.forEach(parameter -> dayAgeSet.add(parameter.getDayAge()));
            for (Integer dayAge : dayAgeSet) {
                BreedingParameterDto parameterDto = new BreedingParameterDto();
                parameterDto.setDayAge(dayAge);
                List<BreedingStandardParameter> parameterList = new ArrayList<>();
                parameterDto.setBreedingStandardParameterList(parameterList);
                breedingParameterDtoList.add(parameterDto);
            }
            for (BreedingStandardParameter parameter : standardParameterDos) {
                for (BreedingParameterDto parameterDto : breedingParameterDtoList) {
                    List<BreedingStandardParameter> breedingStandardParameterList = parameterDto.getBreedingStandardParameterList();
                    if (parameter.getDayAge().equals(parameterDto.getDayAge())) {
                        breedingStandardParameterList.add(parameter);
                    }
                }
            }
            detailDto.setBreedingParameterDtoList(breedingParameterDtoList);
            detailDto.setDayAgeList(new ArrayList<>(dayAgeSet));
        }
        return detailDto;
    }

    @PostMapping("/listBreedingParamTemplate")
    @ApiOperation("养殖参数模板列表")
    public BaseResponse listBreedingParamTemplate(@RequestBody BreedingParamTemplateCriteria dto) {
        if (dto.getPageNum() == null) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "起始页不能为空");
        }
        if (dto.getPageSize() == null) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "每页条数不能为空");
        }
        return BaseResponse.successInstance(breedingStandardService.listBreedingParamTemplate(dto));
    }
}
