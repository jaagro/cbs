package com.jaagro.cbs.api.model;

import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author :tony
 * @date :2019/04/22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class PlanLog implements Serializable {
    /**
     * 计划日志表id
     */
    private Integer id;

    /**
     * 计划id
     */
    private Integer planId;

    /**
     * 初始状态(0-待关联,1-已关联,2-执行中,3-已完成,4-已取消)
     */
    private Integer oldStatus;

    /**
     * 变更后状态(0-待关联,1-已关联,2-执行中,3-已完成,4-已取消)
     */
    private Integer newStatus;

    /**
     * 日志信息
     */
    private String logInfo;

    /**
     * 关联业务id
     */
    private Integer bizReferenceId;

    /**
     * 关联业务类型(1-关联合同)
     */
    private Integer bizReferenceType;

    /**
     * 是否有效(0-无效,1-有效)
     */
    private Boolean enable;

    /**
     * 创建时间
     */
    private Date createTime;

    private static final long serialVersionUID = 1L;
}