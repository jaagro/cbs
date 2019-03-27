package com.jaagro.cbs.web.controller;

import com.jaagro.cbs.api.dto.plant.*;
import com.jaagro.cbs.api.service.BreedingCoopDeviceService;
import com.jaagro.cbs.api.service.BreedingPlantService;
import com.jaagro.utils.BaseResponse;
import com.jaagro.utils.ResponseStatusCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 养殖户管理
 *
 * @author baiyiran
 * @Date 2019/2/21
 */
@RestController
@Slf4j
@Api(description = "养殖户管理", produces = MediaType.APPLICATION_JSON_VALUE)
public class BreedingPlantController {

    @Autowired
    private BreedingPlantService breedingPlantService;
    @Autowired
    private BreedingCoopDeviceService breedingCoopDeviceService;

    /**
     * 养殖场-新增
     *
     * @param plantDto
     * @return
     */
    @ApiOperation("养殖场-新增")
    @PostMapping("/createPlant")
    public BaseResponse createPlant(@RequestBody CreatePlantDto plantDto) {
        if (StringUtils.isEmpty(plantDto.getPlantName())) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "养殖场名称不能为空");
        }
        if (StringUtils.isEmpty(plantDto.getPlantType())) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "养殖场类型不能为空");
        }
        if (StringUtils.isEmpty(plantDto.getExpandable())) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "未选择是否可以扩建");
        }
        if (StringUtils.isEmpty(plantDto.getDurableYears())) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "截止年份不能为空");
        }
        if (StringUtils.isEmpty(plantDto.getProvince())) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "省不能为空");
        }
        if (StringUtils.isEmpty(plantDto.getCity())) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "市不能为空");
        }
        if (StringUtils.isEmpty(plantDto.getCounty())) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "区不能为空");
        }
        try {
            breedingPlantService.createPlant(plantDto);
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResponse.errorInstance("创建失败：" + e.getMessage());
        }
        return BaseResponse.successInstance("创建成功");
    }

    /**
     * 养殖场-修改
     *
     * @param plantDto
     * @return
     */
    @ApiOperation("养殖场-修改")
    @PutMapping("/updatePlant")
    public BaseResponse updatePlant(@RequestBody UpdatePlantDto plantDto) {
        if (StringUtils.isEmpty(plantDto.getId())) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "养殖场id不能为空");
        }
        return BaseResponse.service(breedingPlantService.updatePlant(plantDto));
    }

    /**
     * 养殖场-获取单条
     *
     * @param id
     * @return
     */
    @ApiOperation("养殖场-获取单条")
    @GetMapping("/getPlant/{id}")
    public BaseResponse<ReturnPlantDto> getPlant(@PathVariable("id") Integer id) {
        return BaseResponse.successInstance(breedingPlantService.getPlantDetailsById(id));
    }

    /**
     * 养殖场-通过养殖户id获得养殖场列表
     *
     * @param customerId
     * @return
     */
    @ApiOperation("养殖场-通过养殖户id获得养殖场列表")
    @GetMapping("/listPlantByCustomer/{customerId}")
    public BaseResponse<List<ReturnPlantDto>> listPlantByCustomer(@PathVariable("customerId") Integer customerId) {
        return BaseResponse.successInstance(breedingPlantService.listPlantByCustomerId(customerId));
    }

    /**
     * 鸡舍-新增
     *
     * @param coopDto
     * @return
     */
    @ApiOperation("鸡舍-新增")
    @PostMapping("/createCoop")
    public BaseResponse createCoop(@RequestBody CreateCoopDto coopDto) {
        if (StringUtils.isEmpty(coopDto.getPlantId())) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "养殖场id不能为空");
        }
        if (StringUtils.isEmpty(coopDto.getCoopName())) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "鸡舍名称不能为空");
        }
        if (StringUtils.isEmpty(coopDto.getCapacity())) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "可养数量不能为空");
        }
        Map<String, Object> result;
        try {
            result = breedingPlantService.createCoop(coopDto);
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResponse.errorInstance(e.getMessage());
        }
        return BaseResponse.service(result);
    }

    @ApiOperation("鸡舍-删除")
    @GetMapping("/deleteCoop/{plantId}")
    public BaseResponse deleteCoop(@PathVariable("plantId") Integer coopId) {
        if (coopId == null) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "鸡舍id不能为空");
        }
        return null;
    }

    /**
     * 鸡舍-通过养殖场id获得列表
     *
     * @param
     * @return
     */
    @ApiOperation("鸡舍-通过养殖场id获得列表")
    @GetMapping("/coop/{plantId}")
    public BaseResponse<List<ReturnCoopDto>> createPlant(@PathVariable("plantId") Integer plantId) {
        if (plantId == null) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "养殖场id不能为空");
        }
        return BaseResponse.successInstance(breedingPlantService.listCoopByPlantId(plantId));
    }

    /**
     * 鸡舍与设备进行关联
     * (新增鸡舍设备)
     *
     * @param dto
     * @return
     * @author @Gao.
     */
    @ApiOperation("鸡舍与设备关联")
    @PostMapping("/bindDeviceToCoop")
    public BaseResponse bindDeviceToCoop(@RequestBody CreateCoopDeviceDto dto) {
        if (dto.getCoopId() == null) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "鸡舍id不能为空");
        }
        if (dto.getDeviceName() == null) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "设备名称不能为空");
        }
        breedingCoopDeviceService.bindDeviceToCoop(dto);
        return BaseResponse.successInstance(ResponseStatusCode.OPERATION_SUCCESS);
    }

    /**
     * 根据养殖场id 查询出鸡舍与设备列表相关信息
     *
     * @param
     * @return
     * @author @Gao.
     */
    @ApiOperation("鸡舍与设备列表")
    @GetMapping("/listBreedingCoopDevice/{plantId}")
    public BaseResponse listBreedingCoopDevice(@PathVariable("plantId") Integer plantId) {
        if (plantId == null) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "养殖场id不能为空");
        }
        return BaseResponse.successInstance(breedingCoopDeviceService.listBreedingCoopDevice(plantId));
    }

    /**
     * 根据鸡舍id 查询出设备列表
     *
     * @param
     * @return
     * @author baiyiran
     */
    @ApiOperation("获取鸡舍下的设备列表")
    @GetMapping("/listBreedingCoopDeviceByCoopId/{coopId}")
    public BaseResponse listBreedingCoopDeviceByCoopId(@PathVariable("coopId") Integer coopId) {
        if (coopId == null) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "鸡舍id不能为空");
        }
        return BaseResponse.successInstance(breedingCoopDeviceService.listCoopDeviceByCoopId(coopId));
    }

    @ApiOperation("查询当前客户有鸡舍的养殖场")
    @GetMapping("/listFreePlantByCustomerId/{customerId}")
    public BaseResponse<List<ReturnBasicPlantDto>> listFreePlantByCustomerId(@PathVariable("customerId") Integer customerId) {
        return BaseResponse.successInstance(breedingPlantService.listFreePlantByCustomerId(customerId));
    }
}
