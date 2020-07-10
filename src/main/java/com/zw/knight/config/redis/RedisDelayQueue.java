/*
 * Copyright (c) 2018.掌阅科技
 * All rights reserved.
 */

package com.zw.knight.config.redis;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Date;
import java.util.Set;

/**
 * redis 延时队列
 *
 * @author WangBonan
 * @date 2018/5/24
 */
public class RedisDelayQueue implements IDelayQueue<String> {

    //redis 模板
    private RedisTemplate<String, String> redisQueueTemplate;

    //队列名称
    private String key;

    public RedisDelayQueue(String key, RedisTemplate<String, String> redisQueueTemplate) {
        this.key = key;
        this.redisQueueTemplate = redisQueueTemplate;
    }

    @Override
    public boolean add(long score, String data) {
        try {
            return redisQueueTemplate.opsForZSet().add(key, data, score);
        } catch (Exception e) {
            RedisConnectionFactory redisConnectionFactory = redisQueueTemplate.getConnectionFactory();
            if (!(redisConnectionFactory instanceof JedisConnectionFactory)) {
                throw e;
            }
            JedisConnectionFactory connectionFactory = (JedisConnectionFactory) redisConnectionFactory;
            String host = connectionFactory.getHostName();
            int port = connectionFactory.getPort();
            RedisErrorLog errorLog = new RedisErrorLog("", host, port, connectionFactory.getDatabase());
            errorLog.addZaddOp(key, score, data);
            RedisLogUtils.redisLog(errorLog);
            throw e;
        }
    }

    @Override
    public String get() {
        double now = new Date().getTime();
        double min = Double.MIN_VALUE;
        Set<String> res = redisQueueTemplate.opsForZSet().rangeByScore(key, min, now, 0, 10);
        if (CollectionUtils.isNotEmpty(res)) {
            for (String data : res) {
                // 删除成功，则进行处理，防止并发获取重复数据
                if (rem(data)) {
                    return data;
                }
            }
        }
        return null;
    }


    @Override
    public boolean rem(String data) {
        return redisQueueTemplate.opsForZSet().remove(key, data) > 0;
    }

}
