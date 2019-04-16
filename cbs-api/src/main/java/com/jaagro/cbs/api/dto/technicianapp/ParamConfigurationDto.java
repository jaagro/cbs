package com.jaagro.cbs.api.dto.technicianapp;

import com.jaagro.cbs.api.dto.standard.BreedingParameterDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 养殖计划参数配置参数
 *
 * @author byr
 * @date 2019/4/16
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ParamConfigurationDto implements Serializable {
    /**
     * 养殖参数列表
     */
    @NotEmpty(message = "{breedingParameterDtoList.NotEmpty}")
    private List<BreedingParameterDto> breedingParameterDtoList;
    /**
     * 鸡舍配置列表
     */
    @NotEmpty(message = "{breedingPlanCoopDtoList.NotEmpty}")
    private List<BreedingPlanPlantsDto> breedingPlanCoopDtoList;
    /**
     * 养殖计划id
     */
    @NotNull(message = "{planId.NotNull}")
    private Integer planId;
    /**
     * 养殖计划批次号
     */
    @NotBlank(message = "{batchNo.NotBlank}")
    private String batchNo;
}
