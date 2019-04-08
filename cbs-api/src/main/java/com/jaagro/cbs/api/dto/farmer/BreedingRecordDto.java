package com.jaagro.cbs.api.dto.farmer;

import com.jaagro.cbs.api.model.BreedingRecord;
import com.jaagro.cbs.api.dto.progress.BreedingRecordItemsDto;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 养殖记录
 *
 * @author yj
 * @date 2019/3/11 9:36
 */
@Data
@Accessors(chain = true)
public class BreedingRecordDto extends BreedingRecord implements Serializable {

    private static final long serialVersionUID = 8461319274474419912L;
    /**
     * 喂养记录明细
     */
    private List<BreedingRecordItemsDto> breedingRecordItemsList;
}
