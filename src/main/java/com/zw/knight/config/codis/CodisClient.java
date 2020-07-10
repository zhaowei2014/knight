package com.zw.knight.config.codis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisException;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * @author chenchao
 * @version 1.0
 * @date 2016年4月28日 下午7:41:41
 * @throws
 * @see
 */

public class CodisClient {

    private static final Logger logger = LoggerFactory.getLogger(CodisClient.class);

    /**
     * batch codis-proxy instance
     */
    private ArrayList<RedisProxyInstanceInfo> redisProxyInstances;

    /**
     * key example<br>
     * /cache2:192.168.6.236:6349:0<br>
     * /cache2:192.168.6.236:6349:1<br>
     * /cache2:192.168.6.236:6349:2<br>
     * /cache2:192.168.6.236:6449:1<br>
     */
    private static ConcurrentHashMap<String, StringRedisTemplate> stringRedisTemplates =
            new ConcurrentHashMap<String, StringRedisTemplate>();

    /**
     * key example<br>
     * /cache2:192.168.6.236:6349:0<br>
     * /cache2:192.168.6.236:6349:1<br>
     * /cache2:192.168.6.236:6349:2<br>
     * /cache2:192.168.6.236:6449:1<br>
     */
    private static ConcurrentHashMap<String, JedisPool> jedisPools =
            new ConcurrentHashMap<String, JedisPool>();

    /**
     * Constructor
     *
     * @param redisProxyInstances
     * @param useJedis            是否采用jedis原生客户端
     */
    public CodisClient(ArrayList<RedisProxyInstanceInfo> redisProxyInstances, boolean useJedis) {
        this.redisProxyInstances = redisProxyInstances;
        if (useJedis) {
            initJedisPools();
        } else {
            initStringRedisTemplates();
        }
    }

    /**
     * 初始化spring方式的StringJedisTemplate客户端
     */
    public void initStringRedisTemplates() {
        if (null == redisProxyInstances || redisProxyInstances.isEmpty()) {
            logger.error("redisProxyInstances is null or empty!");
            throw new IllegalArgumentException("redisProxyInstances is null or empty!");
        }

        logger.debug("init codis client start");
        logger.debug("stringRedisTemplates.size:{}", stringRedisTemplates.size());

        Set<String> clientKeys = new HashSet<>();
        String clusterName = redisProxyInstances.get(0).getClusterName();
        /* 更新和添加新的实例 */
        for (int i = 0; i < redisProxyInstances.size(); i++) {
            RedisProxyInstanceInfo redisProxyInstanceInfo = redisProxyInstances.get(i);
            String clientKeyNoWeight = buildClientKeyNoWeight(redisProxyInstanceInfo);
            int weight = redisProxyInstanceInfo.getWeight();
            for (int j = 0; j < weight; j++) {
                String clientKey = String.format("%s:%s", clientKeyNoWeight, j);
                clientKeys.add(clientKey);
                /* 如果同clusterName:ip:port:weight的连接已存在，则不再添加 */
                if (stringRedisTemplates.containsKey(clientKey)) {
                    continue;
                } else {
                    /* 如果同clusterName:ip:port的连接实例已存在，只是新增weight，则直接把已有的0号weight复用，包括连接池配置都不再new */
                    String clientKeyWeightZero = String.format("%s:%s", clientKeyNoWeight, 0);
                    StringRedisTemplate srt = stringRedisTemplates.get(clientKeyWeightZero);
                    if (null == srt) {
                        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(
                                redisProxyInstanceInfo.getJedisPoolConfig());
                        jedisConnectionFactory.setHostName(redisProxyInstanceInfo.getHost());
                        jedisConnectionFactory.setPort(redisProxyInstanceInfo.getPort());
                        jedisConnectionFactory.setTimeout(redisProxyInstanceInfo.getTimeout());
                        jedisConnectionFactory.afterPropertiesSet();
                        srt = new StringRedisTemplate(jedisConnectionFactory);
                    }
                    stringRedisTemplates.put(clientKey, srt);
                    logger.debug("add codis client {}", clientKey);
                }
            }
        }

        /*清除老的无用实例，解决周期性加载配置时老数据不能清除的bug，注意只能清除同一个clusterName下的实例(2017-02-15 by chenchao) */
        Iterator<Map.Entry<String, StringRedisTemplate>> iter =
                stringRedisTemplates.entrySet().iterator();
        logger.info("redisProxyOnlineKeys:{}", clientKeys);
        while (iter.hasNext()) {
            Map.Entry<String, StringRedisTemplate> entry = iter.next();
            String key = entry.getKey();
            if (!clientKeys.contains(key) && key.startsWith(clusterName)) {
                logger.info("redisProxyRemoveKey:{}", key);
                iter.remove();
                //关闭链接
                try {
                    JedisConnectionFactory factory = (JedisConnectionFactory) entry.getValue().getConnectionFactory();
                    factory.destroy();
                } catch (DataAccessException e) {
                    //不影响其他移除操作
                    logger.error("close connection error", e);
                }

            }
        }

        logger.debug("stringRedisTemplates.size:{}", stringRedisTemplates.size());
        logger.debug("init codis client end");
    }

    /**
     * 获取spring StringRedisTemplate
     *
     * @return StringRedisTemplate
     */
    public StringRedisTemplate getStringRedisTemplate() {
        if (null == stringRedisTemplates || stringRedisTemplates.isEmpty()) {
            return null;
        }
        /* 只获取该codisClient的clusterName归属的列表，避免多个不同clusterName下随机取所有client范围的bug */
        Map<String, StringRedisTemplate> map = stringRedisTemplates.entrySet().stream()
                .filter((e) -> e.getKey().contains(redisProxyInstances.get(0).getClusterName()))
                .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));
