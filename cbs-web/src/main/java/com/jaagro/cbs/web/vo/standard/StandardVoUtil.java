package com.jaagro.cbs.web.vo.standard;

import com.jaagro.cbs.api.dto.standard.BreedingStandardDrugDto;
import com.jaagro.cbs.api.enums.CapacityUnitEnum;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * 模板VO工具
 * @author yj
 * @date 2019/4/10 17:50
 */
public class StandardVoUtil {
    /**
     * 将药品配置信息按照日龄分组
     * @param breedingStandardDrugListDtoList
     * @return
     */
    public static List<BreedingStandardDrugListVo> generateStandardDrugs(List<BreedingStandardDrugDto> breedingStandardDrugListDtoList){
        List<BreedingStandardDrugListVo> listVoList = new ArrayList<>();
        Set<Integer> dayAgeStart = new HashSet<>();
        if (!CollectionUtils.isEmpty(breedingStandardDrugListDtoList)) {
            breedingStandardDrugListDtoList.forEach(dto -> {
                if (dto.getDayAgeStart() != null) {
                    dayAgeStart.add(dto.getDayAgeStart());
                }
            });
            for (Integer startDayAge : dayAgeStart) {
                BreedingStandardDrugListVo drugListVo = new BreedingStandardDrugListVo();
                drugListVo.setDayAgeStart(startDayAge);
                List<BreedingStandardDrugItemVo> breedingStandardDrugItemVoList = new ArrayList<>();
                drugListVo.setBreedingStandardDrugItemVoList(breedingStandardDrugItemVoList);
                listVoList.add(drugListVo);
            }
            for (BreedingStandardDrugDto drugDto : breedingStandardDrugListDtoList) {
                for (BreedingStandardDrugListVo drugListVo : listVoList) {
                    if (drugDto.getDayAgeStart() != null && drugDto.getDayAgeStart().equals(drugListVo.getDayAgeStart())) {
                        drugListVo.setDayAgeEnd(drugDto.getDayAgeEnd())
                                .setStandardId(drugDto.getBreedingStandardId())
                                .setStopDrugFlag(drugDto.getStopDrugFlag());
                        if (!drugDto.getStopDrugFlag()) {
                            List<BreedingStandardDrugItemVo> drugItemVoList = drugListVo.getBreedingStandardDrugItemVoList();
                            BreedingStandardDrugItemVo drugItemVo = new BreedingStandardDrugItemVo();
                            drugItemVo.setCapacityUnit(CapacityUnitEnum.getTypeByCode(drugDto.getCapacityUnit()))
                                    .setFeedVolume(drugDto.getFeedVolume())
                                    .setId(drugDto.getId())
                                    .setProductId(drugDto.getProductId())
                                    .setSkuNo(drugDto.getSkuNo())
                                    .setProductName(drugDto.getProductName());
                            drugItemVoList.add(drugItemVo);
                        }
                    }
                }
            }
        }
        listVoList.sort(Comparator.comparingInt(BreedingStandardDrugListVo::getDayAgeStart));
        return listVoList;
    }
}
