package com.jaagro.cbs.api.dto.standard;

import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 养殖参数在每个日龄上的值
 * @author yj
 * @date 2019/3/13 10:54
 */
@Data
@Accessors(chain = true)
public class BreedingParameterListDto implements Serializable{
    /**
     * 养殖模板id
     */
    @Min(value = 1,message = "{standardId.Min}")
    @NotNull(message = "{standardId.NotNull}")
    private Integer standardId;
    /**
     * 参数名称
     */
    @NotBlank(message = "{paramName.NotBlank}")
    private String paramName;
    /**
     * 参数类型（10-温度,11-湿度,12-光照强度,13-光照时间,14-氮气,15-二氧化碳,16-通风,17-负压值,20-喂料(次/日),21-喂水(次/日),22-饲喂(克/日),23-体重(克/只/日),24-死淘(只),25-药品/疫苗)
     */
    @Min(value = 1,message = "{paramType.Min}")
    @NotNull(message = "{paramType.NotNull}")
    private Integer paramType;
    /**
     * 数值类型(1-区间值,2-标准值,3-临界值)
     */
    @Min(value = 1,message = "{valueType.Min}")
    @NotNull(message = "{valueType.NotNull}")
    private Integer valueType;
    /**
     * 单位(摄氏度℃｜百分比%｜百万分比ppm｜立方英尺每分钟cfm｜小时H｜勒克斯lux｜只,｜次/日｜克/只/日|只/M/平方)
     */
    @NotBlank(message = "{unit.NotBlank}")
    private String unit;
    /**
     * 养殖参数状态（1-启用 0 -未启用）
     */
    @NotNull(message = "{status.NotNull}")
    private Integer status;

    /**
     * 是否必要(0-不必要,1-必要)
     */
    @NotNull(message = "{necessary.NotNull}")
    private Boolean necessary;

    /**
     * 是否参与报警
     */
    private Boolean alarm;

    /**
     * 展示顺序
     */
    private Integer displayOrder;

     /**
     * 养殖参数列表
     */
    @NotEmpty(message = "{breedingStandardParameterList.NotEmpty}")
    private List<BreedingStandardParameterItemDto> breedingStandardParameterList;
}
