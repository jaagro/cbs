package com.jaagro.cbs.web.vo.techconsult;


import com.jaagro.cbs.api.model.TechConsultRecord;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author gavin
 * @Date 20190228
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class TechConsultVo extends TechConsultRecord implements Serializable {

    private static final long serialVersionUID = 8863129538029688698L;
    /**
     * 紧急程度(1-一般,2-次要,3-重要,4-紧急)
     */
    private String strEmergencyLevel;


    /**
     * 技术咨询状态(0-待处理,1-已处理)
     */
    private String strTechConsultStatus;


    /**
     * 养殖天数
     */
    private Integer breedingDays;
}
