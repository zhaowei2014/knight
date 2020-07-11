package com.zw.knight.config.codis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.JedisPoolConfig;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * @author chenchao
 * @date 2016年4月28日 下午7:49:29
 * @version 1.0
 * @throws
 * @see
 */

public abstract class CodisClientBuilder {

    private static final Logger logger = LoggerFactory.getLogger(CodisClientBuilder.class);

    /**
     * eg: redis://192.168.7.145:19000/cache2?weight=1<br>
     * redis://192.168.7.146:19000/cache2?weight=1<br>
     * redis://192.168.7.145:19200/cache2?weight=1<br>
     */
    protected Set<String> pathConfigList = new HashSet<String>();

    /**
     * default timeout unit:ms
     */
    private int timeout = 1000;

    private JedisPoolConfig jedisPoolConfig;

    /**
     * 是否使用原生的jedis客户端 default:false
     */
    private boolean useJedis = false;

    /**
     * 创建codis client
     *
     * @param useJedis 是否使用原生的jedis客户端
     * @return CodisClient
     */
    public CodisClient build() {
        ArrayList<RedisProxyInstanceInfo> redisProxyInstances = new ArrayList<RedisProxyInstanceInfo>();
        if (null == pathConfigList || pathConfigList.isEmpty()) {
            logger.error("init codis error! pathConfigList is null or empty!");
            throw new IllegalArgumentException("init codis error! pathConfigList is null or empty!");
        }
        logger.info("pathConfigList: {}", pathConfigList);
        for (String pathConfig : pathConfigList) {
            if (null == pathConfig || "".equals(pathConfig.trim())) {
                logger.error("init codis error! pathConfig is null or empty!");
                throw new IllegalArgumentException("init codis error! pathConfig is null or empty!");
            }

            try {
                URI uri = URI.create(pathConfig);
                String host = uri.getHost();
                int port = uri.getPort();
                String clusterName = uri.getPath();
                int weight = Integer.valueOf(uri.getQuery().split("=")[1]);
                RedisProxyInstanceInfo redisInstanceProxyInfo =
                        new RedisProxyInstanceInfo(clusterName, host, port, weight, timeout, jedisPoolConfig);
                redisProxyInstances.add(redisInstanceProxyInfo);
            } catch (Exception ex) {
                logger.error("invalid config: {}", pathConfig);
                throw new IllegalArgumentException(String.format("invalid config: %s", pathConfig), ex);
            }
        }
        CodisClient codisClient = new CodisClient(redisProxyInstances, useJedis);
        return codisClient;
    }

    /**
     * 获取并解析配置
     *
     * @return CodisClientBuilder
     */
    public abstract CodisClientBuilder parsePathConfig();

    public CodisClientBuilder withJedisPoolConfig(JedisPoolConfig jedisPoolConfig) {
        this.jedisPoolConfig = jedisPoolConfig;
        return this;
    }

    public CodisClientBuilder withTimeout(int timeout) {
        this.timeout = timeout;
        return this;
    }

    public CodisClientBuilder withUseJedis(boolean useJedis) {
        this.useJedis = useJedis;
        return this;
    }

    /**
     * 定时获取最新的配置更新现有客户端
     *
     * @param interval 获取配置间隔，单位：秒
     */
    public void watch(int interval) {
        long milliscond = interval * 1000;
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(milliscond);
                    this.parsePathConfig().build();
                } catch (InterruptedException e) {
                    logger.error("reload config and rebuild error", e);
                } catch (Throwable t) {//catch error, not only exception
                    logger.error("reload config and rebuild error", t);
                }
            }
        }).start();
    }

}
