package com.jaagro.cbs.biz.service.impl;

import com.jaagro.cbs.api.dto.base.DeviceIdDto;
import com.jaagro.cbs.api.dto.base.SessionIdDto;
import com.jaagro.cbs.api.dto.plant.CreateCoopDeviceDto;
import com.jaagro.cbs.api.dto.plant.ReturnCoopDeviceDto;
import com.jaagro.cbs.api.model.Coop;
import com.jaagro.cbs.api.model.CoopDevice;
import com.jaagro.cbs.api.model.CoopDeviceExample;
import com.jaagro.cbs.api.service.BreedingCoopDeviceService;
import com.jaagro.cbs.biz.mapper.CoopDeviceMapperExt;
import com.jaagro.cbs.biz.mapper.CoopMapperExt;
import com.jaagro.cbs.biz.utils.JsonUtils;
import com.jaagro.constant.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.*;
import java.net.*;
import java.util.List;

/**
 * @author @Gao.
 * @date 2019-02-26
 */
@Slf4j
@Service
public class BreedingCoopDeviceServiceImpl implements BreedingCoopDeviceService {

    @Autowired
    private CoopDeviceMapperExt coopDeviceMapper;
    @Autowired
    private CoopMapperExt coopMapper;
    @Autowired
    private CurrentUserService currentUserService;

    /**
     * 给鸡舍新增设备进行绑定
     *
     * @return
     * @author @Gao.
     */
    @Override
    public void bindDeviceToCoop(CreateCoopDeviceDto dto) {
        UserInfo currentUser = currentUserService.getCurrentUser();
        CoopDevice coopDevice = new CoopDevice();
        Coop coop = coopMapper.selectByPrimaryKey(dto.getCoopId());
        BeanUtils.copyProperties(dto, coopDevice);
        coopDevice
                .setPlantId(coop.getPlantId())
                .setCreateUserId(currentUser.getId());
        coopDeviceMapper.insertSelective(coopDevice);
    }

    /**
     * 根据养殖场id 查询鸡舍与设备信息
     *
     * @return
     * @author @Gao.
     */
    @Override
    public List<ReturnCoopDeviceDto> listBreedingCoopDevice(Integer plantId) {
        Coop coop = new Coop();
        coop.setPlantId(plantId);
        return coopMapper.listCoopDeviceByCoopExample(coop);
    }

    /**
     * 根据鸡舍id查询设备列表
     *
     * @param coopId
     * @return
     */
    @Override
    public List<CoopDevice> listCoopDeviceByCoopId(Integer coopId) {
        CoopDeviceExample deviceExample = new CoopDeviceExample();
        deviceExample.createCriteria()
                .andCoopIdEqualTo(coopId)
                .andEnableEqualTo(true);
        deviceExample.setOrderByClause("create_time desc");
        return coopDeviceMapper.selectByExample(deviceExample);
    }

