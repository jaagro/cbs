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
public class DeviceHistoryData implements Serializable {
    private Integer id;

    private String deviceCode;

    private Date createTime;

    /**
     * `1
     */
    private Boolean enable;

    private String dataJson;

    private static final long serialVersionUID = 1L;
}