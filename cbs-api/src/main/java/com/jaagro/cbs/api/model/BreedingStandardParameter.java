package com.jaagro.cbs.api.model;

import java.io.Serializable;
import java.math.BigDecimal;
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
public class BreedingStandardParameter implements Serializable {
    /**
     * 参数id
     */
    private Integer id;

    /**
     * 标准方案id
     */
    private Integer standardId;

    /**
     * 参数名称
     */
    private String paramName;

    /**
     * 日龄
     */
    private Integer dayAge;

    /**
     * 参数类型（10-温度,11-湿度,12-光照强度,13-光照时间,14-氮气,15-二氧化碳,16-通风,17-负压值,20-喂料(次/日),21-喂水(次/日),22-饲喂(克/日),23-体重(克/只/日),24-死淘(只),25-药品/疫苗)
     */
    private Integer paramType;

    /**
     * 数值类型(1-区间值,2-标准值,3-临界值)
     */
    private Integer valueType;

    /**
     * 参考值下限
     */
    private BigDecimal lowerLimit;

    /**
     * 参考值上限(临界值时有效)
     */
    private BigDecimal upperLimit;

    /**
     * 单位(摄氏度℃｜百分比%｜百万分比ppm｜立方英尺每分钟cfm｜小时H｜勒克斯lux｜只,｜次/日｜克/只/日|只/M/平方)
     */
    private String unit;

    /**
     * 超限是否报警(0-不报警,1-报警)
     */
    private Boolean alarm;

    /**
     * 参数值(标准值,临界值)
     */
    private String paramValue;

    /**
     * 临界值方向(1>=,2<=)
     */
    private Integer thresholdDirection;

    /**
     * 养殖参数状态（1-启用 0 -未启用）
     */
    private Integer status;

    /**
     * 是否必要(0-不必要,1-必要)
     */
    private Boolean necessary;

    /**
     * 展示顺序
     */
    private Integer displayOrder;

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

    /**
     * 是否有效（1- 有效 0 -无效）
     */
    private Boolean enable;

    private static final long serialVersionUID = 1L;
}