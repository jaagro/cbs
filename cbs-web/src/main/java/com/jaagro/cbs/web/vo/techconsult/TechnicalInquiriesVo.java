package com.jaagro.cbs.web.vo.techconsult;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @description: 农户端app技术询问列表
 * @author: @Gao.
 * @create: 2019-03-08 13:41
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class TechnicalInquiriesVo implements Serializable {

    /**
     * 养殖场名称
     */
    private String plantName;

    /**
     * 批次号
     */
    private String batchNo;

    /**
     * 地址
     */
    private String address;

    /**
     * 紧急程度(1-一般,2-次要,3-重要,4-紧急)
     */
    private String emergencyLevel;

    /**
     * 处理人
     */
    private String handleUser;

    /**
     * 处理人手机号
     */
    private String handlePhone;

    /**
     * 创建时间
     */
    private Date createTime;
}
