package com.jaagro.cbs.biz.utils;

import com.jaagro.cbs.api.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author @Gao.
 * <p>
 * 生成序列单号
 */
@Slf4j
@Component
public class SequenceCodeUtils {

    @Autowired
    private RedisTemplate redisTemplate;

    private static final int START_LENGTH = 9;
    private static final int ZERO_START_LENGTH = 4;
    private static final String ZERO_STRING = "00000000000000";


    /**
     * 生成唯一长度的序列号 格式 前缀+时间日期+随机数+自增键
     *
     * @param prefix
     * @return
     */
    public String genSeqCode(String prefix) {
        StringBuilder genSeqCode = new StringBuilder();
        String date = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE);
        String currentDate = date.substring(2, date.length());
        RedisAtomicLong entityIdCounter;
        try {
            entityIdCounter = new RedisAtomicLong(prefix + currentDate, redisTemplate.getConnectionFactory());
        } catch (Exception e) {
            log.info("O genSeqCode entityIdCounter param is null");
            throw new BusinessException("生成序列号失败");
        }
        long incrementId = entityIdCounter.getAndIncrement() + 1;
        if (incrementId == 1) {
            entityIdCounter.expire(48, TimeUnit.HOURS);
        }
        int zeroLength = ZERO_START_LENGTH - Long.valueOf(incrementId).toString().length();
        genSeqCode.append(prefix).append(currentDate).append(ZERO_STRING.substring(0, zeroLength)).append(incrementId);
        return genSeqCode.toString();
    }

    /**
     * 生成随机数
     *
     * @param strLength
     * @return
     */
    private String fixLengthString(int strLength) {
        Random rm = new Random();
        // 获得随机数
        double random = (1 + rm.nextDouble()) * Math.pow(10, strLength);
        // 将获得的获得随机数转化为字符串
        String fixLengthString = String.valueOf(random);
        // 返回固定的长度的随机数
        return fixLengthString.substring(2, strLength + 1);
    }
}
