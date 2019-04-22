package com.jaagro.cbs.web.controller;

import com.jaagro.cbs.api.enums.ParameterStatusEnum;
import com.jaagro.cbs.api.model.BatchPlantCoop;
import com.jaagro.cbs.api.model.Product;
import com.jaagro.cbs.api.model.ProductExample;
import com.jaagro.cbs.api.service.BreedingFarmerService;
import com.jaagro.cbs.api.service.BreedingPlanService;
import com.jaagro.cbs.api.service.IotJoinService;
import com.jaagro.cbs.biz.mapper.BatchPlantCoopMapperExt;
import com.jaagro.cbs.biz.mapper.BreedingPlanMapperExt;
import com.jaagro.cbs.biz.mapper.ProductMapperExt;
import com.jaagro.cbs.biz.schedule.BreedingPlanOverdueSchedule;
import com.jaagro.cbs.biz.utils.SequenceCodeUtils;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author tonyZheng
 * @date 2019-02-21 14:45
 */
@RestController
public class TestController {

    @Autowired
    private ProductMapperExt productMapperExt;
    @Autowired
    private SequenceCodeUtils sequenceCodeUtils;
    @Autowired
    private BatchPlantCoopMapperExt batchPlantCoopMapper;
    @Autowired
    private BreedingPlanService breedingPlanService;
    @Autowired
    private BreedingFarmerService breedingFarmerService;
    @Autowired
    private BreedingPlanMapperExt breedingPlanMapper;
    @Autowired
    private BreedingPlanOverdueSchedule breedingPlanOverdueSchedule;

    @PostMapping("/createProduct")
    public void createProduct() {
        Product product = new Product();
        product
                .setProductName("毒鼠强")
                .setProductType(1)
                .setManufacturer("卖毒药的");

        productMapperExt.insertSelective(product);
        System.out.println(product.getId());
    }

    @GetMapping("/getProduct")
    public List<Product> getProduct() {
        ProductExample productExample = new ProductExample();
        productExample.createCriteria()
                .andProductNameLike("毒%")
                .andProductTypeEqualTo(1);
        return productMapperExt.selectByExample(productExample);
    }

    @GetMapping("/test1")
    public void test1() {
        String at = sequenceCodeUtils.genSeqCode("AT");
        System.out.println(at);
        return;
    }

    @GetMapping("/testInsertBatchCoop")
    public void testInsertBatchCoop() {
        List<BatchPlantCoop> list = new ArrayList<>();
        BatchPlantCoop coop = new BatchPlantCoop();
        coop.setCreateTime(new Date())
                .setCreateUserId(1)
                .setEnable(Boolean.TRUE)
                .setPlantId(999)
                .setCoopId(999)
                .setPlanId(999);
        BatchPlantCoop coop1 = new BatchPlantCoop();
        BeanUtils.copyProperties(coop, coop1);
        list.add(coop);
        list.add(coop1);
        batchPlantCoopMapper.insertBatch(list);
    }

    @GetMapping("/teettta/{planID}")
    public void test(@PathVariable Integer planID) throws Exception {
        //BreedingPlan breedingPlan = breedingPlanMapper.selectByPrimaryKey(planID);
        breedingPlanOverdueSchedule.cancelBreedingPlanOverdue();

        //System.out.println(breedingFarmerService.getDayAge(breedingPlan.getPlanTime()));
        // System.out.println(breedingPlanService.getDayAge(planID));
    }

    @GetMapping("/test2")
    public String test2() throws IOException, URISyntaxException {
        return null;
    }


    @Autowired
    IotJoinService iotJoinService;

    @GetMapping("/test3/{loginName}/{password}")
    public String test3(@PathVariable("password") String password, @PathVariable("loginName") String loginName) {
        return iotJoinService.getTokenFromFanLong(loginName, password);
    }

    @GetMapping("/test4/{deviceCode}")
    public Map<String, String> test4(@PathVariable("deviceCode") String deviceCode){
        return iotJoinService.getDeviceCurrentDataByDeviceCodeFromFanLong(deviceCode);
    }
}
