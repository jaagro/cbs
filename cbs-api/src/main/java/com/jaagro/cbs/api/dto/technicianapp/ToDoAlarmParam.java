package com.jaagro.cbs.api.dto.technicianapp;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author gavin
 * @Date 20190228
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ToDoAlarmParam implements Serializable {

    private static final long serialVersionUID = 3835147859136626721L;
    /**
     * 起始页
     */
    private Integer pageNum;

    /**
     * 每页条数
     */
    private Integer pageSize;

    /**
     * 技术员id
     */
    private Integer technicianId;

}
