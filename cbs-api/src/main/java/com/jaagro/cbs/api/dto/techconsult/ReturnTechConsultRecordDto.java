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
    /**
     * 处理的方式、途径
     */
    private String strHandleType;

    /**
     * 处理备注说明
     */
    private String handleDesc;
    /**
     * 处理人姓名
     */
    private String handleName;
    /**
     * 处理时间
     */
    private Date handleTime;
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
