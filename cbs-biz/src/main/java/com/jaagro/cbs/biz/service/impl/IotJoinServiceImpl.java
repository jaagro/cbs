package com.jaagro.cbs.biz.service.impl;

import cn.jpush.api.device.DeviceClient;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.annotations.VisibleForTesting;
import com.jaagro.cbs.api.dto.base.ResultDeviceIdDto;
import com.jaagro.cbs.api.model.Coop;
import com.jaagro.cbs.api.model.CoopDevice;
import com.jaagro.cbs.api.model.CoopDeviceExample;
import com.jaagro.cbs.api.model.DeviceHistoryData;
import com.jaagro.cbs.api.service.IotJoinService;
import com.jaagro.cbs.biz.bo.FanLongIotDeviceBo;
import com.jaagro.cbs.biz.config.RabbitMqConfig;
import com.jaagro.cbs.biz.mapper.CoopDeviceMapperExt;
import com.jaagro.cbs.biz.mapper.CoopMapperExt;
import com.jaagro.cbs.biz.mapper.DeviceHistoryDataMapperExt;
import lombok.extern.log4j.Log4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.ArrayList;
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
    @Autowired
    private AmqpTemplate amqpTemplate;
    @Autowired
    private DeviceHistoryDataMapperExt deviceHistoryDataMapper;

    /**
     * 递归控制字段，如果请求对方api超过3次失败则抛异常
     */
    private int retryCount = 0;

    private static final String FL_ROOT_LOGIN_NAME = "janm";
    private static final String FL_ROOT_PASSWORD = "jaagro888888";

    private String deviceListStr = null;

    @Override
    public String getTokenFromFanLong(String loginName, String password, Integer... loginType) {

        String url = "http://www.ecventpro.uiot.top/APIAction!login.action?loginname=" + loginName + "&password=" + password;
        Map<String, String> result = httpClientFactory(url, null);
        JSONObject jsonObject = JSON.parseObject(result.get("data"));
        Map tokenMap = jsonObject.toJavaObject(Map.class);

        if (loginType.length > 0 && loginType[0] == 1) {
            redisTemplate.opsForValue().set("fanLongRootSessionId", tokenMap.get("sessionId").toString());
        } else {
            redisTemplate.opsForValue().set("fanLongSessionId", tokenMap.get("sessionId").toString());
        }
        return tokenMap.get("sessionId").toString();
    }

    @Override
    public String getDeviceListFromFanLong(Integer coopId, Integer... loginType) {

        String sessionId;
        boolean isRoot = loginType.length > 0 && loginType[0] == 1;
        if (isRoot) {
            sessionId = redisTemplate.opsForValue().get("fanLongRootSessionId");
        } else {
            sessionId = redisTemplate.opsForValue().get("fanLongSessionId");
        }
        //请求接口
        String urlAddress = "http://www.ecventpro.uiot.top/APIAction!queryAllEquip.action";
        Map<String, String> clientFactory = httpClientFactory(urlAddress, sessionId);
        // 请求失败
        if (!"200".equals(clientFactory.get("statusCode"))) {
            if (isRoot) {
                log.debug("O getDeviceListFromFanLong this user is root");
                getTokenFromFanLong(FL_ROOT_LOGIN_NAME, FL_ROOT_PASSWORD, 1);
            } else {
                Coop coop = coopMapper.selectByPrimaryKey(coopId);
                if (coop == null) {
                    throw new RuntimeException("鸡舍不存在");
                }
                if (StringUtils.isEmpty(coop.getIotUsername()) || StringUtils.isEmpty(coop.getIotPassword())) {
                    throw new RuntimeException("鸡舍用户名密码错误");
                }
                getTokenFromFanLong(coop.getIotUsername(), coop.getIotPassword());
            }
            retryCount += 1;
            if (retryCount < 3) {
                getDeviceListFromFanLong(coopId, loginType);
            }
        }
        JSONObject jsonObject = JSON.parseObject(clientFactory.get("data"));
        if (null != jsonObject) {
            Map map = jsonObject.toJavaObject(Map.class);
            deviceListStr = map.get("list").toString();
        }
        return deviceListStr;
    }

    @Override
    public void createDeviceCurrentDataFromFanLong() {
        List<FanLongIotDeviceBo> deviceBoList = JSON.parseArray(getDeviceListFromFanLong(1, 1), FanLongIotDeviceBo.class);
        if (CollectionUtils.isEmpty(deviceBoList)) {
            throw new NullPointerException("deviceBoList must not be null");
        }
        String sessionId = redisTemplate.opsForValue().get("fanLongSessionId");

        for (FanLongIotDeviceBo device : deviceBoList) {
            String url = "http://www.ecventpro.uiot.top/APIAction!queryCurrentData.action?equipId=" + device.getId();
            Map<String, String> result = httpClientFactory(url, sessionId);
            if (!"200".equals(result.get("statusCode"))) {
                //通过deviceCode找到coopId
                CoopDeviceExample coopDeviceExample = new CoopDeviceExample();
                coopDeviceExample
                        .createCriteria()
                        .andDeviceCodeEqualTo(device.getId().toString());
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
                    createDeviceCurrentDataFromFanLong();
                }
            }
            JSONObject jsonObject = JSON.parseObject(result.get("data"));
            String dataStr = "";
            if (null != jsonObject) {
                Map resultMap = jsonObject.toJavaObject(Map.class);
                dataStr = resultMap.get("list").toString();
            }
            Map<String, String> returnData = new LinkedHashMap<>();
            returnData.put("deviceCode", device.getId().toString());
            returnData.put("data", dataStr);
            if (!StringUtils.isEmpty(returnData.get("data"))) {
                amqpTemplate.convertAndSend(RabbitMqConfig.TOPIC_EXCHANGE, "fanLong_iot.send", returnData);
                log.debug("R createDeviceCurrentDataFromFanLong dataSet: " + returnData);
            }
        }
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

    @RabbitListener(queues = RabbitMqConfig.FAN_LONG_IOT_QUEUE)
    private void receiveMessageFromFanLong(Map<String, String> data) {
        DeviceHistoryData deviceHistoryData = new DeviceHistoryData();
        deviceHistoryData
                .setDeviceCode(data.get("deviceCode"))
                .setDataJson(data.get("data"));
        try {
            deviceHistoryDataMapper.insertSelective(deviceHistoryData);
        } catch (Exception ex) {
            log.error("R receiveMessageFromFanLong Mq consume failed, throw exception: " + ex);
            ex.printStackTrace();
        }
        log.info("R receiveMessageFromFanLong Mq consume success");
    }
}
