package com.jaagro.cbs.api.service;

import com.github.pagehelper.PageInfo;
import com.jaagro.cbs.api.dto.techconsult.ReturnTechConsultRecordDto;
import com.jaagro.cbs.api.dto.techconsult.TechConsultParamDto;
import com.jaagro.cbs.api.dto.techconsult.UpdateTechConsultDto;

/**
 * @Author gavin
 * @Date 20190228
 */
public interface TechConsultService {

    /**
     * 技术询问分页列表
     *
     * @param queryParam
     * @return
     */
    PageInfo listTechConsultRecords(TechConsultParamDto queryParam);

    /**
     * 根据技术询问主键Id获取详情
     *
     * @param id
     * @return
     */
    ReturnTechConsultRecordDto getDetailTechConsultDtoById(Integer id);

    /**
     * 处理技术申请
     *
     * @param updateTechConsultDto
     * @return
     */
    boolean handleTechConsultRecord(UpdateTechConsultDto updateTechConsultDto);

    /**
     *技术询问列表-技术员APP
     *  @param queryParam
     * @return
     */

    PageInfo listTechConsultRecordsApp(TechConsultParamDto queryParam);
}
