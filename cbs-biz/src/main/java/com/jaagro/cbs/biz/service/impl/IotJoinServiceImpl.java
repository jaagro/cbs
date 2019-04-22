package com.jaagro.cbs.biz.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jaagro.cbs.api.service.IotJoinService;
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

import java.io.IOException;
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

    @Override
    public String getTokenFromFanLong() {
        String url = "http://www.ecventpro.uiot.top/APIAction!login.action";
        String username = "G190418";
        String password = "12345678";
        String tokenStr = httpClientFactory(url, username, password);
        JSONObject jsonObject = JSON.parseObject(tokenStr);
        Map tokenMap = jsonObject.toJavaObject(Map.class);
        return tokenMap.get("sessionId").toString();
    }

    @Override
    public Map<String, String> getDeviceListFromFanLong() {
        return null;
    }

    @Override
    public Map<String, String> getDeviceCurrentDataByDeviceCodeFromFanLong(Integer deviceCode) {
        return null;
    }

    private String httpClientFactory(String url, String... ages) {
        RequestConfig config = RequestConfig.custom().setRedirectsEnabled(false).build();
        CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(config).build();

        if (ages.length < 1) {
            throw new RuntimeException("pNumber of invalid parameters");
        }
        if (ages.length == 1) {
            HttpGet httpGet = new HttpGet(url);
            BasicCookieStore cookieStore = new BasicCookieStore();
            httpGet.setHeader("Cookie", "JSESSIONID=" + ages[0]);
            HttpResponse httpResponse;
            String contents = null;
            try {
                httpResponse = httpClient.execute(httpGet);
                contents = EntityUtils.toString(httpResponse.getEntity(), "gbk");
            } catch (IOException e) {
                log.warn("O httpClientFactory: fanLong API request failed url：" + url + "; ages:" + ages[0] + ";" + e);
                e.printStackTrace();
            }
            return contents;
        }
        if (ages.length == 2) {
            url = url + "?loginname=" + ages[0] + "&password=" + ages[1];
            HttpGet httpGet = new HttpGet(url);
            HttpResponse httpResponse;
            String contents = null;
            try {
                httpResponse = httpClient.execute(httpGet);
                contents = EntityUtils.toString(httpResponse.getEntity(), "gbk");
            } catch (IOException e) {
                log.warn("O httpClientFactory: fanLong Login API request failed url：" + url + "; ages1:" + ages[0] + ", ages2" + ages[1] + ";" + e);
                e.printStackTrace();
            }
            return contents;
        }
        return null;
    }
}
