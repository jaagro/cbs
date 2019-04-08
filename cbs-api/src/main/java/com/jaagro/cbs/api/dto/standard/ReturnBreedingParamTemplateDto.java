package com.jaagro.cbs.api.dto.standard;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @description: 养殖参数列表
 * @author: @Gao.
 * @create: 2019-03-15 16:38
 **/
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ReturnBreedingParamTemplateDto implements Serializable {

    /**
     * 计划id
     */
    private Integer id;

    /**
     * 批次号
     */
    private String batchNo;

    /**
     * 养殖户id
     */
    private Integer customerId;

    /**
     * 养殖户的名称
     */
    private String customerName;
    /**
     *
     */
    private String customerPhone;

    /**
     * 计划状态(0-待录入合同,1-待参数纠偏,2-待上鸡签收,3-养殖中,4-待出栏确认,5-待出栏计划,6-已完成,7-已取消)
     */
    private Integer planStatus;

    /**
     *
     */
    private String strPlanStatus;

    /**
     * 计划上鸡数量
     */
    private Integer planChickenQuantity;

    /**
     * 养殖参数模板id
     */
    private Integer breedingStandardId;

    /**
     * 上鸡时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date planTime;

    /**
     * 创建人姓名
     */
    private String createUserName;

    /**
     * 养殖模板名称
     */
    private String standardName;

}
