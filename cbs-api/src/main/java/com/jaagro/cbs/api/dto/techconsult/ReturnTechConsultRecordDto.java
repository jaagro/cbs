package com.jaagro.cbs.api.dto.techconsult;


import com.jaagro.cbs.api.model.TechConsultImages;
import com.jaagro.cbs.api.model.TechConsultRecord;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author gavin
 * @Date 20190301
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ReturnTechConsultRecordDto extends TechConsultRecord implements Serializable {

    private String strEmergencyLevel;

    private String strTechConsultStatus;

    private String strHandleType;
    /**
     * 存栏量
     */
    private int livingAmount;
    /**
     * 养殖天数
     */
    private Integer breedingDays;
    /**
     * 上传的图片
     */
    private List<TechConsultImages> imagesList;
}
