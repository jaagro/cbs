package com.jaagro.cbs.api.dto.technicianapp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 参数配置 鸡舍信息
 *
 * @author byr
 * @date 2019/4/16
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class paramCoopsDto implements Serializable {
    /**
     * 客户鸡舍信息表id
     */
    private Integer id;

    /**
     * 在养数量
     */
    private Integer breedingValue;
}
