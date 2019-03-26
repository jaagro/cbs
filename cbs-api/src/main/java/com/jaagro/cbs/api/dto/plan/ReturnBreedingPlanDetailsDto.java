package com.jaagro.cbs.api.dto.plan;

import com.jaagro.cbs.api.dto.base.ListEmployeeDto;
import com.jaagro.cbs.api.model.Plant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @description: 养殖计划详情对象
 * @author: @Gao.
 * @create: 2019-02-27 09:55
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ReturnBreedingPlanDetailsDto implements Serializable {
    /**
     * 计划id
     */
    private Integer id;

    /**
     * 批次号
     */
    private String batchNo;

    /**
     * 养殖户的名称
     */
    private String customerName;
    /**
     *
     */
    private String customerPhone;
    /**
     * 计划状态(0-待录入合同,1-待参数纠偏,2-待上鸡签收,3-养殖中,4-待出栏确认,5-已完成,6-已取消)
     */
    private Integer planStatus;

    /**
     * 计划上鸡数量
     */
    private Integer planChickenQuantity;

    /**
     * 剩余上鸡数量
     */
    private Integer residueChickenQuantity;

    /**
     * 上鸡时间
     */
    private Date planTime;

    /**
     * 技术员姓名
     */
    private String technician;

    /**
     * 技术员id
     */
    private Integer technicianId;

    /**
     * 备注
     */
    private String notes;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人姓名
     */
    private String createUserName;

    /**
     * 最大可养数量
     */
    private Integer breedingAble;

    /**
     * 养殖场
     */
    private List<Plant> plants;

    /**
     * 部门下技术员信息
     */
    private List<ListEmployeeDto> technicianList;
}
