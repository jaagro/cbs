package com.jaagro.cbs.biz.service.impl;

import com.jaagro.cbs.api.dto.base.ShowCustomerDto;
import com.jaagro.cbs.api.dto.plant.*;
import com.jaagro.cbs.api.enums.CoopStatusEnum;
import com.jaagro.cbs.api.exception.BusinessException;
import com.jaagro.cbs.api.model.Coop;
import com.jaagro.cbs.api.model.CoopExample;
import com.jaagro.cbs.api.model.Plant;
import com.jaagro.cbs.api.model.PlantExample;
import com.jaagro.cbs.api.service.BreedingPlantService;
import com.jaagro.cbs.biz.mapper.CoopDeviceMapperExt;
import com.jaagro.cbs.biz.mapper.CoopMapperExt;
import com.jaagro.cbs.biz.mapper.PlantImageMapperExt;
import com.jaagro.cbs.biz.mapper.PlantMapperExt;
import com.jaagro.cbs.biz.service.CustomerClientService;
import com.jaagro.cbs.biz.service.OssSignUrlClientService;
import com.jaagro.cbs.biz.utils.SequenceCodeUtils;
import com.jaagro.utils.ServiceResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author baiyiran
 * @Date 2019/2/22
 */
@Slf4j
@Service
public class BreedingPlantServiceImpl implements BreedingPlantService {

    @Autowired
    private SequenceCodeUtils sequenceCodeUtils;
    @Autowired
    private CurrentUserService currentUserService;
    @Autowired
    private CustomerClientService customerClientService;
    @Autowired
    private OssSignUrlClientService ossSignUrlClientService;
    @Autowired
    private PlantMapperExt plantMapper;
    @Autowired
    private CoopMapperExt coopMapperExt;
    @Autowired
    private PlantImageMapperExt plantImageMapper;
    @Autowired
    private CoopDeviceMapperExt coopDeviceMapper;
    @Autowired
    private BatchPlantCoopServiceImpl batchPlantCoopService;


    /**
     * 新增
     *
     * @param plantDto
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean createPlant(CreatePlantDto plantDto) {
        Plant plant = new Plant();
        BeanUtils.copyProperties(plantDto, plant);
        if (StringUtils.isEmpty(plant.getCustomerId())) {
            throw new NullPointerException("养殖户id不能为空");
        }
        ShowCustomerDto showCustomerById = customerClientService.getShowCustomerById(plant.getCustomerId());
        if (showCustomerById == null) {
            throw new NullPointerException("养殖户不存在");
        }
        ShowCustomerDto customer = customerClientService.getShowCustomerById(showCustomerById.getId());
        if (customer != null) {
            plant.setTenantId(customer.getTenantId());
        }
        plant.setCreateUserId(currentUserService.getCurrentUser().getId());
        plantMapper.insertSelective(plant);
        if (plant.getId() != null && !CollectionUtils.isEmpty(plantDto.getImageDtoList())) {
            for (CreatePlantImageDto imageDto : plantDto.getImageDtoList()) {
                imageDto.setCreateUserId(currentUserService.getCurrentUser().getId())
                        .setPlantId(plant.getId());
            }
            //批量新增图片
            plantImageMapper.insertBatch(plantDto.getImageDtoList());
        }
        return true;
    }

    /**
     * 修改
     *
     * @param plantDto
     * @return
     */
    @Override
    public Map<String, Object> updatePlant(UpdatePlantDto plantDto) {
        Plant plant = new Plant();
        BeanUtils.copyProperties(plantDto, plant);
        int result = plantMapper.updateByPrimaryKeySelective(plant);
        if (result > 0) {
            return ServiceResult.toResult("修改成功");
        } else {
            return ServiceResult.toResult("修改失败");
        }
    }