//        Map.Entry<String, StringRedisTemplate> entry = CollectionUtil.getRandomEntryFromMap(map);
        long threadId = Thread.currentThread().getId();
        Map.Entry<String, StringRedisTemplate> entry = CollectionUtil.getModEntryFromMap(map, threadId);
        if (logger.isDebugEnabled()) {
            logger.debug("threadId : {}, codisproxy : {}", threadId, entry.getKey());
        }
        return entry.getValue();
    }

    /**
     * 初始化原生的jedis客户端池
     */
    public void initJedisPools() {
        if (null == redisProxyInstances || redisProxyInstances.isEmpty()) {
            logger.error("redisProxyInstances is null or empty!");
            throw new IllegalArgumentException("redisProxyInstances is null or empty!");
        }

        logger.debug("init codis client[jedis] start");
        logger.debug("jedisPools.size:{}", jedisPools.size());

        Set<String> clientKeys = new HashSet<>();
        String clusterName = redisProxyInstances.get(0).getClusterName();
        /* 更新和添加新的实例 */
        for (int i = 0; i < redisProxyInstances.size(); i++) {
            RedisProxyInstanceInfo redisProxyInstanceInfo = redisProxyInstances.get(i);
            String clientKeyNoWeight = buildClientKeyNoWeight(redisProxyInstanceInfo);
            int weight = redisProxyInstanceInfo.getWeight();
            for (int j = 0; j < weight; j++) {
                String clientKey = String.format("%s:%s", clientKeyNoWeight, j);
                clientKeys.add(clientKey);
                /* 如果同clusterName:ip:port:weight的连接已存在，则不再添加 */
                if (jedisPools.containsKey(clientKey)) {
                    continue;
                } else {
                    /* 如果同clusterName:ip:port的连接池已存在，只是新增weight，则直接把已有的0号weight复用，连接池实例不再new */
                    String clientKeyWeightZero = String.format("%s:%s", clientKeyNoWeight, 0);
                    JedisPool jp = jedisPools.get(clientKeyWeightZero);
                    if (null == jp) {
                        jp = new JedisPool(redisProxyInstanceInfo.getJedisPoolConfig(),
                                redisProxyInstanceInfo.getHost(), redisProxyInstanceInfo.getPort(),
                                redisProxyInstanceInfo.getTimeout());
                    }
                    jedisPools.put(clientKey, jp);
                    logger.debug("add codis client[jedis] {}", clientKey);
                }
            }
        }

        /*清除老的无用实例，解决周期性加载配置时老数据不能清除的bug，注意只能清除同一个clusterName下的实例(2017-02-15 by chenchao) */
        Iterator<Map.Entry<String, JedisPool>> iter = jedisPools.entrySet().iterator();
        logger.info("redisProxyOnlineKeys:{}", clientKeys);
        while (iter.hasNext()) {
            Map.Entry<String, JedisPool> entry = iter.next();
            String key = entry.getKey();
            if (!clientKeys.contains(key) && key.startsWith(clusterName)) {
                logger.info("redisProxyRemoveKey:{}", key);
                iter.remove();
                //销毁无用实例链接
                try {
                    entry.getValue().destroy();
                } catch (JedisException e) {
                    //销毁链接池失败，打错误信息，不影响其他移除操作
                    logger.error("Could not return the resource to the pool", e);
                }
            }
        }

        logger.debug("jedisPools.size:{}", jedisPools.size());
        logger.debug("init codis client[jedis] end");
    }

    /**
     * 获取jedis客户端
     *
     * @return Jedis
     */
    private Map.Entry<String, JedisPool> getJedisFromPools() {
        if (null == jedisPools || jedisPools.isEmpty()) {
            return null;
        }

        /* 只获取该codisClient的clusterName归属的列表，避免多个不同clusterName下随机取所有client范围的bug */
        Map<String, JedisPool> map = jedisPools.entrySet().stream()
                .filter((e) -> e.getKey().contains(redisProxyInstances.get(0).getClusterName()))
                .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));
//        Map.Entry<String, JedisPool> entry = CollectionUtil.getRandomEntryFromMap(map);
        long threadId = Thread.currentThread().getId();
        Map.Entry<String, JedisPool> entry = CollectionUtil.getModEntryFromMap(map, threadId);
        if (logger.isDebugEnabled()) {
            logger.debug("threadId : {}, codisproxy : {}", threadId, entry.getKey());
        }
        return entry;
    }

    /**
     * 包装jedis操作，防止调用方忘记clsoe操作
     *
     * @param consumer
     */
    public void execute(Consumer<Jedis> consumer) {
        String jedisProxyKey = null;
        Jedis jedis = null;
        try {
            Map.Entry<String, JedisPool> entry = getJedisFromPools();
            jedisProxyKey = entry.getKey();
            jedis = entry.getValue().getResource();
            consumer.accept(jedis);// 接受外部可执行代码
        } catch (Exception ex) {
            logger.error("jedisPools size:{}, get random jedis key:{}", jedisPools.size(),
                    jedisProxyKey);
            logger.error("codis client [jedis] excute exception", ex);
            throw new RuntimeException("codis client [jedis] excute exception");
        } finally {
            if (null != jedis) {
                jedis.close();
            }
        }
    }

    /**
     * 生成不带weight的key
     *
     * @param redisProxyInstanceInfo
     * @return clientKeyNoWeight
     */
    private String buildClientKeyNoWeight(RedisProxyInstanceInfo redisProxyInstanceInfo) {
        String clientKeyNoWeight =
                String.format("%s:%s:%s", redisProxyInstanceInfo.getClusterName(),
                        redisProxyInstanceInfo.getHost(), redisProxyInstanceInfo.getPort());
        return clientKeyNoWeight;
    }

}
