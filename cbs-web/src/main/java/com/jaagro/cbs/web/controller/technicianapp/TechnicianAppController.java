package com.jaagro.cbs.web.controller.technicianapp;

import com.jaagro.cbs.api.dto.farmer.BreedingBatchParamDto;
import com.jaagro.cbs.api.service.BreedingPlanService;
import com.jaagro.utils.BaseResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 技术管理app端
 *
 * @author baiyiran
 * @date 2019-04-08
 */
@RestController
@Api(description = "技术管理app端", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class TechnicianAppController {
    @Autowired
    private BreedingPlanService breedingPlanService;

    /**
     * 养殖
     *
     * @param dto
     * @return
     * @author byr
     */
    @PostMapping("/listBreedingBatchForTechnician")
    @ApiOperation("养殖")
    public BaseResponse listBreedingBatchForTechnician(@RequestBody @Validated BreedingBatchParamDto dto) {
        log.info("O listBreedingBatchForFarmer params={}", dto);
        return BaseResponse.successInstance(breedingPlanService.listBreedingBatchForTechnician(dto));
    }
}
