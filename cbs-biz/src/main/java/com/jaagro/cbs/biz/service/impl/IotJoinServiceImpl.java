package com.jaagro.cbs.biz.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jaagro.cbs.api.dto.base.ResultDeviceIdDto;
import com.jaagro.cbs.api.model.Coop;
import com.jaagro.cbs.api.model.CoopDevice;
import com.jaagro.cbs.api.model.CoopDeviceExample;
import com.jaagro.cbs.api.service.IotJoinService;
import com.jaagro.cbs.biz.mapper.CoopDeviceMapperExt;
import com.jaagro.cbs.biz.mapper.CoopMapperExt;
import lombok.extern.log4j.Log4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author tonyZheng
 * @date 2019-04-22 13:13
 */
@Log4j
@Service
public class IotJoinServiceImpl implements IotJoinService {

    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private CoopDeviceMapperExt coopDeviceMapper;
    @Autowired
    private CoopMapperExt coopMapper;

    /**
     * 递归控制字段，如果请求对方api超过3次失败则抛异常
     */
    private int retryCount = 0;

    @Override
    public String getTokenFromFanLong(String loginName, String password) {
        String url = "http://www.ecventpro.uiot.top/APIAction!login.action?loginname=" + loginName + "&password=" + password;
        Map<String, String> result = httpClientFactory(url, null);
        JSONObject jsonObject = JSON.parseObject(result.get("data"));
        Map tokenMap = jsonObject.toJavaObject(Map.class);
        redisTemplate.opsForValue().set("fanLongSessionId", tokenMap.get("sessionId").toString());
        return tokenMap.get("sessionId").toString();
    }

    /**
     * 获取【梵龙】账号下所有我司设备接口
     *
     * @param coopId
     * @return 当前养殖场下所有的环控设备列表（将获取的json直接存入map）
     */
    @Override
    public List<Map<String, String>> getDeviceListFromFanLong(Integer coopId) {
        String sessionId = redisTemplate.opsForValue().get("fanLongSessionId");
        Coop coop = coopMapper.selectByPrimaryKey(coopId);
        if (StringUtils.isEmpty(sessionId)) {
            if (coop == null) {
                throw new RuntimeException("鸡舍不存在");
            }
            if (StringUtils.isEmpty(coop.getIotUsername()) || StringUtils.isEmpty(coop.getIotPassword())) {
                throw new RuntimeException("鸡舍用户名密码错误");
            }
            sessionId = getTokenFromFanLong(coop.getIotUsername(), coop.getIotPassword());
        }
        //请求接口
        String urlAddress = "http://www.ecventpro.uiot.top/APIAction!queryAllEquip.action";
        Map<String, String> clientFactory = httpClientFactory(urlAddress, sessionId);
        // 请求失败
        if (!"200".equals(clientFactory.get("statusCode"))) {
            getTokenFromFanLong(coop.getIotUsername(), coop.getIotPassword());
            retryCount += 1;
            if (retryCount < 3) {
                getDeviceListFromFanLong(coopId);
            }
        }
        JSONObject jsonObject = JSON.parseObject(clientFactory.get("data"));
        String resultString = "";
        if (resultString != null) {
            Map map = jsonObject.toJavaObject(Map.class);
            resultString = map.toString();
            ResultDeviceIdDto resultDeviceIdDto = JSON.parseObject(resultString, ResultDeviceIdDto.class);
            if (resultDeviceIdDto != null) {
                return resultDeviceIdDto.getList();
            }
        }
        return null;
    }

    @Override
    public void createDeviceCurrentDataFromFanLong(String deviceCode) {
        String sessionId = redisTemplate.opsForValue().get("fanLongSessionId");
        String url = "http://www.ecventpro.uiot.top/APIAction!queryCurrentData.action?equipId=" + deviceCode;
        Map<String, String> result = httpClientFactory(url, sessionId);
        System.out.println(result.get("statusCode"));
        if (!"200".equals(result.get("statusCode"))) {
            //通过deviceCode找到coopId
            CoopDeviceExample coopDeviceExample = new CoopDeviceExample();
            coopDeviceExample
                    .createCriteria()
                    .andDeviceCodeEqualTo(deviceCode);
            List<CoopDevice> coopDevices = coopDeviceMapper.selectByExample(coopDeviceExample);
            int coopId = 0;
            if (null != coopDevices) {
                coopId = coopDevices.get(0).getCoopId();
            }
            //通过coopId找到用户名和密码
            Coop coop = coopMapper.selectByPrimaryKey(coopId);
            String iotLoginName = coop.getIotUsername();
            String iotPassword = coop.getIotPassword();
            getTokenFromFanLong(iotLoginName, iotPassword);
            retryCount += 1;
            if (retryCount < 3) {
                log.info("retryCount: " + retryCount);
                createDeviceCurrentDataFromFanLong(deviceCode);
            }
        }
        JSONObject jsonObject = JSON.parseObject(result.get("data"));
        String dataStr = "";
        if (null != jsonObject) {
            Map resultMap = jsonObject.toJavaObject(Map.class);
            dataStr = resultMap.get("list").toString();
        }
        Map<String, String> returnData = new LinkedHashMap<>();
        returnData.put("deviceCode", deviceCode);
        returnData.put("data", dataStr);
        System.out.println(returnData);
    }

    /**
     * @param url 接口地址, 如果url有参数则在请求此api之前将参数拼接在url中
     * @return 结果集
     */
    private Map<String, String> httpClientFactory(String url, String sessionId) {
        RequestConfig config = RequestConfig.custom().setRedirectsEnabled(false).build();
        CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(config).build();
        HttpResponse httpResponse;
        String contents = null;
        String httpStatusCode = null;
        Map<String, String> resultMap = new LinkedHashMap<>();
        HttpGet httpGet = new HttpGet(url);
        if (null != sessionId) {
            BasicCookieStore cookieStore = new BasicCookieStore();
            httpGet.setHeader("Cookie", "JSESSIONID=" + sessionId);
        }
        try {
            httpResponse = httpClient.execute(httpGet);
            contents = EntityUtils.toString(httpResponse.getEntity(), "gbk");
            httpStatusCode = httpResponse.getStatusLine().getStatusCode() + "";
        } catch (IOException e) {
            log.warn("O httpClientFactory: fanLong API request failed url：" + url + ";" + e);
            e.printStackTrace();
        }
        resultMap.put("statusCode", httpStatusCode);
        resultMap.put("data", contents);
        return resultMap;
    }
}
