/*
 * Copyright (c) 2019.掌阅科技
 * All rights reserved.
 */

package com.zw.knight.config;

import com.zhangyue.arch.nsc.NSClient;
import com.zhangyue.arch.nsc.config.NodeConfig;
import com.zhangyue.common2.utils.NameSpaceUtils;
import com.zhangyue.dj.task.service.invite.InviteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

/**
 * rabbitMq配置
 *
 * @author xqh
 * @date 2019-06-05
 */
@Configuration
@PropertySource("classpath:config/rabbitMQ.properties")
public class RabbitMqConfig {

    private static final Logger LOG = LoggerFactory.getLogger(RabbitMqConfig.class);

    @Value("${dj.reading.rabbitMQ.workThreadNum}")
    private int workThreadNum;

    @Value("${dj.reading.rabbitMQ.host}")
    private String host;

    @Value("${dj.reading.rabbitMQ.port}")
    private int port;

    @Value("${dj.reading.rabbitMQ.username}")
    private String userName;

    @Value("${dj.reading.rabbitMQ.password}")
    private String password;

    @Value("${dj.reading.rabbitMQ.namespace}")
    private String namespace;

    @Value("${dj.reading.rabbitMQ.default.monitor.timeout}")
    private int timeout;


    @Autowired
    private NSClient nsClient;

    @Autowired
    private InviteService inviteService;

    /**
     * 连接配置
     *
     * @return connectionFactory
     */
    @Bean(name = "readingTimeConnectionFactory")
    @Primary
    public ConnectionFactory readingTimeConnectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        Optional<NodeConfig> optional = NameSpaceUtils.getIPPort(namespace, nsClient);
        if (optional.isPresent()) {
            NodeConfig config = optional.get();
            connectionFactory.setHost(config.getHost());
            connectionFactory.setPort(config.getPort());
        } else {
            connectionFactory.setHost(host);
            connectionFactory.setPort(port);
        }
        //用户名和密码有默认的，如果是guest可以不用设置
        connectionFactory.setUsername(userName);
        connectionFactory.setPassword(password);
        //必须要设置
        connectionFactory.setPublisherConfirms(true);
        return connectionFactory;
    }


    @Bean(name = "readingContainerFactory")
    public SimpleRabbitListenerContainerFactory readingContainerFactory(
            SimpleRabbitListenerContainerFactoryConfigurer configurer,
            @Qualifier("readingTimeConnectionFactory") ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        return factory;
    }

    /**
     * 队列
     *
     * @return queue
     */
    @Bean("dailyTimeQueue")
    Queue getDailyTimeQueue() {
        return new Queue("daily_time_queue", true);
    }

    @Bean(name = "dailyTimeContainer")
    public SimpleMessageListenerContainer dailyTimeContainer() {
        if (workThreadNum <= 0) {
            return null;
        }
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(readingTimeConnectionFactory());
        container.setQueues(getDailyTimeQueue());
        container.setConcurrentConsumers(workThreadNum);
        container.setAcknowledgeMode(AcknowledgeMode.AUTO);
        container.setReceiveTimeout(timeout);
        container.setMessageListener((ChannelAwareMessageListener) (message, channel) -> {
            byte[] body = message.getBody();
            if (body != null) {
                try {
                    inviteService.processDailyReadingTime(new String(body, StandardCharsets.UTF_8));
                } catch (Exception e) {
                    LOG.error("listen order result msg is error", e);
                }
            }
        });
        return container;
    }

}
