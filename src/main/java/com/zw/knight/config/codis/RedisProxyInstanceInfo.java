package com.zw.knight.config.codis;

import redis.clients.jedis.JedisPoolConfig;

/**
 * redisProxy实体类，即：codis包装的redis代理实例类
 * @author chenchao
 * @date 2016年5月9日 下午2:50:15
 * @version 1.0
 * @throws
 * @see
 */

public class RedisProxyInstanceInfo {
    private String clusterName;
    private String host;
    private int port;
    private int weight;
    private int timeout;
    private JedisPoolConfig jedisPoolConfig;

    public RedisProxyInstanceInfo(String clusterName, String host, int port, int weight, int timeout,
                                  JedisPoolConfig jedisPoolConfig) {
        super();
        this.clusterName = clusterName;
        this.host = host;
        this.port = port;
        this.weight = weight;
        this.timeout = timeout;
        this.jedisPoolConfig = jedisPoolConfig;
    }

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public JedisPoolConfig getJedisPoolConfig() {
        return jedisPoolConfig;
    }

    public void setJedisPoolConfig(JedisPoolConfig jedisPoolConfig) {
        this.jedisPoolConfig = jedisPoolConfig;
    }

}
