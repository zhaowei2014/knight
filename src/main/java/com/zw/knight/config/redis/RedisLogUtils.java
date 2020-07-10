package com.zw.knight.config.redis;

import com.zw.knight.util.GsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * redis log记录工具
 *
 * @author hexiaosong
 * @date 2019-04-16
 */
public class RedisLogUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisLogUtils.class);

    /**
     * 记录redis错误日志
     *
     * @param redisErrorLog redis错误日志
     */
    public static void redisLog(RedisErrorLog redisErrorLog) {
        LOGGER.info(GsonUtils.toJson(redisErrorLog));
    }
}
