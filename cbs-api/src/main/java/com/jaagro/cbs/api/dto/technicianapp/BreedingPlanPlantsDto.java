package com.jaagro.cbs.api.dto.technicianapp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 养殖计划鸡舍信息
 *
 * @author byr
 * @date 2019/4/15
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class BreedingPlanPlantsDto implements Serializable {
    /**
     * 养殖场名称
     */
    private String plantName;

    /**
     * 养殖场id
     */
    private Integer plantId;

    /**
     * 计划id
     */
    private Integer planId;

    /**
     * 鸡舍列表
     */
    List<BreedingPlanCoopsDto> coops;

}
