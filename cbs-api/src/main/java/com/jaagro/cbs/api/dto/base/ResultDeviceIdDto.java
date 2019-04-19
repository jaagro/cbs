package com.jaagro.cbs.api.dto.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 设备
 *
 * @author :baiyiran
 * @date :2019/04/19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ResultDeviceIdDto implements Serializable {
    private String result;

    private List<DeviceIdDto> list;


}