    /**
     * 通过养殖id获取养殖场详情
     *
     * @param id
     * @return
     */
    @Override
    public ReturnPlantDto getPlantDetailsById(Integer id) {
        Plant plant = plantMapper.selectByPrimaryKey(id);
        if (plant == null) {
            return null;
        }
        ReturnPlantDto returnPlantDto = new ReturnPlantDto();
        BeanUtils.copyProperties(plant, returnPlantDto);
        //填充养殖场图片
        List<ReturnPlantImageDto> imageDtoList = plantImageMapper.listByPlantId(plant.getId());
        if (!CollectionUtils.isEmpty(imageDtoList)) {
            for (ReturnPlantImageDto plantImageDto : imageDtoList) {
                //替换资质证照地址
                String[] strArray = {plantImageDto.getImageUrl()};
                List<URL> urlList = ossSignUrlClientService.listSignedUrl(strArray);
                plantImageDto.setImageUrl(urlList.get(0).toString());
            }
            returnPlantDto.setPlantImageDtoList(imageDtoList);
        }
        //填充养殖场鸡舍数量
        returnPlantDto.setCoopCount(getCoopsCountByPlant(returnPlantDto.getId()));
        return returnPlantDto;
    }

    /**
     * 根据养殖计划id 获取养殖场信息
     *
     * @param plantId
     * @return
     */
    @Override
    public List<Plant> listPlantInfoByPlanId(Integer plantId) {
        List<Plant> plants = null;
        List<Integer> plantIds = batchPlantCoopService.listPlantIdByPlanId(plantId);
        if (!CollectionUtils.isEmpty(plantIds)) {
            PlantExample plantExample = new PlantExample();
            plantExample.createCriteria()
                    .andIdIn(plantIds)
                    .andEnableEqualTo(true);
            plants = plantMapper.selectByExample(plantExample);
        }
        return plants;
    }

