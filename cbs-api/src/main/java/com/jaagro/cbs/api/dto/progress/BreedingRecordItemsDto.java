package com.jaagro.cbs.api.dto.progress;

import com.jaagro.cbs.api.model.BreedingRecordItems;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 农户应喂药参数
 * @author gavin
 * @date 2019/3/9 15:20
 */
@Data
@Accessors(chain = true)
public class BreedingRecordItemsDto extends BreedingRecordItems implements Serializable{

    private static final long serialVersionUID = -6793933212959749175L;
    /**
     * 产品名称
     */
    private String productName;

    /**
     * 单位(g,ml)
     */
    private String capacityUnit;
}
