package com.jaagro.cbs.biz.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jaagro.cbs.api.dto.base.ResultDeviceIdDto;
import com.jaagro.cbs.api.model.Coop;
import com.jaagro.cbs.api.service.IotJoinService;
import com.jaagro.cbs.biz.mapper.CoopMapperExt;
import com.jaagro.cbs.biz.utils.JsonUtils;
import lombok.extern.log4j.Log4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.URI;
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
    private CoopMapperExt coopMapper;

    @Override
    public String getTokenFromFanLong(String loginName, String password) {
        String url = "http://www.ecventpro.uiot.top/APIAction!login.action";
        Map<String, Object> result = httpClientFactory(url, null, loginName, password);
        JSONObject jsonObject = JSON.parseObject(result.get("data").toString());
        Map tokenMap = jsonObject.toJavaObject(Map.class);
        redisTemplate.opsForValue().set("fanLongSessionId", tokenMap.get("sessionId").toString());
        return tokenMap.get("sessionId").toString();
    }

    @Override
    public List<Map<String, String>> getDeviceListFromFanLong(Integer coopId) {
        String sessionId = redisTemplate.opsForValue().get("fanLongSessionId");
        if (StringUtils.isEmpty(sessionId)) {
            Coop coop = coopMapper.selectByPrimaryKey(coopId);
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
        // 创建Httpclient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();
        String resultString = "";
        CloseableHttpResponse response = null;
        try {
            // 创建uri
            URIBuilder builder = new URIBuilder(urlAddress);
            URI uri = builder.build();
            // 创建http GET请求
            HttpGet httpGet = new HttpGet(uri);
            httpGet.setHeader(new BasicHeader("Cookie", "JSESSIONID=" + sessionId));
            // 执行请求
            response = httpclient.execute(httpGet);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
            } else {
                throw new RuntimeException("获取第三方设备列表失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (!StringUtils.isEmpty(resultString)) {
            ResultDeviceIdDto resultDeviceIdDto = JsonUtils.jsonToPojo(resultString, ResultDeviceIdDto.class);
            if (resultDeviceIdDto != null) {
                List<Map<String, String>> mapList = resultDeviceIdDto.getList();
                return mapList;
            }
        }
        return null;
    }

    @Override
    public Map<String, String> getDeviceCurrentDataByDeviceCodeFromFanLong(Integer deviceCode) {
        return null;
    }

    /**
     * @param url  接口地址,
     * @param ages 可变参数
     * @return 结果集
     */
    private Map<String, Object> httpClientFactory(String url, String sessionId, String... ages) {
        RequestConfig config = RequestConfig.custom().setRedirectsEnabled(false).build();
        CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(config).build();
        HttpResponse httpResponse;
        String contents = null;
        int httpStatusCode = 0;
        Map<String, Object> resultMap = new LinkedHashMap<>();
        if (sessionId != null) {
            HttpGet httpGet = new HttpGet(url);
            BasicCookieStore cookieStore = new BasicCookieStore();
            httpGet.setHeader("Cookie", "JSESSIONID=" + sessionId);
            try {
                httpResponse = httpClient.execute(httpGet);
                contents = EntityUtils.toString(httpResponse.getEntity(), "gbk");
                httpStatusCode = httpResponse.getStatusLine().getStatusCode();
            } catch (IOException e) {
                log.warn("O httpClientFactory: fanLong API request failed url：" + url + "; ages:" + ages[0] + ";" + e);
                e.printStackTrace();
            }
            resultMap.put("code", httpStatusCode);
            resultMap.put("data", contents);
            return resultMap;
        } else {
            if (ages.length != 2) {
                throw new RuntimeException("Incorrect number of parameters");
            }
            url = url + "?loginname=" + ages[0] + "&password=" + ages[1];
            HttpGet httpGet = new HttpGet(url);
            try {
                httpResponse = httpClient.execute(httpGet);
                httpStatusCode = httpResponse.getStatusLine().getStatusCode();
                contents = EntityUtils.toString(httpResponse.getEntity(), "gbk");
            } catch (IOException e) {
                log.warn("O httpClientFactory: fanLong Login API request failed url：" + url + "; ages1:" + ages[0] + ", ages2" + ages[1] + ";" + e);
                e.printStackTrace();
            }
            resultMap.put("code", httpStatusCode);
            resultMap.put("data", contents);
            return resultMap;
        }
    }
}