    /**
     * 通过养殖户id获取养殖场列表
     *
     * @param customerId
     * @return
     */
    @Override
    public List<ReturnPlantDto> listPlantByCustomerId(Integer customerId) {
        ShowCustomerDto showCustomerById = customerClientService.getShowCustomerById(customerId);
        if (showCustomerById == null) {
            throw new NullPointerException("养殖户不存在");
        }
        PlantExample plantExample = new PlantExample();
        plantExample.createCriteria()
                .andCustomerIdEqualTo(customerId)
                .andEnableEqualTo(true);
        plantExample.setOrderByClause("create_time desc");
        List<Plant> plants = plantMapper.selectByExample(plantExample);
        List<ReturnPlantDto> returnPlantDtoList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(plants)) {
            for (Plant plant : plants) {
                ReturnPlantDto plantDto = new ReturnPlantDto();
                BeanUtils.copyProperties(plant, plantDto);
                //填充养殖场图片
                List<ReturnPlantImageDto> imageDtoList = plantImageMapper.listByPlantId(plant.getId());
                if (!CollectionUtils.isEmpty(imageDtoList)) {
                    for (ReturnPlantImageDto plantImageDto : imageDtoList) {
                        String[] strArray = {plantImageDto.getImageUrl()};
                        plantImageDto.setImageUrl(ossSignUrlClientService.listSignedUrl(strArray).get(0).toString());
                    }
                    plantDto.setPlantImageDtoList(imageDtoList);
                }
                //填充养殖场鸡舍数量
                Integer coopCount = getCoopsCountByPlant(plant.getId());
                plantDto.setCoopCount(coopCount);
                //查询鸡舍信息
                if (plant.getId() != null) {
                    List<ReturnCoopDto> returnCoopDtos = listCoopByPlantId(plant.getId());
                    plantDto.setReturnCoopDtos(returnCoopDtos);
                }
                returnPlantDtoList.add(plantDto);
            }
        }
        return returnPlantDtoList;
    }

    /**
     * 通过养殖场id获得鸡舍总数
     *
     * @param plantId
     * @return
     */
    private Integer getCoopsCountByPlant(Integer plantId) {
        if (plantId == null) {
            return 0;
        }
        CoopExample coopExample = new CoopExample();
        coopExample.createCriteria()
                .andPlantIdEqualTo(plantId);
        return coopMapperExt.countByExample(coopExample);
    }

    /**
     * 新增鸡舍
     *
     * @param coopDto
     * @return
     */
    @Override
    public Map<String, Object> createCoop(CreateCoopDto coopDto) {
        Plant plant = plantMapper.selectByPrimaryKey(coopDto.getPlantId());
        if (plant == null) {
            throw new BusinessException("养殖场不存在");
        }
        Coop coop = new Coop();
        BeanUtils.copyProperties(coopDto, coop);
        coop
                .setCoopNo(sequenceCodeUtils.genSeqCode("CP"))
                .setCoopStatus(CoopStatusEnum.LEISURE.getCode())
                .setCustomerId(plant.getCustomerId())
                .setCreateUserId(currentUserService.getCurrentUser().getId());
        int result = coopMapperExt.insertSelective(coop);
        if (result > 0) {

            return ServiceResult.toResult("创建成功");
        }
        return ServiceResult.error("创建失败");
    }

    /**
     * 逻辑删除鸡舍
     *
     * @param coopId
     * @author @Gao.
     */
    @Override
    public void deleteCoop(Integer coopId) {
        //逻辑删除鸡舍
        Coop coop = new Coop();
        coop
                .setId(coopId)
                .setEnable(false);
        coopMapperExt.updateByPrimaryKeySelective(coop);
        //逻辑删除鸡舍下的设备
        coopDeviceMapper.logicDeleteCoopDeviceByCoopId(coopId);
    }


    /**
     * 通过养殖场id获得鸡舍列表
     *
     * @param plantId
     * @return
     */
    @Override
    public List<ReturnCoopDto> listCoopByPlantId(Integer plantId) {
        CoopExample coopExample = new CoopExample();
        coopExample.createCriteria().andPlantIdEqualTo(plantId);
        List<Coop> coops = coopMapperExt.selectByExample(coopExample);
        List<ReturnCoopDto> returnCoopDtoList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(coops)) {
            for (Coop coop : coops) {
                ReturnCoopDto returnCoopDto = new ReturnCoopDto();
                BeanUtils.copyProperties(coop, returnCoopDto);
                returnCoopDtoList.add(returnCoopDto);
            }
        }
        return returnCoopDtoList;
    }


    /**
     * 通过养殖户id获取养殖场列表
     *
     * @param
     * @return
     * @author @Gao.
     */
    @Override
    public List<ReturnPlantDto> listPlantAndCoopByPlantIds(List<Integer> planIds) {

        PlantExample plantExample = new PlantExample();
        plantExample.createCriteria()
                .andIdIn(planIds)
                .andEnableEqualTo(true);
        plantExample.setOrderByClause("create_time desc");
        List<Plant> plants = plantMapper.selectByExample(plantExample);
        List<ReturnPlantDto> returnPlantDtoList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(plants)) {
            for (Plant plant : plants) {
                ReturnPlantDto plantDto = new ReturnPlantDto();
                BeanUtils.copyProperties(plant, plantDto);
                //查询鸡舍信息
                if (plant.getId() != null) {
                    List<ReturnCoopDto> returnCoopDtos = listCoopByPlantId(plant.getId());
                    plantDto.setReturnCoopDtos(returnCoopDtos);
                }
                returnPlantDtoList.add(plantDto);
            }
        }
        return returnPlantDtoList;
    }

    /**
     * 查询当前客户空闲的养殖场
     *
     * @param customerId
     * @return
     */
    @Override
    public List<ReturnBasicPlantDto> listFreePlantByCustomerId(Integer customerId) {
        List<ReturnBasicPlantDto> returnBasicPlantDtos = new ArrayList<>();
        List<ReturnPlantDto> returnPlantDtos = listPlantByCustomerId(customerId);
        if (!CollectionUtils.isEmpty(returnPlantDtos)) {
            for (ReturnPlantDto returnPlantDto : returnPlantDtos) {
                if (CollectionUtils.isEmpty(returnPlantDto.getReturnCoopDtos())) {
                    continue;
                }
                ReturnBasicPlantDto returnBasicPlantDto = new ReturnBasicPlantDto();
                if (returnPlantDto.getPlantName() != null) {
                    returnBasicPlantDto
                            .setPlantName(returnPlantDto.getPlantName());
                }
                if (returnPlantDto.getId() != null) {
                    returnBasicPlantDto
                            .setId(returnPlantDto.getId());
                }
                returnBasicPlantDtos.add(returnBasicPlantDto);
            }
        }
        return returnBasicPlantDtos;
    }
}
