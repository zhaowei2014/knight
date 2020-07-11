package com.zw.knight.config;

import com.zw.knight.config.codis.CodisClient;
import com.zw.knight.config.codis.CodisClientBuilderFromNamespace;
import com.zw.knight.config.codis.CodisUtils;
import com.zw.knight.config.namespace.NSClient;
import com.zw.knight.config.namespace.NameSpaceUtils;
import com.zw.knight.config.namespace.NodeConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Optional;

/**
 * redis数据源配置类
 *
 * @author zw
 * @date 2019/9/2
 */
@Configuration
@PropertySource("classpath:config/redis.properties")
public class RedisConfig {

    private static final Logger LOG = LoggerFactory.getLogger(RedisConfig.class);

    @Value("${redis.maxTotal}")
    private int maxTotal;

    @Value("${redis.maxIdle}")
    private int maxIdle;

    @Value("${redis.MinIdle}")
    private int minIdle;

    @Value("${redis.maxWaitMillis}")
    private int maxWaitMillis;

    @Value("${redis.testOnBorrow}")
    private boolean testOnBorrow;

    @Value("${redis.queue.host}")
    private String queueHost;

    @Value("${redis.queue.port}")
    private int queuePort;

    @Value("${redis.queue.timeout}")
    private int queueTimeOut;

    @Value("${redis.queue.database}")
    private int queueDataBase;

    @Value("${redis.namespace}")
    private String redisNamespace;

    @Value("${redis.knight.queue}")
    private String knightQueue;

    @Value("${redis.knight.queue.name}")
    private String knightQueueName;

    @Value("${redis.knight.queue.num}")
    private int knightQueueNum;

    @Value("${redis.knight.queue.sleep.time}")
    private int knightQueueSleepTime;

    @Value("${codis.proxy.namespace}")
    private String codisProxyNamespace;

    @Value("${codis.proxy.timeout}")
    private int codisProxyTimeout;

    @Value("${codis.sign.proxy.namespace}")
    private String codisSignProxyNamespace;

    @Value("${codis.sign.proxy.timeout}")
    private int codisSignProxyTimeout;

    @Value("${codis.cache.proxy.namespace}")
    private String codisCacheProxyNamespace;
    @Value("${codis.cache.proxy.timeout}")
    private int codisCacheProxyTimeout;

    @Value("${download.config.proxy.namespace}")
    private String downloadConfigProxyNamespace;
    @Value("${download.config.proxy.timeout}")
    private int downloadConfigProxyTimeout;

    @Value("${order.codis.proxy.namespace}")
    private String orderCodisProxyNamespace;
    @Value("${order.codis.proxy.timeout}")
    private int orderCodisProxyTimeout;

    @Autowired
    private NSClient nsClient;

    @Bean(name = "jedisConnectionFactory")
    public JedisConnectionFactory getRedisConnectionFactory() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(maxTotal);
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMinIdle(minIdle);
        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
        jedisPoolConfig.setTestOnBorrow(testOnBorrow);
        JedisConnectionFactory factory = new JedisConnectionFactory();
        Optional<NodeConfig> optional = NameSpaceUtils.getIPPort(redisNamespace, nsClient);
        if (optional.isPresent()) {
            NodeConfig config = optional.get();
            setFactoryParams(factory, config.getHost(), config.getPort(), queueDataBase, queueTimeOut, jedisPoolConfig);
        } else {
            setFactoryParams(factory, queueHost, queuePort, queueDataBase, queueTimeOut, jedisPoolConfig);
        }
        return factory;
    }

    @Bean(name = "redisTemplate")
    public RedisTemplate<String, String> getRedisTemplate(JedisConnectionFactory jedisConnectionFactory) {
        return new StringRedisTemplate(jedisConnectionFactory);
    }

    private void setFactoryParams(JedisConnectionFactory factory, String host, int port, int database, int timeout,
                                  JedisPoolConfig redisPoolConfig) {
        factory.setPoolConfig(redisPoolConfig);
        factory.setHostName(host);
        factory.setPort(port);
        factory.setDatabase(database);
        factory.setTimeout(timeout);
    }

    @Bean
    public JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig extendJedisPoolConfig = new JedisPoolConfig();
        extendJedisPoolConfig.setMaxTotal(maxTotal);
        extendJedisPoolConfig.setMaxIdle(maxIdle);
        extendJedisPoolConfig.setMinIdle(minIdle);
        extendJedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
        extendJedisPoolConfig.setTestOnBorrow(testOnBorrow);
        return extendJedisPoolConfig;
    }

    /**
     * @return CodisClient
     */
    @Bean(name = "codisClient")
    public CodisUtils codisClient(JedisPoolConfig jedisPoolConfig) {
        CodisClientBuilderFromNamespace codisBuilder = new CodisClientBuilderFromNamespace();
        codisBuilder.withNamespace(codisProxyNamespace)
//                .withNSClient(nsClient)
                .withTimeout(codisProxyTimeout).withUseJedis(true).withJedisPoolConfig(jedisPoolConfig);
        CodisClient codisClient = codisBuilder.parsePathConfig().build();
        // 启动定时获取最新配置功能，单位：秒
        codisBuilder.watch(10);
        return new CodisUtils(codisClient);
    }

}
