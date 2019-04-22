package com.jaagro.cbs.api.model;

import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
<<<<<<< HEAD
 * @author :tony
 * @date :2019/04/22
=======
 * @author :asus
 * @date :2019/04/19
>>>>>>> jaagro/v1.0.1-dev
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class CoopDevice implements Serializable {
    /**
     * 鸡舍设备表id
     */
    private Integer id;

    /**
     * 鸡舍id
     */
    private Integer coopId;

    /**
     * 养殖场id
     */
    private Integer plantId;

    /**
     * IOT设备唯一识别号
     */
    private String deviceCode;

    /**
     * 检测类型(（10-温度,11-湿度,12-光照强度,13-光照时间,14-氮气,15-二氧化碳,16-通风,17-负压值,20-饲喂(次/日),21-饲喂(克/日),22-体重(克/只/日),23-死淘(只),24-药品/疫苗))
     */
    private Integer deviceType;

    /**
     * 设备名称
     */
    private String deviceName;

    /**
     * 生产商
     */
    private String producer;

    /**
     * 设备型号（生产商家的型号）
     */
    private String modelNumber;

    /**
     * 回调url
     */
    private String apiUrl;

    /**
     * 设备状态（0-拆除，1-正常 2-故障）
     */
    private Integer coopDeviceStatus;

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
     * 是否有效（1-有效 0-无效）
     */
    private Boolean enable;

    private static final long serialVersionUID = 1L;
}