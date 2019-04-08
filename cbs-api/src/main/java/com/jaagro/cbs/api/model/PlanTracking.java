package com.jaagro.cbs.api.model;

import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author :gaoxin
 * @date :2019/04/04
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class PlanTracking implements Serializable {
    /**
     * 计划养殖流程追踪表id
     */
    private Integer id;

    /**
     * 计划id
     */
    private Integer planId;

    /**
     * 状态(0-计划待审核1-计划已审核,2-已过磅,3-已上鸡,4-饲养完成)
     */
    private String planStatus;

    /**
     * 日志信息
     */
    private String trackingInfo;

    /**
     * 是否有效(0-无效,1-有效)
     */
    private Boolean enable;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人
     */
    private Integer createUserId;

    /**
     * 更新时间
     */
    private Date modifyTime;

    /**
     * 更新人
     */
    private Integer modifyUserId;

    private static final long serialVersionUID = 1L;
}