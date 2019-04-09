package com.jaagro.cbs.api.dto.technicianapp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @description:技术端分页参数
 * @author: baiyiran
 * @create: 2019-04-09
 **/
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class BreedingPlanCriteriaDto implements Serializable {
    /**
     * 起始页
     */
    @NotNull(message = "{pageNum.NotNull}")
    @Min(value = 1, message = "{pageNum.Min}")
    private Integer pageNum;

    /**
     * 每页条数
     */
    @NotNull(message = "{pageSize.NotNull}")
    @Min(value = 1, message = "{pageSize.Min}")
    private Integer pageSize;

    /**
     * 计划状态(0-待录入合同,1-待参数纠偏,4-待出栏确认)
     */
    @NotNull(message = "{planStatus.NotNull}")
    @Min(value = 1, message = "{planStatus.Min}")
    private Integer planStatus;

    /**
     * 技术员id
     */
    private Integer technicianId;
}
