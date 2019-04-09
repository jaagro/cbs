package com.jaagro.cbs.web.controller.technicianapp;

import com.github.pagehelper.PageInfo;
import com.jaagro.cbs.api.dto.farmer.BreedingBatchParamDto;
import com.jaagro.cbs.api.dto.plan.ReturnBreedingPlanDto;
import com.jaagro.cbs.api.service.BreedingPlanService;
import com.jaagro.cbs.web.vo.technicianapp.UnConfirmChickenPlanVo;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

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

    /**
     * 确认出栏列表
     *
     * @param dto
     * @return
     */
    @PostMapping("/listUnConfirmChickenPlan")
    @ApiOperation("确认出栏列表")
    public BaseResponse listPublishedChickenPlan(@RequestBody BreedingBatchParamDto dto) {
        if (dto.getPageNum() == null) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "起始页不能为空");
        }
        if (dto.getPageSize() == null) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "每页条数不能为空");
        }
        PageInfo pageInfo = breedingPlanService.listBreedingPlanForTechnician(dto);
        List<UnConfirmChickenPlanVo> unConfirmChickenPlanVos = new ArrayList<>();
        if (!CollectionUtils.isEmpty(pageInfo.getList())) {
            List<ReturnBreedingPlanDto> breedingPlans = pageInfo.getList();
            for (ReturnBreedingPlanDto plan : breedingPlans) {
                UnConfirmChickenPlanVo planVo = new UnConfirmChickenPlanVo();
                BeanUtils.copyProperties(plan, planVo);
                unConfirmChickenPlanVos.add(planVo);
            }
        }
        pageInfo.setList(unConfirmChickenPlanVos);
        return BaseResponse.successInstance(pageInfo);
    }
}
