package com.jaagro.cbs.api.dto.plant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @description: 返回养殖场基本信息
 * @author: @Gao.
 * @create: 2019-03-27 10:17
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ReturnBasicPlantDto implements Serializable {
    /**
     * 养殖场id
     */
    private Integer id;

    /**
     * 养殖场名称
     */
    private String plantName;
}
