package com.jaagro.cbs.web.vo.plan;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @description: 农户端上鸡计划列表
 * @author: @Gao.
 * @create: 2019-03-08 11:24
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class PublishedChickenPlanVo implements Serializable {
    /**
     * 计划id
     */
    private Integer id;

    /**
     * 批次号
     */
    private String batchNo;

    /**
     * 计划上鸡数量
     */
    private Integer planChickenQuantity;

    /**
     * 上鸡时间
     */
    private Date planTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 养殖户名称
     */
    private String customerName;

}
