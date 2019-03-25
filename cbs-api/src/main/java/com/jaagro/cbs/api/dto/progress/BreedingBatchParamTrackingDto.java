package com.jaagro.cbs.api.dto.progress;

import com.jaagro.cbs.api.model.BreedingBatchParameter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author :Gavin
 * @date :2019/02/25
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class BreedingBatchParamTrackingDto extends BreedingBatchParameter implements Serializable {

    private static final long serialVersionUID = 2925681939310981862L;
    /**
     * 仪表参数实际检查结果
     */
    private List<DeviceValueDto> actualResult;
    /**
     * 报警信息
     */
    private String alarmMessage;

    /**
     * 养殖过程中记录的值（）
     */
    private String actualValue;
}
