package com.jaagro.cbs.biz.utils;

import com.jaagro.cbs.biz.service.OssSignUrlClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author Gavin
 * 富文本的图片的路径、OSS的文件路径处理工具
 */
@Component
public class UrlPathUtil {

    private static Pattern pattern = Pattern.compile("src\\s*=\\s*\"?(.*?)(\"|>|\\s+)");

    private static OssSignUrlClientService ossSignUrlClientService;

    @Autowired
    public void initsetOssSignUrlClientService(OssSignUrlClientService ossSignUrlClientService) {
        UrlPathUtil.ossSignUrlClientService = ossSignUrlClientService;
    }

    public static String getAbstractImageUrl(String relativeImageUrl) {
        if (StringUtils.hasText(relativeImageUrl)) {
            String[] strArray = {relativeImageUrl};
            List<URL> urls = ossSignUrlClientService.listSignedUrl(strArray);
            if (!CollectionUtils.isEmpty(urls)) {
                return urls.get(0).toString();
            }
        }
        return null;
    }


    public static String replaceImageUrl(List<String> imgStr, String content) {
        if (!CollectionUtils.isEmpty(imgStr) && StringUtils.hasText(content)) {
            for (String imgUrl : imgStr) {
                String abstractImgUrl = getAbstractImageUrl(imgUrl);
                content = content.replace(imgUrl, abstractImgUrl);
            }
        }
        return content;
    }

    /**
     * @param htmlStr
     * @return
     */
    public static List<String> getImgUrl(String htmlStr) {
        List<String> pics = new ArrayList<>();
        String img;
        String imgRegEx = "<.*img.*src\\s*=\\s*(.*?)[^>]*?>";
        Pattern pImage = Pattern.compile(imgRegEx, Pattern.CASE_INSENSITIVE);
        Matcher mImage = pImage.matcher(htmlStr);

        while (mImage.find()) {
            // 得到<img />数据
            img = mImage.group();
            // 匹配<img>中的src数据
            Matcher m = pattern.matcher(img);
            while (m.find()) {
                pics.add(m.group(1));
            }
        }
        return pics;
    }
}
