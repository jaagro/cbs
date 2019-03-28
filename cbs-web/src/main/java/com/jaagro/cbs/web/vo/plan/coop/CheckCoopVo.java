package com.jaagro.cbs.web.vo.plan.coop;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @description: 鸡舍检查
 * @author: @Gao.
 * @create: 2019-03-07 13:49
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class CheckCoopVo implements Serializable {
    /**
     * 养殖场名称
     */
    private String plantName;

    /**
     * 鸡舍编号
     */
    private String coopName;

    /**
     * 容量
     */
    private Integer capacity;

    /**
     * 鸡舍状态
     */
    private String coopStatus;

    /**
     * 上次起始时间
     */
    private Date lastStartDate;

    /**
     * 上次截止时间
     */
    private Date lastEndDate;
}
