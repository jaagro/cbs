package com.jaagro.cbs.api.dto.standard;

import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 创建养殖模板参数
 * @author yj
 * @date 2019/3/13 14:27
 */
@Data
@Accessors(chain = true)
public class CreateBreedingStandardDto implements Serializable {
    private static final long serialVersionUID = -2606009629739171469L;
    /**
     * 养殖标准方案表id
     */
    private Integer id;
    /**
     * 标准方案名称
     */
    @NotBlank(message = "{standardName.NotBlank}")
    private String standardName;

    /**
     * 养殖类型(1-平养,2-棚养,3-笼养)
     */
    @Min(value = 1, message = "{breedingType.Min}")
    @NotNull(message = "{breedingType.NotNull}")
    private Integer breedingType;

    /**
     * 养殖天数
     */
    @Min(value = 30, message = "{breedingDays.Min}")
    @Max(value = 60, message = "{breedingDays.Max}")
    @NotNull(message = "{breedingDays.NotNull}")
    private Integer breedingDays;
}
