package com.jaagro.cbs.biz.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;


/**
 * @author @Gao.
 * redis分布式锁
 */
@Slf4j
@Component
public class RedisLock {
    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 加锁
     *
     * @param key
     * @param value    当前时间 + 超时时间
     * @param timeout  超时时间
     * @param timeUnit 时间单位
     * @return
     */
    public boolean lock(String key, String value, Integer timeout, TimeUnit timeUnit) {
        if (redisTemplate.opsForValue().setIfAbsent(key, value)) {
            if (timeout == null) {
                timeout = 10;
            }
            if (timeUnit == null) {
                timeUnit = TimeUnit.MINUTES;
            }
            redisTemplate.expire(key, timeout, timeUnit);
            return true;
        }
        String currentValue = redisTemplate.opsForValue().get(key);
        if (!StringUtils.isEmpty(currentValue) && Long.parseLong(currentValue) < System.currentTimeMillis()) {
            //获取上一个锁的时间
            String oldValue = redisTemplate.opsForValue().getAndSet(key, value);
            if (!StringUtils.isEmpty(oldValue) && oldValue.equals(currentValue)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 解锁
     *
     * @param key
     * @return
     */
    public boolean unLock(String key) {
        try {
            redisTemplate.delete(key);
        } catch (Exception e) {
            log.error("O unLock error:{}", e);
        }
        return true;
    }
}