    @Override
    public List<DeviceIdDto> listDeviceIdList(Integer coopId) {
        Coop coop = coopMapper.selectByPrimaryKey(coopId);
        if (coop == null) {
            throw new RuntimeException("鸡舍用户名密码错误");
        }
        //返回的设备列表
        List<DeviceIdDto> deviceIdDtoList = null;

        String loginName = coop.getIotUsername();
        String passWord = coop.getIotPassword();
        if (StringUtils.isEmpty(loginName) || StringUtils.isEmpty(passWord)) {
            throw new RuntimeException("鸡舍用户名密码错误");
        }
        //通过 鸡舍登录名和密码获取sessionId
        String sessionId = getSessionId(coop);
        if (StringUtils.isEmpty(sessionId)) {
            throw new RuntimeException("鸡舍用户名密码错误");
        }
        //请求接口
//        String urlAddress = "http://www.ecventpro.uiot.top/APIAction!queryAllEquip.action?sessionId=" + sessionId;
        String urlAddress = "http://www.ecventpro.uiot.top/APIAction!queryAllEquip.action?sessionId=" + sessionId;


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
            httpGet.setHeader("sessionId", sessionId);
            // 执行请求
            response = httpclient.execute(httpGet);
            Header[] allHeaders = response.getAllHeaders();
                for (Header h : allHeaders) {
                System.out.println(h.getName()+"---"+h.getValue());
            }
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
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
//                    ResultDeviceIdDto resultDeviceIdDto = JsonUtils.jsonToPojo(str, ResultDeviceIdDto.class);
//                    if (resultDeviceIdDto != null) {
//                        deviceIdDtoList = resultDeviceIdDto.getList();
//                    }
//                }
            System.out.println(resultString);
        }

//        try {
//            URL url = new URL(urlAddress);
//            //打开和url之间的连接
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            PrintWriter out = null;
//            //请求方式
//            conn.setRequestMethod("POST");
////           //设置通用的请求属性
//            conn.setRequestProperty("sessionId", "547A3E23E9A7759B1E248583E2CF9A3F");
//            conn.setRequestProperty("accept", "*/*");
//            conn.setRequestProperty("connection", "Keep-Alive");
//            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
//            //设置是否向httpUrlConnection输出，设置是否从httpUrlConnection读入，此外发送post请求必须设置这两个
//            //最常用的Http请求无非是get和post，get请求可以获取静态页面，也可以把参数放在URL字串后面，传递给servlet，
//            //post与get的 不同之处在于post的参数不是放在URL字串里面，而是放在http请求的正文内。
//            conn.setDoOutput(true);
//            conn.setDoInput(true);
//            //获取URLConnection对象对应的输出流
//            out = new PrintWriter(conn.getOutputStream());
//            //发送请求参数即数据
//            //缓冲数据
//            out.flush();
//            //获取URLConnection对象对应的输入流
//            InputStream is = conn.getInputStream();
//            //构造一个字符流缓存
//            BufferedReader br = new BufferedReader(new InputStreamReader(is));
//            String str = "";
//            while ((str = br.readLine()) != null) {
////                if (!StringUtils.isEmpty(str)) {
////                    ResultDeviceIdDto resultDeviceIdDto = JsonUtils.jsonToPojo(str, ResultDeviceIdDto.class);
////                    if (resultDeviceIdDto != null) {
////                        deviceIdDtoList = resultDeviceIdDto.getList();
////                    }
////                }
//                System.out.println(str);
//            }
//            //关闭流
//            is.close();
//            conn.disconnect();
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (ProtocolException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        return deviceIdDtoList;
    }


    /**
     * 通过鸡舍的账号密码获得sessionId
     *
     * @param coop
     * @return
     */
    private String getSessionId(Coop coop) {
        String loginName = coop.getIotUsername();
        String passWord = coop.getIotPassword();
        if (StringUtils.isEmpty(loginName) || StringUtils.isEmpty(passWord)) {
            throw new RuntimeException("查无数据");
        }
        String urlAddress = "http://www.ecventpro.uiot.top/APIAction!login.action?loginname=" + loginName + "&password=" + passWord;
//        String urlAddress = "http://www.ecventpro.uiot.top/APIAction!login.action?loginname=G190418&password=12345678";
        try {
            URL url = new URL(urlAddress);
            //打开和url之间的连接
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            PrintWriter out = null;
            //请求方式
            conn.setRequestMethod("POST");
//           //设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
            //设置是否向httpUrlConnection输出，设置是否从httpUrlConnection读入，此外发送post请求必须设置这两个
            //最常用的Http请求无非是get和post，get请求可以获取静态页面，也可以把参数放在URL字串后面，传递给servlet，
            //post与get的 不同之处在于post的参数不是放在URL字串里面，而是放在http请求的正文内。
            conn.setDoOutput(true);
            conn.setDoInput(true);
            //获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            //发送请求参数即数据
            //缓冲数据
            out.flush();
            //获取URLConnection对象对应的输入流
            InputStream is = conn.getInputStream();
            //构造一个字符流缓存
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String str = "";
            while ((str = br.readLine()) != null) {
                if (!StringUtils.isEmpty(str)) {
                    SessionIdDto sessionIdDto = JsonUtils.jsonToPojo(str, SessionIdDto.class);
                    if (sessionIdDto != null) {
                        if ("true".equals(sessionIdDto.getResult())) {
                            return sessionIdDto.getSessionId();
                        }
                    }
                }

            }
            //关闭流
            is.close();
            conn.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

}
